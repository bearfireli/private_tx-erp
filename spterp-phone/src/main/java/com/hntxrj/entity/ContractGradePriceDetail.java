package com.hntxrj.entity;

import java.io.Serializable;

import lombok.Data;
import org.apache.http.ssl.PrivateKeyStrategy;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@Table(name = "sm_contractgradepricedetail")
@Entity
public class ContractGradePriceDetail implements Serializable {

    /* 站别代号 */
    @Column(name = "compid")
    private String compid;
    /* 子合同号 */
    @Column(name = "ccontractcode")
    private String cContractCode;
    /* 主合同UID号 */
    @Column(name = "contractuid")
    private String contractUid;
    /* 标号名称 */
    @Column(name = "stgid")
    private String stgId;
    /* 非泵价格 */
    @Column(name = "notpumpprice")
    private BigDecimal notPumpPrice;
    /* 泵送价格 */
    @Column(name = "pumpprice")
    private BigDecimal pumpPrice;
    /* 差价 */
    @Column(name = "pricedifference")
    private BigDecimal priceDifference;
    /* 塔吊价 */
    @Column(name = "towercraneprice")
    private BigDecimal towerCranePrice;
    /* 价格开始执行时间 */
    @Column(name = "priceetime")
    private String priceETime;
    /* 价格结束时间 */
    @Column(name = "pricestoptime")
    private String priceStopTime;
    /* 特性 */
    @Column(name = "attribute")
    private String attribute;
    /* 添加时间 */
    @Column(name = "createtime")
    private String createTime;
    /* 添加人代号 */
    @Column(name = "opid")
    private String opId;
    /* 审核否 */
    @Column(name = "verifystatus")
    private boolean verifyStatus;
    /* 审核人代号 */
    @Column(name = "buildercode")
    private String builderCode;
    /* 审核时间 */
    @Column(name = "verifytime")
    private String verifyTime;
    /* 网络标识 */
    @Column(name = "updown")
    private Integer upDown;
    /* null */
    @Column(name = "updownmark")
    private Integer upDownMark;
    /* null */
    @Column(name = "recstatus")
    private boolean recStatus;
    /* null */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

}

