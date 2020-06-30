package com.hntxrj.txerp.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * SM_ContractDetail
 *
 * @author 刘浩然
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ContractDetail extends ContractDetailKey implements Serializable {
    private String compid;

    /**
     * 项目代号
     */
    private String eppCode;

    /**
     * 施工单位代号
     */
    private String builderCode;

    /**
     * 送货地址
     */
    private String address;

    /**
     * 最小泵送量
     */
    private BigDecimal minJumpNum;

    /**
     * 泵送加价金额
     */
    private BigDecimal jumpPriceMarkup;

    /**
     * 预付金额
     */
    private BigDecimal preMoney;

    /**
     * 预付方量
     */
    private BigDecimal preNum;

    /**
     * 担保金额
     */
    private BigDecimal suretyMoney;

    /**
     * 合同方量
     */
    private BigDecimal contractNum;

    /**
     * 结账方量
     */
    private BigDecimal accountNum;

    /**
     * 以生产金额
     */
    private BigDecimal produceMoney;

    /**
     * 结账部位
     */
    private String accountPlace;

    /**
     * 是否含税
     */
    private Boolean isTax;

    /**
     * 税额
     */
    private BigDecimal taxRate;

    /**
     * 区间(距离)
     */
    private BigDecimal distance;

    /**
     * 材料状况
     */
    private Integer matStatus;

    /**
     * 合同状态
     */
    private Integer contractStatus;

    /**
     * 状态修改时间
     */
    private Date statusTime;

    /**
     * 审核状态
     */
    private Boolean verifyStatus;

    /**
     * 审核人代码
     */
    private String verifyId;

    /**
     * 审核时间
     */
    private Date verifyTime;

    /**
     * 创建人代码
     */
    private String opId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 工地是否GPS标定
     */
    private Boolean isGPSLocate;

    /**
     * 备注
     */
    private String remarks;

    /**
     * 网络标识
     */
    private Boolean upDown;

    /**
     * 记录状态(有效)
     */
    private Boolean recStatus;

    private Integer mortarModule;

    private String mortarStgid;

    private Integer waterModule;

    private String waterStgid;

    private BigDecimal waterAccountNum;

    private Integer noloadModule;

    private BigDecimal noloadOderMinNum;

    private BigDecimal noloadCarMinNum;

    private BigDecimal noloadMoney;

    private Integer tableExpenseModule;

    private Integer upDownMark;

    /**
     * 空载收费方式
     */
    private Integer noloadMoneyStyle;

    /**
     * 允许尾数次数
     */
    private Integer noloadAllowsNumber;

    /**
     * 空载费计量方量
     */
    private BigDecimal noloadMoneyNumber;

    /**
     * 二级审核状态
     */
    private Boolean secondVerifyStatus;

    /**
     * 二级审核人
     */
    private String secondVerifyName;

    /**
     * 二级审核时间
     */
    private Date secondVerifyTime;

    /**
     * 开盘时间
     */
    private Date openTime;

    private BigDecimal rebateRatio;

    private BigDecimal rebateAmount;

    private static final long serialVersionUID = 1L;
}