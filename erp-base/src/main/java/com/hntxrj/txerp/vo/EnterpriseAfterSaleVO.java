package com.hntxrj.txerp.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class EnterpriseAfterSaleVO {

    private Integer asid;

    private Integer eid;

    private String enterpriseName;

    /* 售后内容 */
    private String afContext;

    /* project id */
    private Integer pid;

    private String projectName;

    // 线数
    private Integer lineNumber;

    /* 合同金额 */
    private BigDecimal contractNumber;


    private Date singDate;

    private Date expireTime;

    /* 合同负责人 */
    private Integer headUid;

    private String headName;

    private String headPhone;
    // 备注
    private String remark;

}
