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


    /**
     * 获取司机列表
     *
     * @param compid     企业
     * @param token      令牌
     * @param driverName 司机姓名（模糊搜索）
     * @return 司机列表
     */
    List<DriverVO> getDriverList(String compid, String token, String driverName);

    /**
     * 获取任务单泵车列表
     *
     * @param compid 企业
     * @param taskId 任务单号
     * @return 泵车列表
     */
    List<TaskJumpVO> getJumpVehicleList(String compid, String token, String taskId);

    /**
     * 小票签收
     *
     * @param receiptPeople 签收人
     * @param receiptNum    签收数量
     * @param jumpVehicle   泵车
     * @param sign          签字图片
     * @param invoiceId     小票代号
     */
    void taskSaleInvoiceReceipt(String receiptPeople, Double receiptNum,
                                String jumpVehicle, String sign, String invoiceId);

    /**
     * 获取司机姓名
     *
     * @param compid     企业代号
     * @param driverCode 司机代号
     */
    Map<String, String> getDriverName(String compid, String driverCode);


    /**
     * 根据司机编号查询司机姓名
     */
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

    /**
     * 获取小票签收汇总
     *
     * @param invoiceId   小票id
     * @param compid      企业
     * @param beginTime   开始时间
     * @param endTime     结束时间
     * @param eppCode     工程代号
     * @param builderCode 施工单位代号
     * @param driverCode  司机代号
     * @param placing     浇筑部位
     * @param upStatus    签收状态
     * @param page        页数
     * @param pageSize    每页数量
     * @return 小票签收汇总
     */
    TaskSaleInvoiceSumVO getTaskSaleInvoiceSum(Integer invoiceId, String compid, String beginTime, String endTime, String eppCode, Byte upStatus, String builderCode, String placing, Integer page, Integer pageSize, String driverCode);

    /**
     * 保存司机打卡时间
     *
     * @param compid     企业
     * @param driverCode 司机代号
     * @param workTime   司机打卡时间
     * @param timeType   司机打卡类型  0:上班打卡    1：下班打卡
     */
    void saveDriverWorkTime(String compid, String driverCode, String workTime, Integer timeType);

    /**
     * 查询司机当天打卡时间
     *
     * @param compid     企业
     * @param driverCode 司机代号
     * @param queryTime  查询日期
     */
    DriverWorkTimeVO getDriverWorkTime(String compid, String driverCode, String queryTime);

    /**
     * 获取小票详情
     *
     * @param compid    企业
     * @param invoiceId 小票id
     * @return 小票签收列表
     */
    TaskSaleInvoiceDetailVO getTaskSaleInvoiceDetail(String compid, Integer invoiceId);

    /**
     * 保存签收人手写图片
     *
     * @param saleFileImage 用户手写签名
     * @param invoiceId     小票id
     */
    void saveSaleFileImage(String saleFileImage, String invoiceId, String compid, Double receiptNum);

    /**
     * 编辑签收方量
     *
     * @param compid     公司代号
     * @param receiptNum 签收方量
     * @param invoiceId  小票id
     */
    void saveNumberOfSignings(String compid, Double receiptNum, String invoiceId);

    /**
     * 修改小票中的车辆状态
     *
     * @param compid        企业
     * @param invoiceId     小票id
     * @param vehicleStatus 车辆状态   13：正在卸料； 14：卸料完毕
     */
    void updateVehicleStatus(String compid, Integer invoiceId, Integer vehicleStatus);
}


