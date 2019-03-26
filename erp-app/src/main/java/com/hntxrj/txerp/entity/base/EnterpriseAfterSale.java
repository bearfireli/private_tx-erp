package com.hntxrj.txerp.entity.base;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 企业售后表
 *
 * @author lhr
 * @create 2018年12月16日21:13:40
 */
@Data
@Entity
public class EnterpriseAfterSale implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer asid;

    private Integer eid;

    /* 售后内容 */
    private String afContext;

    /* project id */
    private Integer pid;

    // 线数
    private Integer lineNumber;

    /* 合同金额 */
    private BigDecimal contractNumber;

    /* 合同负责人 */
    private Integer headUid;

    // 备注
    private String remark;


}
