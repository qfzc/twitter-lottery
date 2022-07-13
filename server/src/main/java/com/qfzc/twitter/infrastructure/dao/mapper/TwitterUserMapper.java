package com.qfzc.twitter.infrastructure.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qfzc.twitter.domain.entity.TwitterUserEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author liang.qfzc@gmail.com
 * @description 针对表【twitter_user(Twitter用户列表)】的数据库操作Mapper
 * @createDate 2022-06-16 13:17:50
 */
public interface TwitterUserMapper extends BaseMapper<TwitterUserEntity> {

    List<TwitterUserEntity> selectAllByAddress(@Param("address") String address);

}




