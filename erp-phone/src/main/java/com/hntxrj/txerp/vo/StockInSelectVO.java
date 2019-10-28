package com.hntxrj.txerp.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

@Data
/*原材料过磅查询详情*/
public class StockInSelectVO {

    private String stiCode;
    /*车号*/
    private String vehicleId;
    /*毛重*/
    private BigDecimal grWeight;
    /*皮重*/
    private BigDecimal taWeight;
    /*净重*/
    private BigDecimal netWeight;
    /*另扣率*/
    private BigDecimal deduct;
    /*过磅员1*/
    private String oper1Name;
    /*过磅员2*/
    private String oper2Name;
    /*另扣量*/
    private BigDecimal deductNum;
    /*备注*/
    private String remarks;
    /*出场次数*/
    private Integer stlTimes;
    /*二次过磅时间*/
    private Timestamp secondTime;
   /*初次过磅时间*/
    private Timestamp firstTime;
    /*出厂净重*/
    private BigDecimal supNetWeight;
    /*结算净重*/
    private double clWeight;
    /*出厂油升量*/
    private BigDecimal supOilNum;
    /*称重次数*/
    private Date supExFactory;
    /*供货商*/
    private String supName;
    /*材料*/
    private  String matName;
    /*库位*/
    private String stoName;
    /*业务类别*/
    private String ptTypeName;
    /*状态*/
    private boolean stiStatus;

}
