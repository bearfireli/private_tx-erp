package com.hntxrj.txerp.vo;

import lombok.Data;

import java.util.List;
import java.util.Map;

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

    List<DispatchVehicle> cars;

    private Integer carNum;
    //正在生产状态的车辆按照线号分类
    private Map<String, List<DispatchVehicle>> productVehicleMap;

    List<DispatchVehicle> zeroStirIdCars;    //0号线所有正在生产的车辆
    List<DispatchVehicle> firstStirIdCars;    //1号线所有正在生产车辆
    List<DispatchVehicle> secondStirIdCars;   //2号线所有正在生产车辆
    List<DispatchVehicle> thirdStirIdCars;    //3号线所有正在生产车辆
    List<DispatchVehicle> fourthStirIdCars;    //4号线所有正在生产车辆
    List<DispatchVehicle> fifthStirIdCars;    //5号线所有正在生产车辆


}
