package com.hntxrj.txerp.vo;

import lombok.Data;

/**
 * @author qyb
 * @ClassName DriverTransportationCountVO
 * @Description 司机砼运输汇总
 * @Date 19-6-3 下午5:56
 * @Version 1.0
 **/
@Data
public class DriverTransportationCountVO {
    /**
     *司机
     */
    private String personalName;
    /**
     *车号
     */
    private String vehicleId;
    /**
     *砼产量
     */
    private double num;
    /**
     *车数
     */
    private  Integer carNum;
    /**
     *运距
     */
    private double distance;
    /**
     * 砼产量合计
     */
    private double numList;
    /**
     * 车数合计
     */
    private  Integer carNumList;

}
