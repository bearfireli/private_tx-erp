package com.hntxrj.txerp.entity.sell;

import java.io.Serializable;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Data
public class PriceMarkup implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    /* null */
    private Integer pmId;
    /* 加价项目名称 */
    private String projectName;
    /* 项目代号 */
    private String projectCode;
    /* 价格单位 */
    private String unit;
    /* 泵送单价 */
    private BigDecimal pumpPrice;
    /* 非泵单价 */
    private BigDecimal noPumpPrice;
    /* 塔吊单价 */
    private BigDecimal towerCranePrice;
    /* 价格 */
    private BigDecimal unitPrice;
    /* 其他价格 */
    private BigDecimal otherPrice;
    /* 0不默认，1默认 */
    @Column(name = "`default`")
    private Integer default_;
    /* 是否引用到特殊要求 */
    private Integer quotesStatus;
    /* 删除否 */
    private Integer del;
    /* 企业id */
    private Integer enterprise;
}
