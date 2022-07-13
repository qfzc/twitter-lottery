package com.qfzc.twitter.infrastructure.twitter.v1;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.TimeZone;

public class StrTest {
    public static final String DATE_PATTERN_SIMPLE = "yyyyMMdd";
    public static final String DATE_PATTERN_LARGE = "yyyyMMddHHmm";

    public static void main(String[] args) throws ParseException {
        String stringDate = "20170101";

        SimpleDateFormat formatter = new SimpleDateFormat(DATE_PATTERN_LARGE);
        if (stringDate.length() == 8) {
            formatter = new SimpleDateFormat(DATE_PATTERN_SIMPLE);
        }
        TimeZone.getTimeZone("UTC");

        System.out.println(ZoneId.of("UTC").toString());
        LocalDateTime localDateTime = formatter.parse(stringDate).toInstant()
                .atZone(ZoneId.of("UTC"))
                .toLocalDateTime();

        System.out.println(localDateTime.toString());


        LocalDateTime date = LocalDateTime.now()
                .atZone(ZoneId.of("UTC"))
                .toLocalDateTime();
        System.out.println(date.toString());
    }
}
