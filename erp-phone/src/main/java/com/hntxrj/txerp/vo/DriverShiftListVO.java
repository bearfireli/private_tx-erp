package com.hntxrj.txerp.vo;

import lombok.Data;

import java.sql.Timestamp;

/**
 * @author qyb
 * @ClassName DriverShiftLis
 * @Description 司机排班列表信息
 * @Date 19-6-18 下午5:13
 * @Version 1.0
 **/
@Data
public class DriverShiftListVO {

    //司机代码
    private  String personalCode;
    //司机名称
    private  String personalName;
    //备注
    private  String remarks;
    //车号
    private String vehicleId;
    //班次代码
    private  Integer workClass;
    //班次
    private  String workClassName;
    //上班时间
    private Timestamp workOverTime;
    //下班时间
    private Timestamp workStarTime;
    //id
    private String id;


}
