package com.qfzc.twitter.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 参与抽奖的账号
 *
 * @TableName account
 */
@TableName(value = "account")
@Data
public class AccountEntity extends AuditModel {

    /**
     *
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 钱包表ID
     */
    private String wId;

    /**
     * Twitter用户ID
     */
    private String tId;

    /**
     * Twitter 名
     */
    private String name;

    /**
     * 头像
     */
    private String avatar;

    /**
     * @好友及其文案
     */
    private String tag;

    /**
     * 转推次数
     */
    private Integer retCount;

    /**
     * 状态
     */
    private Integer status;

    /**
     * Twitter Api Key
     */
    private String apiKey;

    /**
     * Twitter Api Secret
     */
    private String apiSecret;

    /**
     * Authentication Tokens
     */
    private String accessToken;

    /**
     * Access Token and Secret
     */
    private String accessTokenSecret;

}
