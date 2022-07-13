package com.qfzc.twitter.domain.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName(value = "config")
@Data
public class ConfigEntity extends AuditModel {
    /**
     *
     */
    @TableId
    private Integer id;

    /**
     * 配置名
     */
    private String confName;

    /**
     * 描述
     */
    private String confDesc;

    /**
     * 配置值
     */
    private String confValue;

}
