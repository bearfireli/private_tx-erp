package com.hntxrj.txerp.vo;

import lombok.Data;

import java.util.List;

/**
 * @author qyb
 * @ClassName DirverLEDListVO
 * @Description TODO
 * @Date 19-6-18 下午3:20
 * @Version 1.0
 **/
@Data
public class DirverLEDListVO {


    private Integer status;

    private String statusName;

    List<DriverShiftLEDVO> cars;

    private Integer carNum;

    List<DriverShiftLEDVO> zeroStirIdCars;    //0号线所有正在生产的车辆
    List<DriverShiftLEDVO> firstStirIdCars;    //1号线所有正在生产车辆
    List<DriverShiftLEDVO> secondStirIdCars;   //2号线所有正在生产车辆
    List<DriverShiftLEDVO> thirdStirIdCars;    //3号线所有正在生产车辆
    List<DriverShiftLEDVO> fourthStirIdCars;    //4号线所有正在生产车辆
    List<DriverShiftLEDVO> fifthStirIdCars;    //5号线所有正在生产车辆


}
