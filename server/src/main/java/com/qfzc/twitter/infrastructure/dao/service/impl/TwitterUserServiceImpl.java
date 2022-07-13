package com.qfzc.twitter.infrastructure.dao.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qfzc.twitter.infrastructure.dao.service.AccountRelService;
import com.qfzc.twitter.infrastructure.dao.service.AccountService;
import com.qfzc.twitter.infrastructure.dao.service.TwitterUserService;
import com.qfzc.twitter.infrastructure.dao.service.WalletAddressService;
import com.qfzc.twitter.infrastructure.twitter.TwitterApiService;
import com.qfzc.twitter.infrastructure.twitter.TwitterApiServiceImpl;
import com.qfzc.twitter.domain.entity.AccountEntity;
import com.qfzc.twitter.domain.entity.AccountRelEntity;
import com.qfzc.twitter.domain.entity.TwitterUserEntity;
import com.qfzc.twitter.domain.entity.WalletAddressEntity;
import com.qfzc.twitter.infrastructure.dao.mapper.TwitterUserMapper;
import io.github.redouane59.twitter.dto.user.UserV2;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author liang.qfzc@gmail.com
 * @description 针对表【twitter_user(Twitter用户列表)】的数据库操作Service实现
 * @createDate 2022-06-16 13:17:50
 */
@Service
public class TwitterUserServiceImpl extends ServiceImpl<TwitterUserMapper, TwitterUserEntity>
        implements TwitterUserService {

    @Resource
    AccountService accountService;

    @Resource
    AccountRelService accountRelService;

    @Resource
    WalletAddressService walletAddressService;

    @Override
    public boolean saveAccount(String address, String twitterUserName) {

        WalletAddressEntity walletAddress = walletAddressService.lambdaQuery().eq(WalletAddressEntity::getAddress, address).one();

        //随机取一个账号使用
        AccountEntity account = accountService.randomPickAccount();
        TwitterApiService twitterApiService = new TwitterApiServiceImpl(account);

        UserV2 user = twitterApiService.findUserByUsername(twitterUserName);
        TwitterUserEntity userEntity = new TwitterUserEntity();
        userEntity.setId(user.getId());
        userEntity.setAvatar(user.getProfileImageUrl());
        userEntity.setName(user.getName());
        userEntity.setNickName(user.getDisplayedName());
        userEntity.created(String.valueOf(walletAddress.getId()));

        AccountRelEntity accountRel = new AccountRelEntity();
        accountRel.setWId(walletAddress.getId());
        accountRel.setTId(userEntity.getId());
        accountRelService.save(accountRel);

        save(userEntity);
        return true;
    }

    @Override
    public List<TwitterUserEntity> findUsersByAddress(String address) {
        return getBaseMapper().selectAllByAddress(address);
    }

    @Override
    public boolean unbindTwitterUser(String tid, String address) {
        Integer walletId = walletAddressService.lambdaQuery().eq(WalletAddressEntity::getAddress, address).one().getId();
        String watchUserId = lambdaQuery().eq(TwitterUserEntity::getId, tid).one().getId();

        return accountRelService.lambdaUpdate()
                .eq(AccountRelEntity::getWId, walletId)
                .eq(AccountRelEntity::getTId, watchUserId).remove();
    }
}




