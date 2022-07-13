package com.qfzc.twitter.infrastructure.dao.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qfzc.twitter.infrastructure.dao.service.AccountService;
import com.qfzc.twitter.domain.entity.AccountEntity;
import com.qfzc.twitter.infrastructure.dao.mapper.AccountMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author liang.qfzc@gmail.com
 * @description 针对表【account(参与抽奖的账号)】的数据库操作Service实现
 * @createDate 2022-06-16 13:17:49
 */
@Service
public class AccountServiceImpl extends ServiceImpl<AccountMapper, AccountEntity>
        implements AccountService {

    @Override
    public List<AccountEntity> findAllByStatus(int status) {
        return lambdaQuery().eq(AccountEntity::getStatus, status).list();
    }

    @Override
    public AccountEntity randomPickAccount() {
        return getBaseMapper().randomPickAccount();
    }

}




