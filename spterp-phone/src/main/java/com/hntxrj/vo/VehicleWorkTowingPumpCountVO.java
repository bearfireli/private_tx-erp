package com.hntxrj.vo;

import lombok.Data;

import java.sql.Timestamp;

/**
 * @author qyb
 * @ClassName VehicleWorkTowingPumpDetailVO
 * @Description 　搅拌车拖水拖泵汇总
 * @Date 19-6-3 上午11:25
 * @Version 1.0
 **/
@Data
public class VehicleWorkTowingPumpCountVO {
    /**
     *车辆代号
     */
    private String vehicleId;
    /**
     *运输车数
     */
    private Integer carNumList;
    private String personalName;
}
