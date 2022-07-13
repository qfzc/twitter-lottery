package com.qfzc.twitter.domain;

/**
 * @author liang.qfzc@gmail.com
 * @date 2022/5/26
 */
public interface LotteryService {

    /**
     * 转推参与抽奖
     *
     * @return
     */
    boolean lottery();

    /**
     * 转推参与抽奖
     *
     * @return
     */
    boolean doAction(String accountId, String tweetId);

    /**
     * 拿到所有被监控的Twitter用户
     *
     * @return
     */
    void fetchTweet();

}
