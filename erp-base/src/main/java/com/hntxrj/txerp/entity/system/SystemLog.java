package com.hntxrj.txerp.entity.system;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

/**
 * 系统日志
 */
@Entity
@Data
public class SystemLog implements Serializable {

    //系统日志
    public static final Integer LOG_TYPE_SYSTEM = 0;
    // 用户操作日志
    public static final Integer LOG_OPERATE = 1;
    // 用户查看日志
    public static final Integer LOG_SELECT = 2;

    /* 日志id */
    @Id
    private String slId;
    /* 创建时间 */
    @Column(insertable = false, updatable = false)
    private Date createTime;
    /* 用户id */
    private Integer uid;
    /* 时间 */
    private String action;
    /* 接口 */
    private String uri;

    /* 企业id */
    private Integer enterprise;

    /* 类型 */
    private Integer type;

}
