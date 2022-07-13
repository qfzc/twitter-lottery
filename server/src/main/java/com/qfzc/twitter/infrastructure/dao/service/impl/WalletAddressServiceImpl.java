package com.qfzc.twitter.infrastructure.dao.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qfzc.twitter.domain.entity.WalletAddressEntity;
import com.qfzc.twitter.infrastructure.dao.mapper.WalletAddressMapper;
import com.qfzc.twitter.infrastructure.dao.service.WalletAddressService;
import org.springframework.stereotype.Service;

/**
 * @author liang.qfzc@gmail.com
 * @description 针对表【wallet_address(钱包账号地址)】的数据库操作Service实现
 * @createDate 2022-06-16 13:17:50
 */
@Service
public class WalletAddressServiceImpl extends ServiceImpl<WalletAddressMapper, WalletAddressEntity>
        implements WalletAddressService {

    @Override
    public boolean register(WalletAddressEntity account) {
        if (lambdaQuery().eq(WalletAddressEntity::getAddress, account.getAddress()).exists()) {
            return false;
        }

        saveOrUpdate(account);
        return true;
    }
}




