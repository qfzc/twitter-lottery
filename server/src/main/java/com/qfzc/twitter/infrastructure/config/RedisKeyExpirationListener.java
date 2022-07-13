package com.qfzc.twitter.infrastructure.config;

import com.qfzc.twitter.domain.LotteryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author liang.qfzc@gmail.com
 * @date 2022/6/23
 */
@Component
@Slf4j
public class RedisKeyExpirationListener extends KeyExpirationEventMessageListener {

    @Resource
    private LotteryService lotteryService;

    public RedisKeyExpirationListener(RedisMessageListenerContainer listenerContainer) {
        super(listenerContainer);
    }

    /**
     * 使用该方法监听 当我们都key失效的时候执行该方法
     *
     * @param message
     * @param pattern
     */
    @Override
    public void onMessage(Message message, byte[] pattern) {
        String[] expireKey = message.toString().split("_");
        String accountId = expireKey[0];
        String tweetId = expireKey[1];
        log.info("我已经监测到键值失效啦键值是" +  message + "你可以执行你的业务了");

        lotteryService.doAction(accountId, tweetId);
    }
}
