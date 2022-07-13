package com.qfzc.twitter.domain.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 推文
 * @TableName tweet
 */
@TableName(value ="tweet")
@Data
public class TweetEntity extends AuditModel {
    /**
     * 推文ID
     */
    @TableId
    private String id;

    /**
     * 推文文本
     */
    private String text;

    /**
     * 使用它来确定这条推文是否是对另一条推文的回复
     */
    private String inReplyToUserId;

    /**
     * 原始tweetID，表示当前这条是原始tweet下的评论
     */
    private String conversationId;

    /**
     * 推文作者(Twitter用户ID)
     */
    private String authorId;

    /**
     * 推文创建时间
     */
    private Date createdAt;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 写入场景
     */
    private Integer insertType;

    /**
     * 对应的参与抽奖的钱包账户
     */
    @TableField(exist = false)
    private String walletAddress;

}
