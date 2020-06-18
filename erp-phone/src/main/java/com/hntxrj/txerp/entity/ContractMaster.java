package com.hntxrj.txerp.entity;


import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Table(name = "SM_ContractMaster")
public class ContractMaster {
    /* 站别代号 */
    @Column(name = "compid")
    private String compid;
    /* 主合同号 */
    @Column(name = "contractId")
    private String contractId;
    /* 主合同UID号 */
    @Column(name = "contractUid")
    private String contractUid;
    /* 业务员代号 */
    @Column(name = "salesman")
    private String salesman;
    /* 签订日期 */
    @Column(name = "signDate")
    private String signDate;
    /* 执行日期 */
    @Column(name = "effectDate")
    private String effectDate;
    /* 到期日期 */
    @Column(name = "expiresDate")
    private String expiresDate;
    /* 合同类别 */
    @Column(name = "contractType")
    private Integer contractType;
    /* 结账日 */
    @Column(name = "accountDay")
    private Integer accountDay;
    /* 锁定日 */
    @Column(name = "lockDate")
    private Integer lockDate;
    /* 锁定比率 */
    @Column(name = "lockRate")
    private BigDecimal lockRate;
    /* 合同联系人 */
    @Column(name = "linkMan")
    private String linkMan;
    /* 合同联系电话 */
    @Column(name = "linkTel")
    private String linkTel;
    /* 合同状态 */
    @Column(name = "contractStatus")
    private Integer contractStatus;
    /* 价格执行方式 */
    @Column(name = "priceStyle")
    private Integer priceStyle;
    /* 价格浮动范围 */
    @Column(name = "priceFloatLimits")
    private BigDecimal priceFloatLimits;
    /* 付款方式 */
    @Column(name = "paymentTerms")
    private Integer paymentTerms;
    /* 计算方式 */
    @Column(name = "computeStyle")
    private String computeStyle;
    /* 创建人代号 */
    @Column(name = "opId")
    private String opId;
    /* 创建日期 */
    @Column(name = "createTime")
    private String createTime;
    /* 网络标识 */
    @Column(name = "upDown")
    private String upDown;
    /* 记录状态(有效) */
    @Column(name = "recStatus")
    private String recStatus;
    /* null */
    @Column(name = "payRate")
    private BigDecimal payRate;
    /* null */
    @Column(name = "upDownMark")
    private Integer upDownMark;

}
