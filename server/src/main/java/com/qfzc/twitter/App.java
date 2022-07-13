package com.qfzc.twitter;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * @author liang.qfzc@gmail.com
 * @date 2022/5/26
 */
@SpringBootApplication
@EnableCaching
@MapperScan("com.qfzc.twitter.infrastructure.dao.mapper")
public class App {

    public static void main(String[] args) {
        SpringApplication.run(App.class);
    }
}
