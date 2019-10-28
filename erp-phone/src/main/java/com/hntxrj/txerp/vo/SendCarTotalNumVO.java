package com.hntxrj.txerp.vo;

import lombok.Data;

import java.util.Date;


@Data
public class SendCarTotalNumVO {
    private  double conuntNum;
    private double todayTotalNum;
    private double yesterdayTotalNum;
    private double monthTotalNum;
    private Date time;
}
