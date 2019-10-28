package com.hntxrj.txerp.server;

import com.alibaba.fastjson.JSONArray;
import com.hntxrj.txerp.vo.DriverVO;
import com.hntxrj.txerp.vo.TaskJumpVO;

import java.util.List;
import java.util.Map;

/**
 * 司机服务
 *
 * @update 2019-09-10 15:56:18
 */
public interface DriverService {


    /**
     * 司机排班数据获取
     *
     * @param compId        企业id
     * @param stirId        线号
     * @param vehicleStatus 车辆状态
     * @param vehicleClass  班次
     * @return {@link JSONArray}
     */
    JSONArray getDriverScheduling(String compId, String stirId, String vehicleStatus,
                                  String vehicleClass);


    List<DriverVO> getDriverList(String compid, String token, String driverName);

    List<TaskJumpVO> getJumpVehicleList(String compid, String token, String taskId);

    void taskSaleInvoiceReceipt(String receiptPeople, Double receiptNum,
                                String jumpVehicle, String sign, String invoiceId);

    Map<String, String> getDriverName(String compid, String driverCode);
    String getDriverNames(String compid, String driverCode);
}


