package com.hntxrj.txerp.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class DriverTaskSaleDetailVO implements Serializable {
    private Integer id;
    private String compid;
    private Date leaveTime;     //离厂时间
    private Integer vehicleStatus;      //车辆状态

}
