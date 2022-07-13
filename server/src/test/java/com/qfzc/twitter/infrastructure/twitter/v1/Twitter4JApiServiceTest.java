//package com.smurfs.twitter.infrastructure.twitter.v1;
//
//import com.smurfs.twitter.domain.entity.AccountEntity;
//import com.smurfs.twitter.domain.repository.AccountRepository;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import twitter4j.TwitterException;
//
//@SpringBootTest
//class Twitter4JApiServiceTest {
//
//    @Autowired
//    AccountRepository repository;
//
//    @Test
//    void usersTweets() {
//    }
//
//    @Test
//    void findUserByUsername() throws TwitterException {
//        AccountEntity account1 = repository.getById(1);
//        AccountEntity account2 = repository.getById(2);
//        TwitterApiService twitterApiService1 = new TwitterApiServiceImpl(account1);
//
//        boolean user = twitterApiService1.like(1531848013613830144L);
//
//        System.out.println(user);
//
//        TwitterApiService twitterApiService2 = new TwitterApiServiceImpl(account2);
//
//        twitterApiService2.like(1531848013613830144L);
//
//    }
//}
