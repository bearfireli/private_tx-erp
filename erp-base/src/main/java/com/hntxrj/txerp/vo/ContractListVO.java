package com.hntxrj.txerp.vo;

import lombok.Data;

import java.util.Date;

/**
 * 合同列表视图
 */
@Data
public class ContractListVO {

    /**
     * 主合同id
     */
    private String cmUid;
    private String cmId;
    /**
     * 子合同id
     */
    private String cdUid;
    private String cdId;
    /**
     * 主合同类别
     */
    private Integer cmType;

    /**
     * 工程名称
     */
    private Integer engineeringId;
    private String engineeringCode;
    private String engineeringName;
    private String engineeringLinkMan;
    private String engineeringLinkTel;

    /**
     * 施工单位
     */
    private Integer builderId;
    private String builderCode;
    private String builderName;


    /**
     * 销售员
     */
    private Integer salesmanUid;
    private String salesmanName;


    private Integer contractStatus;


    private Integer createUid;
    private String createUserName;


    private Integer verifyStatus;
    private Integer verifyUser;
    private String verifyUserName;
    private Date verifyTime;


    private Integer secondVerifyStatus;
    private Integer secondVerifyUser;
    private String secondVerifyUserName;
    private Date secondVerifyTime;

    /**
     * 送货地址
     */
    private String shippingAddress;

    /**
     * 开盘时间
     */
    private Date openTime;


}
