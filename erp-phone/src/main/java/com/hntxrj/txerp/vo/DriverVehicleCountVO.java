package com.hntxrj.txerp.vo;


import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class DriverVehicleCountVO implements Serializable {
    private String vehicleId;           // 车号
    private Integer carNum;             // 车数
    private BigDecimal concreteNum;     // 方量
    private Integer Distance;           // 运距
}
