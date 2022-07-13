package com.qfzc.twitter.infrastructure.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qfzc.twitter.domain.entity.AccountEntity;

/**
 * @author liang.qfzc@gmail.com
 * @description 针对表【account(参与抽奖的账号)】的数据库操作Mapper
 * @createDate 2022-06-16 13:17:49
 */
public interface AccountMapper extends BaseMapper<AccountEntity> {

    AccountEntity randomPickAccount();
}




