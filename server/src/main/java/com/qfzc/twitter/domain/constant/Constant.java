package com.qfzc.twitter.domain.constant;

public class Constant {

    public static final class Config {

        public static final String SEARCH_NAME = "search_keyword";

        public static final String RETWEET_RULE = "retweet_rule";

        public static final String FOLLOW_RULE = "follow_rule";
    }

    public enum AccountStatus {

        /**
         * 初始状态
         */
        INIT(0),

        /**
         * 禁用
         */
        INACTIVE(1);

        private final int status;

        AccountStatus(int status) {
            this.status = status;
        }

        public int code() {
            return status;
        }
    }

    public enum TweetStatus {

        /**
         * 初始状态
         */
        INIT(0),

        /**
         * 转推过
         */
        RETWEETED(1);

        private final int status;

        TweetStatus(int status) {
            this.status = status;
        }

        public int code() {
            return status;
        }
    }

    public enum TweetInsertType {

        /**
         * 初始状态
         */
        INIT(0),

        /**
         * 定时根据关键字搜索获取的
         */
        KEYWORD(1);

        private final int status;

        TweetInsertType(int status) {
            this.status = status;
        }

        public int code() {
            return status;
        }
    }

}
