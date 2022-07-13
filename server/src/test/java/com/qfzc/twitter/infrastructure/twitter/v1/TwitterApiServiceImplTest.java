package com.qfzc.twitter.infrastructure.twitter.v1;

import com.qfzc.twitter.infrastructure.dao.service.AccountService;
import com.qfzc.twitter.infrastructure.twitter.TwitterApiService;
import com.qfzc.twitter.infrastructure.twitter.TwitterApiServiceImpl;
import com.qfzc.twitter.domain.LotteryService;
import com.qfzc.twitter.domain.entity.AccountEntity;
import io.github.redouane59.twitter.dto.tweet.Tweet;
import io.github.redouane59.twitter.dto.user.UserV2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class TwitterApiServiceImplTest {

    @Autowired
    AccountService repository;

    @Resource
    LotteryService lotteryService;

    @Test
    void getUserTimeline() {
        AccountEntity account1 = repository.getById("1");
        AccountEntity account2 = repository.getById("2");
        TwitterApiService twitterApiService1 = new TwitterApiServiceImpl(account1);
    }

    @Test
    void findUserByUsername() {
        AccountEntity account1 = repository.getById("1");
        TwitterApiService twitterApiService1 = new TwitterApiServiceImpl(account1);
        UserV2 user = twitterApiService1.findUserByUsername("ultraman");
        System.out.println(user);
    }

    @Test
    void like() {
        AccountEntity account1 = repository.getById("1");
        AccountEntity account2 = repository.getById("2");
        TwitterApiService twitterApiService1 = new TwitterApiServiceImpl(account1);
        System.out.println(twitterApiService1.like("1531848013613830144"));

        TwitterApiService twitterApiService2 = new TwitterApiServiceImpl(account2);
        System.out.println(twitterApiService2.like("1531848013613830144"));


    }

    @Test
    void retweetTweet() {
    }

    @Test
    void postTweet() {
        AccountEntity account2 = repository.getById("2");
        TwitterApiService twitterApiService1 = new TwitterApiServiceImpl(account2);
        System.out.println(twitterApiService1.postTweet("haha", "1532640000416022528"));
    }

    @Test
    void following() {
    }


    @Test
    void queryTweet() {
        AccountEntity account2 = repository.getById("1");
        TwitterApiService twitterApiService1 = new TwitterApiServiceImpl(account2);
        Tweet tweet = twitterApiService1.queryTweet("1533056911288983554");
        System.out.println(tweet);
    }
}
