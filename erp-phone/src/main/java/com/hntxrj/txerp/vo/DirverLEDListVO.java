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

    public static final int WAIT_VEHICLE_STATUS = 1;            //等待生产的车辆的状态
    public static final int PRODUCTION_VEHICLE_STATUS = 3;      //正在生产
    public static final int REPAIR_VEHICLE_STATUS = 4;          //维修
    public static final int REST_VEHICLE_STATUS = 5;            //休息
    public static final int WATER_VEHICLE_STATUS = 8;           //工地托水
    public static final int PUMP_VEHICLE_STATUS = 9;            //工地拖泵
    public static final int WATER_PUMP_VEHICLE_STATUS = 10;      //工地脱水拖泵
    public static final int FACTORY_WATER_VEHICLE_STATUS = 11;   //场内托水
    public static final int BACK_VEHICLE_STATUS = 16;            //自动回厂


}
