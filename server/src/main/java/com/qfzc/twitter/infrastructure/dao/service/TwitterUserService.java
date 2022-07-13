package com.qfzc.twitter.infrastructure.dao.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qfzc.twitter.domain.entity.TwitterUserEntity;

import java.util.List;

/**
 * @author liang.qfzc@gmail.com
 * @description 针对表【twitter_user(Twitter用户列表)】的数据库操作Service
 * @createDate 2022-06-16 13:17:50
 */
public interface TwitterUserService extends IService<TwitterUserEntity> {

    /**
     * 添加监控用户
     *
     * @param address
     * @param twitterUserName
     * @return
     */
    boolean saveAccount(String address, String twitterUserName);

    /**
     * 根据钱包地址查找被监控的推特用户
     */
    List<TwitterUserEntity> findUsersByAddress(String address);

    /**
     * 删除账户与推特用户关联关系
     * @param tid
     * @param address
     * @return
     */
    boolean unbindTwitterUser(String tid, String address);
}
