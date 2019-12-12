package com.hntxrj.txerp.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ProductDriverListvo implements Serializable {
    private int vehicleStatus;  //车辆状态
    private String vehicleStatusName;  //车辆状态名称
    private List cars;  //每个线号的车辆集合
}
