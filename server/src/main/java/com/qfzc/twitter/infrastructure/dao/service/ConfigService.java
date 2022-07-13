package com.qfzc.twitter.infrastructure.dao.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qfzc.twitter.domain.entity.ConfigEntity;

/**
 * @author liang.qfzc@gmail.com
 * @description 针对表【config】的数据库操作Service
 * @createDate 2022-06-16 14:12:12
 */
public interface ConfigService extends IService<ConfigEntity> {

    ConfigEntity findByName(String name);

}
