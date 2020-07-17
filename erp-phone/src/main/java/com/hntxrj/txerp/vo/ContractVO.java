package com.hntxrj.txerp.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
public class ContractVO implements Serializable {

    private String contractCode;
    private String contractUid;
    private String contractDetailCode;
    private Integer compId;

    private String eppName;

    private String builderName;
    private String builderCode;
    private String eppCode;

    private BigDecimal contractNum;  //合同方量
    private BigDecimal preNum;
    private Integer contractStatus;
    private String contractStatusName;

    private Timestamp signDate;    // 签订时间

    private String salesman;
    private String scaleName;

    private Timestamp expiresDate;   //到期时间
    private String address;

    private String remarks;
    private Integer verifyStatus;

    private Integer contractType;  //合同类型
    private String contractTypeName;

    private Integer priceStyle;  //合同价格类型
    private String priceStyleName;

    private String linkMan;     //合同联络人
    private String linkTel;     //合同联络人电话
    private Double preMoney;    //预付金额


}
