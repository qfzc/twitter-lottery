package com.qfzc.twitter.domain.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author liang.qfzc@gmail.com
 * @date 2022/5/26
 */
@Data
public class AuditModel implements Serializable {

    /**
     * 租户号
     */
    private String tenantId;

    /**
     * 乐观锁
     */
    private String revision;

    /**
     * 创建人
     */
    private String createdBy;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createdTime;

    /**
     * 更新人
     */
    private String updatedBy;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.UPDATE)
    private Date updatedTime;

    public void created(String id) {
        this.createdBy = id;
    }
}
