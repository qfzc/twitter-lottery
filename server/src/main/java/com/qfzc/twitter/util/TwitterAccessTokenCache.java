package com.qfzc.twitter.util;

import cn.hutool.core.lang.Tuple;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import twitter4j.Twitter;
import twitter4j.auth.RequestToken;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author liang.qfzc@gmail.com
 * @date 2022/6/17
 */
public class TwitterAccessTokenCache {

    private static final Cache<String, Tuple> TWITTER_INSTANT_CACHE = Caffeine.newBuilder()
            .expireAfterAccess(5, TimeUnit.MINUTES)
            .build();

    public static void put(String apiKey, Twitter twitter, RequestToken requestToken) {
        TWITTER_INSTANT_CACHE.put(apiKey, new Tuple(twitter, requestToken));
    }

    public static String getConsumerSecret(String apiKey) {
        Twitter twitter = Objects.requireNonNull(TWITTER_INSTANT_CACHE.getIfPresent(apiKey)).get(0);
        return twitter.getConfiguration().getOAuthConsumerSecret();
    }

    public static RequestToken getRequestToken(String apiKey) {
        return Objects.requireNonNull(TWITTER_INSTANT_CACHE.getIfPresent(apiKey)).get(1);
    }

    public static Twitter getTwitter(String apiKey) {
        return Objects.requireNonNull(TWITTER_INSTANT_CACHE.getIfPresent(apiKey)).get(0);
    }

}
