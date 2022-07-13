package com.qfzc.twitter.infrastructure.dao.service;

import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.extension.service.IService;
import com.qfzc.twitter.domain.entity.TweetEntity;

import java.util.List;

/**
 * @author liang.qfzc@gmail.com
 * @description 针对表【tweet(推文)】的数据库操作Service
 * @createDate 2022-06-16 13:17:50
 */
public interface TweetService extends IService<TweetEntity> {

    List<TweetEntity> findAllByCreatedAtBetweenAndStatus(DateTime start, DateTime end, int status);

    List<TweetEntity> findAllRelWalletByCreatedAtBetweenAndStatusAndInsertType(DateTime start, DateTime end, int status, int insertType);
}
