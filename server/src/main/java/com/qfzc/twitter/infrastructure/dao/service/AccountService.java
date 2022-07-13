package com.qfzc.twitter.infrastructure.dao.service;

import com.qfzc.twitter.domain.entity.AccountEntity;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author liang.qfzc@gmail.com
 * @description 针对表【account(参与抽奖的账号)】的数据库操作Service
 * @createDate 2022-06-16 13:17:49
 */
public interface AccountService extends IService<AccountEntity> {

    List<AccountEntity> findAllByStatus(int status);

    /**
     * Use a random account
     *
     * @return
     */
    AccountEntity randomPickAccount();

}
