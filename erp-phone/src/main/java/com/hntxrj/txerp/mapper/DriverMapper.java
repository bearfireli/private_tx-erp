package com.hntxrj.txerp.mapper;

import com.hntxrj.txerp.entity.GpsLocateTempInfo;
import com.hntxrj.txerp.vo.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Mapper
public interface DriverMapper {

    /**
     * 获取司机列表
     *
     * @param compid     企业
     * @param driverName 司机姓名（模糊搜索）
     * @return 司机列表
     */
    List<DriverVO> getDriverList(String compid, String driverName);

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
     * @param receiptPeople    签收人
     * @param numberOfSignings 签收数量
     * @param pumpVehicle      泵车
     * @param sign             签字图片
     * @param invoiceId        小票代号
     * @param signingTime      签收时间
     */
    void taskSaleInvoiceReceipt(String receiptPeople, Double numberOfSignings,
                                String pumpVehicle, String sign, String invoiceId, String signingTime);

    /**
     * 获取司机姓名
     *
     * @param compid     企业代号
     * @param driverCode 司机代号
     */
    String getDriverName(String compid, String driverCode);


    /**
     * 根据司机编号和司机姓名对应关系
     *
     * @param compid 企业id
     */
    List<DriverVO> getDriverNames(String compid);

    /**
     * 小票详情
     *
     * @param invoiceId 小票id
     * @param compid    企业代号
     */
    TaskSaleInvoiceDetailVO driverGetTaskSaleInvoiceDetail(Integer invoiceId, String compid);


    /**
     * 获取小票签收列表
     *
     * @param invoiceId   小票id（模糊查询）
     * @param compid      企业
     * @param beginTime   开始时间
     * @param endTime     结束时间
     * @param eppCode     工程代号
     * @param upStatus    签收状态
     * @param builderCode 施工单位代号
     * @param placing     浇筑部位
     * @param driverCode  司机代号
     * @return 小票签收列表
     */
    List<TaskSaleInvoiceDriverListVO> driverGetTaskSaleInvoiceList(String invoiceId, String compid, String beginTime,
                                                                   String endTime, String eppCode, Byte upStatus,
                                                                   String builderCode, String placing, String driverCode);


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
     * @return 小票签收列表
     */
    TaskSaleInvoiceSumVO getTaskSaleInvoiceSum(String invoiceId, String compid, String beginTime, String endTime,
                                               String eppCode, Byte upStatus, String builderCode, String placing,
                                               String driverCode);

    /**
     * 保存司机打卡时间
     *
     * @param compid     企业
     * @param driverCode 司机代号
     * @param workTime   司机打卡时间
     * @param timeType   司机打卡类型  0:上班打卡    1：下班打卡
     * @param dateTime   打卡日期
     * @param cardNumber 打卡次数
     */
    void saveDriverWorkTime(Integer timeType, String compid, String driverCode, String workTime, String dateTime,
                            Integer cardNumber);

    /**
     * 查询司机当天打卡时间
     *
     * @param compid     企业
     * @param driverCode 司机代号
     * @param dateTime   查询日期
     */
    List<DriverWorkTimeVO> getDriverWorkTime(String compid, String driverCode, String dateTime);

    /**
     * 修改司机打卡时间
     *
     * @param compid     企业
     * @param driverCode 司机代号
     * @param workTime   司机打卡时间
     * @param timeType   司机打卡类型  0:上班打卡    1：下班打卡
     * @param dateTime   打卡日期
     */
    void updateDriverWorkTime(Integer timeType, String compid, String driverCode, String workTime, String dateTime,
                              Integer cardNumber);

    /**
     * 保存签收人手写图片
     *
     * @param compid           用户手写签名
     * @param saleFileImage    图片名称
     * @param invoiceId        小票id
     * @param signingTime      签订时间
     * @param numberOfSignings 签收方量
     */
    void saveSaleFileImage(String compid, String saleFileImage, String invoiceId, String signingTime,
                           Double numberOfSignings, String jumpVehicle);

    /**
     * 编辑签收方量
     *
     * @param compid           公司代号
     * @param numberOfSignings 签收方量
     * @param invoiceId        小票id
     */
    void saveNumberOfSignings(String compid, Double numberOfSignings, String invoiceId, String signingTime);

    /**
     * 修改小票中的车辆状态
     *
     * @param compid        企业
     * @param invoiceId     小票id
     * @param vehicleStatus 车辆状态   13：正在卸料； 14：卸料完毕  16:自动回厂
     * @param dateTime      修改车辆状态时间
     */
    void updateInvoiceVehicleStatus(String compid, Integer invoiceId, Integer vehicleStatus, Date dateTime);

    /**
     * 修改车辆表中的车辆状态
     *
     * @param compid        企业
     * @param vehicleId     小票id
     * @param vehicleStatus 车辆状态  1：场内待班
     * @param dateTime      修改车辆状态时间
     */
    void updateVehicleStatus(String compid, String vehicleId, Integer vehicleStatus, Date dateTime);

    //获取小票的车辆状态
    DriverTaskSaleDetailVO getTaskSaleInvoiceDetail(String driverCode, String compid);


    GpsLocateTempInfo getDriverLocation(String compid, String vehicleId);

    List<DriverVehicleCountVO> driverVehicleCount(String compid, String driverCode, String beginTime, String endTime);

    // 小票绑定泵车号
    void bindDriverToInvoice(Integer id, String compid, String vehiclePump);

    Map<String, String> queryOneInvoice(Integer id, String compid);
}
