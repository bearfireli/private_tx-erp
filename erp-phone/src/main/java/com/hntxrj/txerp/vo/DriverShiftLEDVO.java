package com.hntxrj.txerp.vo;

import lombok.Data;

/**
 * @author qyb
 * @ClassName DriverShiftLEDVO
 * @Description TODO
 * @Date 19-6-18 下午2:31
 * @Version 1.0
 **/
@Data
public class DriverShiftLEDVO {


    /**
     * 线号
     */
    private  String stirId;
    private  String stirName;

    /**
     * 车号
     */
    private  String carID;
    /**
     * 车状态  3 正在生产  1 等待生产
     */
    private String VehicleStatus;

    private String statusName;
}

