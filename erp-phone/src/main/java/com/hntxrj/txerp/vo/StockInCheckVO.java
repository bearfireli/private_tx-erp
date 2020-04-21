package com.hntxrj.txerp.vo;

import lombok.Data;

import java.math.BigDecimal;


@Data

public class StockInCheckVO {
    /*材料名称*/
    private String matName;
    /*材料代号*/
    private String matCode;
    /*库位名称*/
    private String stoName;
    /*库位代号*/
    private String stkCode;
    /*车号*/
    private String vehicleId;


    /*进货代号*/
    private String stICode;
    /*供应商*/
    private String supName;
    /*企业id*/
    private String compid;


    /*未通过信息*/
    private String notReason;
    /*图片信息*/
    private String picturePath;
    /*是否通过*/
    private int isPassOrNot;
    private BigDecimal deductNum;
    /*检测人*/
    private String inspector;
    /*检测时间*/
    private String inspectionTime;



}