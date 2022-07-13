package com.qfzc.twitter.infrastructure.dao.service.impl;

import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qfzc.twitter.infrastructure.dao.service.TweetService;
import com.qfzc.twitter.domain.entity.TweetEntity;
import com.qfzc.twitter.infrastructure.dao.mapper.TweetMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author liang.qfzc@gmail.com
 * @description 针对表【tweet(推文)】的数据库操作Service实现
 * @createDate 2022-06-16 13:17:50
 */
@Service
public class TweetServiceImpl extends ServiceImpl<TweetMapper, TweetEntity>
        implements TweetService {

    @Override
    public List<TweetEntity> findAllByCreatedAtBetweenAndStatus(DateTime start, DateTime end, int status) {
        return lambdaQuery().between(TweetEntity::getCreatedAt, start, end).eq(TweetEntity::getStatus, status).list();
    }

    @Override
    public List<TweetEntity> findAllRelWalletByCreatedAtBetweenAndStatusAndInsertType(DateTime start, DateTime end, int status, int insertType) {
        return getBaseMapper().findAllRelWalletByCreatedAtBetweenAndStatusAndInsertType(start, end, status, insertType);
    }
}




