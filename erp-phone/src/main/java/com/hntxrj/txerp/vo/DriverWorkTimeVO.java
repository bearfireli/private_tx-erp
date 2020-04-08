package com.hntxrj.txerp.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class DriverWorkTimeVO implements Serializable {

    private int id;
    private String beginTime;  //上班时间
    private String endTime;   //下班时间
    private String cardNumber;//打卡次数

}
