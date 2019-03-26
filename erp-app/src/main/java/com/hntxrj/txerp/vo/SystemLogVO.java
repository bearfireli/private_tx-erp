package com.hntxrj.txerp.vo;

import lombok.Data;

import java.util.Date;

@Data
public class SystemLogVO {

    /* 日志id */
    private String slId;
    /* 创建时间 */
    private Date createTime;
    /* 用户id */
    private Integer uid;//关联表字段
    /* 时间 */
    private String action;
    /* 接口 */
    private String uri;
    /* 企业id */
    private Integer enterprise;//关联表字段
    /* 类型 */
    private Integer type;
    /*页数*/
    private long page;
    /*每页记录数*/
    private long pageSize;

}
