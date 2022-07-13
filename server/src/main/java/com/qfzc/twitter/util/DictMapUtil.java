package com.qfzc.twitter.util;

import cn.hutool.core.map.MapUtil;
import com.qfzc.twitter.domain.entity.ConfigEntity;
import com.qfzc.twitter.infrastructure.dao.service.ConfigService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.ConcurrentMap;

/**
 * @author liang.qfzc@gmail.com
 * @date 2022/6/26 12:36
 */
@Component
public class DictMapUtil implements CommandLineRunner {

    public final static ConcurrentMap<String, String> DICT_MAP = MapUtil.newConcurrentHashMap();

    @Resource
    ConfigService configService;

    public void initLoad() {
        List<ConfigEntity> list = configService.list();
        for (ConfigEntity configEntity : list) {
            DICT_MAP.put(configEntity.getConfName(), configEntity.getConfValue());
        }
    }

    @Override
    public void run(String... args) throws Exception {
        initLoad();
    }
}
