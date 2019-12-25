package com.hntxrj.txerp.server;

import com.alibaba.fastjson.JSONArray;
import com.hntxrj.txerp.util.jdbc.sql.Page;
import com.hntxrj.txerp.vo.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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
/**
 * 获取签收人手写图片
 **/
    void getTaskSaleInvoiceReceiptSign(String taskSaleInvoiceUploadPath, String fileName, HttpServletResponse response) throws IOException;

    /**
     * 获取小票签收列表
     *
     * @param compid      企业
     * @param beginTime   开始时间
     * @param endTime     结束时间
     * @param eppCode     工程代号
     * @param builderCode 施工单位代号
     * @param placing     浇筑部位
     * @param page        页数
     * @param pageSize    每页数量
     * @param driverCode  司机代号
     * @return 小票签收列表
     */
    PageVO<TaskSaleInvoiceDriverListVO> getTaskSaleInvoiceList(Integer id, String compid, String beginTime, String endTime, String eppCode,
                                                               Byte upStatus, String builderCode, String placing,
                                                               Integer page, Integer pageSize, String driverCode);

    TaskSaleInvoiceSumVO getTaskSaleInvoiceSum( String compid, String beginTime, String endTime, String eppCode, Byte aByte, String builderCode, String placing, Integer page, Integer pageSize, String driverCode);

    /**
     * 保存司机打卡时间
     *
     * @param compid     企业
     * @param driverCode 司机代号
     * @param workTime  司机上班时间
     * @param timeType   司机下班时间  0:上班打卡    1：下班打卡
     */
    void saveDriverWorkTime(String compid, String driverCode, String workTime, Integer timeType);
}


