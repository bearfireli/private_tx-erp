package com.hntxrj.txerp.entity;

import java.io.Serializable;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "sm_contractpricemarkup")
public class ContractPriceMarkup implements Serializable {

    /* 站别代号 */
    @Column(name = "compid")
    private String compid;
    /* 子合同号 */
    @Column(name = "ccontractcode")
    private String cContractCode;
    /* 主合同UID号 */
    @Column(name = "contractuid")
    private String contractUid;
    /* 项目代号 */
    @Id
    @Column(name = "ppcode")
    private String ppCode;
    /* 特殊材料名称 */
    @Column(name = "ppname")
    private String ppName;
    /* 单位 */
    @Column(name = "measureunit")
    private String measureUnit;
    /* 价格 */
    @Column(name = "unitprice")
    private BigDecimal unitPrice;
    /* 泵送价 */
    @Column(name = "jumpprice")
    private BigDecimal jumpPrice;
    /* 自卸价 */
    @Column(name = "selfdiscprice")
    private BigDecimal selfDiscPrice;
    /* 塔吊价 */
    @Column(name = "towercraneprice")
    private BigDecimal towerCranePrice;
    /* 其他价格 */
    @Column(name = "otherprice")
    private BigDecimal otherPrice;
    /* 记录状态 */
    @Column(name = "recstatus")
    private String recStatus;
    /* 网络标识 */
    @Column(name = "updown")
    private String upDown;
    /* 默认否 */
    @Column(name = "isdefault")
    private String isDefault;
    /* 引用状态 */
    @Column(name = "quotesstatus")
    private String quotesStatus;
    /* 更新标识 */
    @Column(name = "updownmark")
    private Integer upDownMark;

    /*ID*/
    @Column(name = "ID")
    private String id;
    /*到期时间*/
    @Column(name = "PriceETime")
    private String priceETime;
    /*价格停止时间*/
    @Column(name = "PriceStopTime")
    private String priceStopTime;

}
