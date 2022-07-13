package com.qfzc.twitter.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 钱包账号地址
 *
 * @TableName wallet_address
 */
@TableName(value = "wallet_address")
@Data
public class WalletAddressEntity extends AuditModel {
    /**
     * ID
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 钱包地址
     */
    private String address;

}