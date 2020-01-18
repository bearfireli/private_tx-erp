package com.hntxrj.txerp.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class DriverWaitLEDVO implements Serializable {
    //决定司机App等待派车的车辆是否显示
    private Boolean isShow;
    //决定司机端App等待派车的车辆显示几辆
    private int value;
}
