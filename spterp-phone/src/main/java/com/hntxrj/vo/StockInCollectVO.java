package com.hntxrj.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

@Data
/*原材料明细汇总详情*/
public class StockInCollectVO {
    /*材料名称*/
    private  String matName;
    /*车号*/
    private String vehicleId;
    /*进场时间*/
    private Timestamp secondTime;
    /*出场时间*/
    private Timestamp firstTime;
    /*入库量*/
    private double clWeight;
    /*进货代号*/
    private String stICode;
    /*供应商*/
    private String supName;
    /*企业id*/
    private String compid;
    /*皮重*/
    private double TaWeight;
    private  String matSpecs;

}