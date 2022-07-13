package com.qfzc.twitter.infrastructure.twitter;//package com.smurfs.twitter.infrastructure.twitter;

import com.qfzc.twitter.domain.entity.TwitterUserEntity;
import io.github.redouane59.twitter.dto.endpoints.AdditionalParameters;
import io.github.redouane59.twitter.dto.tweet.LikeResponse;
import io.github.redouane59.twitter.dto.tweet.RetweetResponse;
import io.github.redouane59.twitter.dto.tweet.Tweet;
import io.github.redouane59.twitter.dto.tweet.TweetList;
import io.github.redouane59.twitter.dto.user.UserActionResponse;
import io.github.redouane59.twitter.dto.user.UserV2;

/**
 * @author liang.qfzc@gmail.com
 * @date 2022/5/27
 */
public interface TwitterApiService {

    /**
     * 用户所有推文
     *
     * @param twitterUser
     * @return
     * @
     */
    TweetList getUserTimeline(TwitterUserEntity twitterUser);

    /**
     * 查找用户
     */
    UserV2 findUserByUsername(String username);

    /**
     * 点赞
     *
     * @param id
     * @return
     */
    LikeResponse.DataLikeResponse like(String id);

    /**
     * 转推
     *
     * @param tweetId
     * @return
     */
    RetweetResponse.RetweetData retweetTweet(String tweetId);

    /**
     * 发推文
     *
     * @param text
     * @return
     */
    Tweet postTweet(String text, String inReplyToTweetId);

    /**
     * 关注
     *
     * @param targetUserId
     * @return
     */
    UserActionResponse.FollowData following(String targetUserId);

    /**
     * 搜索推文
     *
     * @return
     */
    TweetList tweetsSearch(String query, AdditionalParameters parameters);

    Tweet queryTweet(String tweetId);

}
