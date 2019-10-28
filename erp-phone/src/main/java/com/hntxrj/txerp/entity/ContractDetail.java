package com.hntxrj.txerp.entity;

import java.util.Date;
import java.io.Serializable;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Table;
import java.math.BigDecimal;

@Data
@Table(name = "SM_ContractDetail")
public class ContractDetail implements Serializable {

    /* 主合同UID号 */
    @Column(name = "contractUid")
    private String contractUid;
    /* 子合同号 */
    @Column(name = "cContractCode")
    private String cContractCode;
    /* null */
    @Column(name = "compid")
    private String compid;
    /* 项目代号 */
    @Column(name = "eppCode")
    private String eppCode;
    /* 施工单位代号 */
    @Column(name = "builderCode")
    private String builderCode;
    /* 送货地址 */
    @Column(name = "address")
    private String address;
    /* 最小泵送量 */
    @Column(name = "minJumpNum")
    private BigDecimal minJumpNum;
    /* 泵送加价金额 */
    @Column(name = "jumpPriceMarkup")
    private BigDecimal jumpPriceMarkup;
    /* 预付金额 */
    @Column(name = "preMoney")
    private BigDecimal preMoney;
    /* 预付方量 */
    @Column(name = "preNum")
    private BigDecimal preNum;
    /* 担保金额 */
    @Column(name = "suretyMoney")
    private BigDecimal suretyMoney;
    /* 合同方量 */
    @Column(name = "contractNum")
    private BigDecimal contractNum;
    /* 结账方量 */
    @Column(name = "accountNum")
    private BigDecimal accountNum;
    /* 以生产金额 */
    @Column(name = "produceMoney")
    private BigDecimal produceMoney;
    /* 结账部位 */
    @Column(name = "accountPlace")
    private String accountPlace;
    /* 是否含税 */
    @Column(name = "isTax")
    private String isTax;
    /* 税额 */
    @Column(name = "taxRate")
    private BigDecimal taxRate;
    /* 区间(距离) */
    @Column(name = "distance")
    private BigDecimal distance;
    /* 材料状况 */
    @Column(name = "matStatus")
    private Integer matStatus;
    /* 合同状态 */
    @Column(name = "contractStatus")
    private Integer contractStatus;
    /* 状态修改时间 */
    @Column(name = "statusTime")
    private Date statusTime;
    /* 审核状态 */
    @Column(name = "verifyStatus")
    private String verifyStatus;
    /* 审核人代码 */
    @Column(name = "verifyId")
    private String verifyId;
    /* 审核时间 */
    @Column(name = "verifyTime")
    private Date verifyTime;
    /* 创建人代码 */
    @Column(name = "opId")
    private String opId;
    /* 创建时间 */
    @Column(name = "createTime")
    private Date createTime;
    /* 工地是否GPS标定 */
    @Column(name = "isGpsLocate")
    private String isGpsLocate;
    /* 备注 */
    @Column(name = "remarks")
    private String remarks;
    /* 网络标识 */
    @Column(name = "upDown")
    private String upDown;
    /* 记录状态(有效) */
    @Column(name = "recStatus")
    private String recStatus;
    /* null */
    @Column(name = "mortarModule")
    private Integer mortarModule;
    /* null */
    @Column(name = "mortarStgid")
    private String mortarStgid;
    /* null */
    @Column(name = "waterModule")
    private Integer waterModule;
    /* null */
    @Column(name = "waterStgid")
    private String waterStgid;
    /* null */
    @Column(name = "waterAccountNum")
    private BigDecimal waterAccountNum;
    /* null */
    @Column(name = "noloadModule")
    private Integer noloadModule;
    /* null */
    @Column(name = "noloadOderMinNum")
    private BigDecimal noloadOderMinNum;
    /* null */
    @Column(name = "noloadCarMinNum")
    private BigDecimal noloadCarMinNum;
    /* null */
    @Column(name = "noloadMoney")
    private BigDecimal noloadMoney;
    /* null */
    @Column(name = "tableExpenseModule")
    private Integer tableExpenseModule;
    /* null */
    @Column(name = "upDownMark")
    private Integer upDownMark;
    /* 空载收费方式 */
    @Column(name = "noloadMoneyStyle")
    private Integer noloadMoneyStyle;
    /* 允许尾数次数 */
    @Column(name = "noloadAllowsNumber")
    private Integer noloadAllowsNumber;
    /* 空载费计量方量 */
    @Column(name = "noloadMoneyNumber")
    private BigDecimal noloadMoneyNumber;
    /* 二级审核状态 */
    @Column(name = "secondVerifyStatus")
    private String secondVerifyStatus;
    /* 二级审核人 */
    @Column(name = "secondVerifyName")
    private String secondVerifyName;
    /* 二级审核时间 */
    @Column(name = "secondVerifyTime")
    private Date secondVerifyTime;
    /* 开盘时间 */
    @Column(name = "openTime")
    private Date openTime;

}
