package com.hntxrj.txerp.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
public class ContractVO {

    private String contractCode;
    private String contractUid;
    private String contractDetailCode;
    private Integer compId;

    private String eppName;

    private String builderName;
    private String builderCode;
    private String eppCode;
    /**
     * 合同方量
     */
    private BigDecimal contractNum;
    private BigDecimal preNum;
    private Integer contractStatus;
    private String contractStatusName;
    /**
     * 签订时间
     */
    private Timestamp signDate;

    private String salesman;
    private String scaleName;
    /**
     * 到期时间
     */
    private Timestamp expiresDate;
    private String address;

    private String remarks;
    private Integer verifyStatus;
    /**
     * 合同类型
     */
    private Integer contractType;
    private String contractTypeName;
    /**
     * 合同价格类型
     */
    private Integer priceStyle;
    private String priceStyleName;


}
