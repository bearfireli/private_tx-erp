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

    List<DriverShiftLEDVO> zeroStirIdCars;    //0号线所有车辆
    List<DriverShiftLEDVO> firstStirIdCars;    //一号线所有车辆
    List<DriverShiftLEDVO> secondStirIdCars;   //二号线所有车辆
    List<DriverShiftLEDVO> thirdStirIdCars;    //三号线所有车辆


}
