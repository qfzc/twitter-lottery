package com.qfzc.twitter.domain.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * Twitter用户列表
 *
 * @TableName twitter_user
 */
@TableName(value = "twitter_user")
@Data
public class TwitterUserEntity extends AuditModel {
    /**
     * Twitter用户ID
     */
    @TableId
    private String id;

    /**
     * Twitter用户名称
     */
    private String name;

    /**
     * Twitter昵称
     */
    private String nickName;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 个人URL地址
     */
    private String homeUrl;

    /**
     * 要包含在结果集中的最小推文 ID。如果两者都指定，则此参数优先于 start_time。
     */
    private String sinceId;

}