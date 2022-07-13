package com.qfzc.twitter.infrastructure.twitter;

import com.qfzc.twitter.util.TwitterAccessTokenCache;
import org.springframework.stereotype.Service;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;


/**
 * @author Yusuke Yamamoto - yusuke at mac.com
 * @since Twitter4J 2.1.7
 */
@Service
public class AccessTokenService {

    /**
     * Usage: java  twitter4j.examples.oauth.GetAccessToken [consumer key] [consumer secret]
     */
    public String authUrl(String apiKey, String apiSecret) throws TwitterException {
        Configuration build = new ConfigurationBuilder()
                .setOAuthConsumerKey(apiKey)
                .setOAuthConsumerSecret(apiSecret)
                .build();
        Twitter twitter = new TwitterFactory(build).getInstance();
        RequestToken requestToken = twitter.getOAuthRequestToken();

        TwitterAccessTokenCache.put(apiKey, twitter, requestToken);
        return requestToken.getAuthorizationURL();
    }

    public AccessToken getOAuthAccessToken(String apiKey, String oauthVerifier) throws TwitterException {
        Twitter twitter = TwitterAccessTokenCache.getTwitter(apiKey);

        if (twitter == null) {
            return null;
        }

        try {
            return twitter.getOAuthAccessToken(TwitterAccessTokenCache.getRequestToken(apiKey), oauthVerifier);
        } catch (TwitterException te) {
            if (401 == te.getStatusCode()) {
                System.out.println("Unable to get the access token.");
            } else {
                te.printStackTrace();
            }
            throw te;
        }
    }

}
