package com.qfzc.twitter.job;

import cn.hutool.core.thread.ThreadFactoryBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import java.util.concurrent.*;

@Configuration
@EnableScheduling
public class ScheduleConfig implements SchedulingConfigurer {

    @Value("${thread.pool.corePoolSize:10}")
    private int corePoolSize;

    @Value("${thread.pool.maxPoolSize:500}")
    private int maxPoolSize;

    @Value("${thread.pool.keepAliveSeconds:300}")
    private int keepAliveSeconds;

    @Override
    public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {
        scheduledTaskRegistrar.setScheduler(getExecutor(corePoolSize, maxPoolSize, keepAliveSeconds));
    }

    protected ScheduledExecutorService getExecutor(int corePoolSize, int maxPoolSize, long keepAliveSeconds) {
        //线程名称
        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder().setNamePrefix("scheduled-pool").build();
        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(corePoolSize, namedThreadFactory, new ThreadPoolExecutor.CallerRunsPolicy());
        //最小线程数
        executor.setCorePoolSize(corePoolSize);
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        //最大线程数
        executor.setMaximumPoolSize(maxPoolSize);
        //允许空闲时间(秒)
        executor.setKeepAliveTime(keepAliveSeconds, TimeUnit.SECONDS);
        return executor;
    }

}
