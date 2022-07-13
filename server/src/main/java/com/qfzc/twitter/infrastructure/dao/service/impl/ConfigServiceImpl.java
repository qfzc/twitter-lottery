package com.qfzc.twitter.infrastructure.dao.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qfzc.twitter.domain.entity.ConfigEntity;
import com.qfzc.twitter.infrastructure.dao.mapper.ConfigMapper;
import com.qfzc.twitter.infrastructure.dao.service.ConfigService;
import org.springframework.stereotype.Service;

/**
* @author liang.qfzc@gmail.com
* @description 针对表【config】的数据库操作Service实现
* @createDate 2022-06-16 14:12:12
*/
@Service
public class ConfigServiceImpl extends ServiceImpl<ConfigMapper, ConfigEntity>
    implements ConfigService {

    @Override
    public ConfigEntity findByName(String name) {
        return lambdaQuery().eq(ConfigEntity::getConfName, name).one();
    }
}




