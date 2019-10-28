package com.hntxrj.txerp.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class EnterpriseAfterSaleLogVO implements Serializable {

    /* 企业售后记录id */
    private Integer easlid;
    /* 记录标题 */
    private String logTitle;
    /* 申报人 */
    private Integer declarerUid;
    /* 申报人名称 */
    private String declarerName;
    /* 申报时间 */
    private Date declarationTime;
    /* 紧急级别 */
    private Integer grade;
    /* 联系人 */
    private String linkMan;
    /* 联系电话 */
    private String linkTel;
    /* 问题描述 */
    private String problem;
    /* 售后安排 */
    private Integer processingUid;
    /* 处理人名称 */
    private String processingUserName;
    /* 售后方式 */
    private String processingType;

    /* 预计开始时间 */
    private Date estimatedStartTime;
    /* 预计完成时间 */
    private Date estimatedOverTime;
    /* 开始时间 */
    private Date beginTime;
    /* 完成时间 */
    private Date endTime;
    /* 总结 */
    private String sumup;
    /* 附件 */
    private String files;
    /* 状态0未处理，1.正在处理，2已处理 */
    private Integer status;
    /* 所属企业 */
    private Integer enterprise;
    /* 企业名称 */
    private String enterpriseName;

    private Integer project;

    private String projectName;

    private String recordId;
}
