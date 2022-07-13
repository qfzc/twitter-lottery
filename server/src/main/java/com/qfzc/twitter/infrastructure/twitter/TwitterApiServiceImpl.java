package com.qfzc.twitter.infrastructure.twitter;

import com.qfzc.twitter.domain.entity.AccountEntity;
import com.qfzc.twitter.domain.entity.TwitterUserEntity;
import io.github.redouane59.twitter.TwitterClient;
import io.github.redouane59.twitter.dto.endpoints.AdditionalParameters;
import io.github.redouane59.twitter.dto.tweet.*;
import io.github.redouane59.twitter.dto.user.UserActionResponse;
import io.github.redouane59.twitter.dto.user.UserV2;
import io.github.redouane59.twitter.signature.TwitterCredentials;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TwitterApiServiceImpl implements TwitterApiService {

    private final TwitterClient twitterClient;

    public TwitterApiServiceImpl(AccountEntity account) {
        TwitterCredentials credentials = new TwitterCredentials();
        credentials.setApiKey(account.getApiKey());
        credentials.setApiSecretKey(account.getApiSecret());
        credentials.setAccessToken(account.getAccessToken());
        credentials.setAccessTokenSecret(account.getAccessTokenSecret());
        twitterClient = new TwitterClient(credentials);
    }

    @Override
    public TweetList getUserTimeline(TwitterUserEntity twitterUser) {
        AdditionalParameters parameters = AdditionalParameters.builder().sinceId(twitterUser.getSinceId()).recursiveCall(false).build();
        return twitterClient.getUserTimeline(twitterUser.getId(), parameters);
    }

    @Override
    public UserV2 findUserByUsername(String username) {
        UserV2 user = twitterClient.getUserFromUserName(username);
        log.debug("FindUserByUsername resp: {}", user);
        return user;
    }

    @Override
    public LikeResponse.DataLikeResponse like(String id) {
        LikeResponse likeResponse = twitterClient.likeTweet(id);
        log.debug("Like resp: {}", likeResponse.getData());
        return likeResponse.getData();
    }

    @Override
    public RetweetResponse.RetweetData retweetTweet(String tweetId) {
        RetweetResponse retweetResponse = twitterClient.retweetTweet(tweetId);
        log.debug("Retweet resp: {}", retweetResponse);
        return retweetResponse.getData();
    }

    @Override
    public Tweet postTweet(String text, String inReplyToTweetId) {
        twitterClient.postTweet(text);

        TweetParameters tweetParameters = TweetParameters.builder()
                .text(text)
                .reply(TweetParameters.Reply.builder().inReplyToTweetId(inReplyToTweetId).build()).build();
        Tweet tweet = twitterClient.postTweet(tweetParameters);
        log.debug("PostTweet resp: {}", tweet);
        return tweet;
    }

    @Override
    public UserActionResponse.FollowData following(String targetUserId) {
        UserActionResponse follow = twitterClient.follow(targetUserId);
        log.debug("Following resp: {}", follow.getData());
        return follow.getData();
    }

    @Override
    public TweetList tweetsSearch(String query, AdditionalParameters parameters) {
        TweetList tweetList = twitterClient.searchTweets(query, parameters);
        log.debug("tweetsSearch resp: {}", tweetList.getData().size());
        return tweetList;
    }

    @Override
    public Tweet queryTweet(String tweetId) {
        return twitterClient.getTweet(tweetId);
    }
}
