package com.qfzc.twitter.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 抽奖账户关联表
 *
 * @TableName account_rel
 */
@TableName(value = "account_rel")
@Data
public class AccountRelEntity extends AuditModel {
    /**
     *
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * acount用户表ID
     */
    private Integer wId;

    /**
     * Twitter用户表ID
     */
    private String tId;

}
