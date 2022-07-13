package com.qfzc.twitter.util;

import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;

/**
 * @author liang.qfzc@gmail.com
 * @date 2022/6/1
 */
@Slf4j
public class CommonUtil {

    public static void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            log.error("sleep error：", e);
        }
    }

    /**
     * 转换为UTC时间
     *
     * @param offset 偏移分钟
     * @return
     */
    public static LocalDateTime transUtcTime(int offset) {
        final Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MINUTE, -offset);
        return cal.getTime().toInstant()
                .atZone(ZoneId.of("UTC"))
                .toLocalDateTime();
    }
}
