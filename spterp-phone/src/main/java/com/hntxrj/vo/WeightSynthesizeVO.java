package com.hntxrj.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

@Data
/*综合信息*/
public class WeightSynthesizeVO {
    /*日进货量*/
    private double tlWeight;
    /*车数*/
    private int vCount;
    /*材料*/
    private String matName;
    /*供应商*/
    private String supName;
    /*日期*/
    private String secondTime;
}
