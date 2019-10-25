package com.hntxrj.vo;

import lombok.Data;

/**
 * @author qyb
 * @ClassName PumpTruckCountVO
 * @Description 泵车汇总统计
 * @Date 19-6-3 下午7:38
 * @Version 1.0
 **/
@Data
public class PumpTruckCountVO {
    /**
     *泵车号
     */
    private  String vehicleId;
    /**
     *砼产量
     */
    private double num;
    /**
     *车数
     */
    private String carNum;
    /**
     *司机名称
     */
    private  String personalName;
    private  String typeName;
}
