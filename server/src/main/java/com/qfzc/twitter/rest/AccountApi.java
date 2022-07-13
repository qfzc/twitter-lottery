package com.qfzc.twitter.rest;

import com.qfzc.twitter.domain.LotteryService;
import com.qfzc.twitter.domain.entity.AccountEntity;
import com.qfzc.twitter.domain.entity.WalletAddressEntity;
import com.qfzc.twitter.infrastructure.dao.service.AccountService;
import com.qfzc.twitter.infrastructure.dao.service.WalletAddressService;
import com.qfzc.twitter.infrastructure.twitter.AccessTokenService;
import com.qfzc.twitter.infrastructure.twitter.TwitterApiServiceImpl;
import com.qfzc.twitter.util.TwitterAccessTokenCache;
import io.github.redouane59.twitter.dto.user.UserV2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import twitter4j.TwitterException;
import twitter4j.auth.AccessToken;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author liang.qfzc@gmail.com
 * @date 2022/6/5 15:03
 */
@RestController
@CrossOrigin
public class AccountApi {

    @Resource
    LotteryService twitterUserService;

    @Resource
    WalletAddressService walletAddressService;

    @Resource
    AccessTokenService accessTokenService;

    @Resource
    AccountService accountService;

    @PostMapping("/wallet/users")
    public ResponseEntity<Boolean> addWallet(@RequestBody WalletAddressEntity walletAddress) {
        return ResponseEntity.ok(walletAddressService.register(walletAddress));
    }

    @GetMapping("/account/users/{address}")
    public ResponseEntity<List<AccountEntity>> list(@PathVariable("address") String address) {
        WalletAddressEntity walletAddressEntity = walletAddressService.lambdaQuery().eq(WalletAddressEntity::getAddress, address).one();
        if (walletAddressEntity == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(accountService.lambdaQuery().eq(AccountEntity::getWId, walletAddressEntity.getId()).list());
    }

    @DeleteMapping("/account/delete/{id}")
    public ResponseEntity<Boolean> unbind(@PathVariable("id") String id) {
        return ResponseEntity.ok(accountService.lambdaUpdate().eq(AccountEntity::getId, id).remove());
    }

    @PostMapping("/account/authUrl")
    public ResponseEntity<String> authUrl(String consumerKey, String consumerSecret) {
        try {
            return ResponseEntity.ok(accessTokenService.authUrl(consumerKey, consumerSecret));
        } catch (TwitterException e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
    }

    @PostMapping("/account/add")
    public ResponseEntity<String> addAccount(String address, String tag, String consumerKey, String oauthVerifier) {
        try {
            AccessToken oAuthAccessToken = accessTokenService.getOAuthAccessToken(consumerKey, oauthVerifier);

            String consumerSecret = TwitterAccessTokenCache.getConsumerSecret(consumerKey);
            AccountEntity account = new AccountEntity();
            account.setApiKey(consumerKey);
            account.setApiSecret(consumerSecret);

            account.setAccessToken(oAuthAccessToken.getToken());
            account.setAccessTokenSecret(oAuthAccessToken.getTokenSecret());

            UserV2 me = new TwitterApiServiceImpl(account).findUserByUsername(oAuthAccessToken.getScreenName());
            account.setName(me.getName());
            account.setAvatar(me.getProfileImageUrl());
            account.setTId(me.getId());
            account.setTag(tag);

            WalletAddressEntity walletAddress = walletAddressService.lambdaQuery().eq(WalletAddressEntity::getAddress, address).one();
            account.setWId(String.valueOf(walletAddress.getId()));
            accountService.saveOrUpdate(account);
            return ResponseEntity.ok().build();
        } catch (TwitterException e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
    }

}
