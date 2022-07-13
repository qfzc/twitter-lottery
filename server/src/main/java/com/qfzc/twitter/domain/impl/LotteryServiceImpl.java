package com.qfzc.twitter.domain.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ReUtil;
import cn.hutool.core.util.StrUtil;
import com.qfzc.twitter.domain.LotteryService;
import com.qfzc.twitter.domain.constant.Constant;
import com.qfzc.twitter.domain.entity.AccountEntity;
import com.qfzc.twitter.domain.entity.TweetEntity;
import com.qfzc.twitter.domain.entity.TwitterUserEntity;
import com.qfzc.twitter.domain.entity.WalletAddressEntity;
import com.qfzc.twitter.infrastructure.dao.service.AccountService;
import com.qfzc.twitter.infrastructure.dao.service.TweetService;
import com.qfzc.twitter.infrastructure.dao.service.TwitterUserService;
import com.qfzc.twitter.infrastructure.dao.service.WalletAddressService;
import com.qfzc.twitter.infrastructure.twitter.TwitterApiService;
import com.qfzc.twitter.infrastructure.twitter.TwitterApiServiceImpl;
import com.qfzc.twitter.util.CommonUtil;
import com.qfzc.twitter.util.DictMapUtil;
import io.github.redouane59.twitter.dto.endpoints.AdditionalParameters;
import io.github.redouane59.twitter.dto.tweet.Tweet;
import io.github.redouane59.twitter.dto.tweet.TweetList;
import io.github.redouane59.twitter.dto.tweet.TweetV2;
import io.github.redouane59.twitter.dto.user.UserV2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author liang.qfzc@gmail.com
 * @date 2022/5/26
 */
@Slf4j
@Service
public class LotteryServiceImpl implements LotteryService {

    @Autowired
    AccountService accountService;

    @Resource
    TwitterUserService twitterUserService;

    @Resource
    WalletAddressService walletAddressService;

    @Resource
    TweetService tweetService;

    @Resource
    RedisTemplate<String, Object> redisTemplate;

    /**
     * Twitter 抽奖主要逻辑
     * <p>
     * - 获取所有被监控的Twitter用户
     * - 查看这些用户的新发送的Twitter
     * - 根据规则过滤掉不适合转发的Twitter
     * - 遍历所有参与抽奖的用户
     * - 循环对所有推文进行点赞，转推，@Tag
     * - 对参与抽奖的用户次数+1
     *
     * @return
     */
    @Override
    public boolean lottery() {
        // 近一天没有被转推的推文
        List<TweetEntity> tweets = tweetService.findAllRelWalletByCreatedAtBetweenAndStatusAndInsertType(
                DateUtil.offsetDay(DateUtil.date(), -1), DateUtil.date(), Constant.TweetStatus.INIT.code(), Constant.TweetInsertType.INIT.code());

        // 关注的用户 tweet， 每个钱包下可能用多个账户，多账户都参与一遍抽奖
        for (int i = 0; i < tweets.size(); i++) {
            TweetEntity tweet = tweets.get(i);

            Integer walletId = walletAddressService.lambdaQuery().eq(WalletAddressEntity::getAddress, tweet.getWalletAddress()).one().getId();
            List<AccountEntity> accounts = accountService.lambdaQuery().eq(AccountEntity::getWId, walletId).list();

            for (int j = 0; j < accounts.size(); j++) {
                AccountEntity account = accounts.get(j);
                // 一分钟处理一条 tweet
                redisTemplate.opsForValue().set(account.getId() + "_" + tweet.getId(), tweet.getId(), i + 1 + j + 1, TimeUnit.MINUTES);
            }

            tweet.setStatus(Constant.TweetStatus.RETWEETED.code());
            tweetService.saveOrUpdate(tweet);
        }

        // 根据关键字搜索的 tweet, 所有 account 都要推送一遍
        List<TweetEntity> searchResult = tweetService.lambdaQuery()
                .between(TweetEntity::getCreatedAt, DateUtil.offsetDay(DateUtil.date(), -1), DateUtil.date())
                .eq(TweetEntity::getStatus, Constant.TweetStatus.INIT.code())
                .eq(TweetEntity::getInsertType, Constant.TweetInsertType.KEYWORD.code())
                .list();
        for (int i = 0; i < searchResult.size(); i++) {
            TweetEntity tweet = searchResult.get(i);
            List<AccountEntity> accounts = accountService.list();
            for (int j = 0; j < accounts.size(); j++) {
                AccountEntity account = accounts.get(j);
                // 一分钟处理一条 tweet
                redisTemplate.opsForValue().set(account.getId() + "_" + tweet.getId(), tweet.getId(), i + 1 + j + 1, TimeUnit.MINUTES);

            }
            tweet.setStatus(Constant.TweetStatus.RETWEETED.code());
            tweetService.saveOrUpdate(tweet);
        }
        return true;
    }

    @Override
    public boolean doAction(String accountId, String tweetId) {
        AccountEntity account = accountService.lambdaQuery().eq(AccountEntity::getId, accountId).one();
        TweetEntity tweet = tweetService.lambdaQuery().eq(TweetEntity::getId, tweetId).one();

        TwitterApiService twitterApiService = new TwitterApiServiceImpl(account);
        log.info("用户：{},开始参与抽奖， 参与推文ID：{} ", account.getName(), tweet.getId());

        Tweet checkTweet = twitterApiService.queryTweet(tweet.getId());
        if (checkTweet == null) {
            log.error("推文已经被删除：{}", tweet.getId());
            return false;
        }

        //转推
        twitterApiService.retweetTweet(tweet.getId());
        CommonUtil.sleep(10000);

        //点赞
        twitterApiService.like(tweet.getId());
        CommonUtil.sleep(10000);

        //发推
        twitterApiService.postTweet(account.getTag(), tweet.getId());
        CommonUtil.sleep(10000);

        List<String> usernames = ReUtil.findAll("@(\\S+)", tweet.getText(), 0, new ArrayList<>());
        usernames = CollUtil.distinct(usernames.stream().map(text -> {
            return text.replaceAll("^[\\pP\\pS\\pZ]+|[\\pP\\pS\\pZ]+$", "");
        }).collect(Collectors.toList()));

        if (CollUtil.isNotEmpty(usernames)) {
            for (String username : usernames) {
                if (StrUtil.isBlank(username)) {
                    continue;
                }
                UserV2 user = twitterApiService.findUserByUsername(
                        username.replaceFirst("@", ""));
                if (user != null) {
                    //关注用户
                    twitterApiService.following(user.getId());
                    CommonUtil.sleep(10000);
                }
            }
        }
        log.info("用户：{},参与推文ID：{} 抽奖成功, 推文内容:{}", account.getName(), tweet.getId(), tweet.getText());
        account.setRetCount(account.getRetCount() + 1);
        accountService.saveOrUpdate(account);

        CommonUtil.sleep(10000);
        return true;
    }

    @Override
    public void fetchTweet() {
        List<TwitterUserEntity> twitterUsers = twitterUserService.list();

        for (TwitterUserEntity twitterUser : twitterUsers) {

            AccountEntity account = accountService.randomPickAccount();

            TwitterApiService twitterApiService = new TwitterApiServiceImpl(account);

            //2、Check for new tweets from those users you follow
            TweetList tweetList = twitterApiService.getUserTimeline(twitterUser);

            if (CollUtil.isEmpty(tweetList.getData())) {
                continue;
            }

            // Prevent repeated queries, resulting in API waste
            TweetV2.TweetData tweetData = tweetList.getData().get(0);
            twitterUser.setSinceId(tweetData.getId());
            twitterUserService.saveOrUpdate(twitterUser);

            // 3、Filter out inappropriate tweets according to the rules
            transTwitter(tweetList, 0);

            CommonUtil.sleep(20 * 1000);
        }
    }

    /**
     * Determine if it's a winning tweet
     *
     * @param tweet
     * @return
     * @in_reply_to_user_id 使用它来确定这条推文是否是对另一条推文的回复。
     * @conversation_id 原始tweetID，表示当前这条是原始tweet下的评论
     */
    public boolean isLotteryTweet(TweetV2.TweetData tweet) {
        // If the ID is not the same, it means that this tweet is a retweet, which will be filtered out first
        if (!StrUtil.equals(tweet.getId(), tweet.getConversationId())) {
            return false;
        }

        String[] retweetRule = DictMapUtil.DICT_MAP.get(Constant.Config.RETWEET_RULE).split(",");
        String[] followRule = DictMapUtil.DICT_MAP.get(Constant.Config.FOLLOW_RULE).split(",");

        List<String[]> allRule = Arrays.asList(retweetRule, followRule);

        long count = allRule.stream().filter(text -> {
            // Iterate through the copy to see if at least one keyword in each group can be matched
            for (String rule : text) {
                if (tweet.getText().contains(rule)) {
                    return true;
                }
            }
            return false;
        }).count();
        return count == allRule.size();
    }

    /**
     * Transform the list of tweets,
     * select the tweets suitable for the lottery and save them into the database
     *
     * @param tweetList
     */
    private void transTwitter(TweetList tweetList, int insertType) {
        List<TweetEntity> tweets = tweetList.getData().stream().filter(this::isLotteryTweet).map(tweet -> {
            TweetEntity tweetEntity = new TweetEntity();
            BeanUtil.copyProperties(tweet, tweetEntity);
            tweetEntity.setInsertType(insertType);
            return tweetEntity;
        }).sorted(Comparator.comparing(TweetEntity::getCreatedAt).reversed()).collect(Collectors.toList());
        if (!tweets.isEmpty()) {
            // batch tweet
            tweetService.saveBatch(tweets);
        }
    }

}
