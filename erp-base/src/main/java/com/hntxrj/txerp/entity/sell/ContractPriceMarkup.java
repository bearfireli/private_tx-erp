package com.hntxrj.txerp.entity.sell;

import java.io.Serializable;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Data
public class ContractPriceMarkup implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    /* 合同特殊加价项目明细id */
    private Integer cpmId;
    /* null */
    private Integer enterprise;
    /* null */
    private String cdUid;
    /* null */
    private String cmUid;
    /* 加价项目名称 */
    private String projectName;
    /* 项目代号 */
    private String projectCode;
    /* 计量单位 */
    private String measureUnit;
    /* 单价 */
    private BigDecimal unitPrice;
    /* 泵送价 */
    private BigDecimal pumpPrice;
    /* 非泵价（自卸价） */
    private BigDecimal noPumpPrice;
    /* 塔吊价 */
    private BigDecimal towerCranePrice;
    /* 其他价 */
    private BigDecimal otherPrice;
    /* 是否默认0不默认 1默认 */
    @Column(name = "`default`")
    private Integer default_;
    /* 0不引用到任务单的特殊要求中，1 引用到任务单的特殊要求中 */
    private Integer quotesStatus;
    /* null */
    private Integer del;

}
