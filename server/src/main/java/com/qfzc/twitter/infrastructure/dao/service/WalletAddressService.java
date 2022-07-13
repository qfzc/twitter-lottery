package com.qfzc.twitter.infrastructure.dao.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qfzc.twitter.domain.entity.WalletAddressEntity;

/**
* @author liang.qfzc@gmail.com
* @description 针对表【wallet_address(钱包账号地址)】的数据库操作Service
* @createDate 2022-06-16 13:17:50
*/
public interface WalletAddressService extends IService<WalletAddressEntity> {

    boolean register(WalletAddressEntity account);

}
