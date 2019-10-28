package com.hntxrj.txerp.entity.sell;

import java.util.Date;
import java.io.Serializable;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Data
public class ContractDetails implements Serializable {

    @Id
    /* 详情id */
    private String cdUid;
    /* 子合同代号 */
    private String cdId;
    /* 主合同id */
    private String cmUid;
    /* 工程id */
    private Integer engineeringId;
    /* 施工单位id */
    private Integer builderId;
    /* 送货地址 */
    private String address;
    /* 预付金额 */
    private BigDecimal prepayMoney;
    /* 预付方量 */
    private BigDecimal prepayNum;
    /* 担保金额 */
    private BigDecimal suretyMoney;
    /* 合同方量 */
    private BigDecimal contractNum;
    /* 结账类型 */
    private Integer accountType;
    /* 结账方量 */
    private BigDecimal accountNum;
    /* 结账部位 */
    private String accountPlace;
    /* 已生产金额 */
    private BigDecimal produceMoney;
    /* 0不含税，1含税 */
    private Integer taxIncluded;
    /* 税率 */
    private BigDecimal taxRate;
    /* 区间（距离） */
    private BigDecimal distance;
    /* 材料状态 */
    private Integer materialStatus;
    /* 合同状态 */
    private Integer contractStatus;
    /* 状态修改时间 */
    private Date statusUpTime;
    /* 审核状态 */
    private Integer verifyStatus;
    /* 审核人 */
    private Integer verifyUser;
    /* 审核时间 */
    private Date verifyTime;
    /* 二审状态 */
    private Integer secondVerifyStatus;
    /* 二审用户 */
    private Integer secondVerifyUser;
    /* 二审时间 */
    private Date secondVerifyTime;
    /* GPS标定 */
    private String gpsLocate;
    /* 砂浆计费模式 */
    private Integer mortarModel;
    /* 砂浆计费标号 */
    private String mortarStgId;
    /* 水车计费模式 */
    private Integer waterCarModel;
    /* 水车计费标准 */
    private String waterCarStgId;
    /* 水车每车收费方量 */
    private BigDecimal waterCarAccountNum;
    /* 空载费计费模式 */
    private Integer noloadModel;
    /* 允许尾数次数 */
    private Integer noloadAllowsNum;
    /* 空载费单次最小方量数 */
    private BigDecimal noloadOderMinNum;
    /* 空载费最小车方量 */
    private BigDecimal noloadCarMinNum;
    /* 空载计费金额 */
    private BigDecimal noloadMoney;
    /* 空载费计费方量 */
    private BigDecimal noloadMoneyNum;
    /* 台班费（机械费）计费模式 */
    private Integer tableExpenseModel;
    /* 最小泵送方量 */
    private BigDecimal jumpMinNum;
    /* 泵送加价金额 */
    private BigDecimal jumpPriceMarkup;
    /* 开盘时间 */
    private Date openTime;
    /* 备注1 */
    private String remark1;
    /* 备注2 */
    private String remark2;
    /* 记录有效否 */
    private Integer del;
    /* 所属企业 */
    private Integer enterprise;
    /* null */
    private Integer createUser;
    /* null */
    private Date createTime;
    /* null */
    private Integer updateUser;
    /* null */
    private Date updateTime;

}
