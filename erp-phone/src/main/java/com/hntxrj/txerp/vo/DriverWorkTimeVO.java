package com.hntxrj.txerp.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class DriverWorkTimeVO implements Serializable {

    private String compid;
    private String driverCode; //司机代号
    private String beginTime;  //上班时间
    private String endTime;   //下班时间
    private String dateTime;  //打卡日期
}
