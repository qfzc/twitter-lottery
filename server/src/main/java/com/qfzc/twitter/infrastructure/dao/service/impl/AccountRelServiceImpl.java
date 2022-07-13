package com.qfzc.twitter.infrastructure.dao.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qfzc.twitter.infrastructure.dao.service.AccountRelService;
import com.qfzc.twitter.domain.entity.AccountRelEntity;
import com.qfzc.twitter.infrastructure.dao.mapper.AccountRelMapper;
import org.springframework.stereotype.Service;

/**
 * @author liang.qfzc@gmail.com
 * @description 针对表【account_rel(抽奖账户关联表)】的数据库操作Service实现
 * @createDate 2022-06-16 13:17:50
 */
@Service
public class AccountRelServiceImpl extends ServiceImpl<AccountRelMapper, AccountRelEntity>
        implements AccountRelService {


}




