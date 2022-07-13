package com.qfzc.twitter.infrastructure.dao.mapper;

import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qfzc.twitter.domain.entity.TweetEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author liang.qfzc@gmail.com
 * @description 针对表【tweet(推文)】的数据库操作Mapper
 * @createDate 2022-06-16 13:17:50
 */
public interface TweetMapper extends BaseMapper<TweetEntity> {

    List<TweetEntity> findAllRelWalletByCreatedAtBetweenAndStatusAndInsertType(@Param("start") DateTime start,
                                                                               @Param("end") DateTime end,
                                                                               @Param("status") int status,
                                                                               @Param("insertType") int insertType);

}




