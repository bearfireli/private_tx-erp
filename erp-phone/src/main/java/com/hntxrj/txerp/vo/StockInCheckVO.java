package com.hntxrj.txerp.vo;

import lombok.Data;

import java.sql.Timestamp;

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







}