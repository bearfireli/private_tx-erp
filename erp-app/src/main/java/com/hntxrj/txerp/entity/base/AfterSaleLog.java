package com.hntxrj.txerp.entity.base;

import java.util.Date;
import java.io.Serializable;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class AfterSaleLog implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    /* 售后单id */
    private Integer aslId;
    /* 创建人 */
    private Integer createUid;
    /* 创建时间 */
    private Date createTime;
    /* 客户联系人 */
    private String customerLinkMan;
    /* 客户联系电话 */
    private String customerLinkPhone;
    /* 紧急级别 */
    private Integer grade;
    /* 问题描述 */
    private String issueContext;
    /* 售后人员 */
    private Integer afUid;
    /* 售后方式 */
    private String afType;
    /* 售后开始时间 */
    private Date afBeginTime;
    /* 售后完成时间 */
    private Date afEndTime;
    /* 售后因果 */
    private String afSummary;
    /* 附件 */
    private String files;
    /* 状态0未处理，1已处理，2正在处理 */
    private Integer aslStatus;

}
