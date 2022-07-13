package com.qfzc.twitter.job;

import com.qfzc.twitter.domain.LotteryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Slf4j
@Component
public class LotteryJob {

    @Resource
    LotteryService lotteryService;

    /**
     * 抓取关注列表中的Twitter更新 2小时一次
     */
    @Scheduled(initialDelay = 10 * 60 * 1000, fixedRate = 2 * 60 * 60 * 1000)
    public void fetchTweet() {
        log.info("fetchTweet");
        lotteryService.fetchTweet();
    }

    /**
     * 抽奖（RT、Like等) 半小时，延迟一分钟
     */
    @Scheduled(fixedRate = 30 * 60 * 1000)
    public void lottery() {
        log.info("lottery");
        lotteryService.lottery();
    }

}
