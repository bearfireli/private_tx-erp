package com.hntxrj.txerp.server;

import com.alibaba.fastjson.JSONArray;
import com.hntxrj.txerp.entity.PageBean;
import com.hntxrj.txerp.core.exception.ErpException;
import com.hntxrj.txerp.vo.*;

import java.util.List;
import java.util.Map;

/**
 * 功能: 搅拌车服务层接口
 *
 * @Auther 李帅
 * @Data 2017-08-15.下午 6:20
 */
public interface VehicleService {

    /**
     * 搅拌车工作量统计
     *
     * @param mark             标记
     * @param beginTime        开始日期
     * @param endTime          结束日期
     * @param taskId           任务单ID
     * @param builderShortName 工程名简称
     * @param placing          浇灌部位
     * @param eppName          工程名称
     * @param vehicleID        车号
     * @param personalCode     司机代号
     * @param compid           站别代号
     * @param pageBean         分页
     * @param pageBeanList     多组分页
     */
    JSONArray getTotalVehicle(Integer mark, String beginTime, String endTime, String taskId,
                              String builderShortName, String placing, String eppName,
                              String vehicleID, String personalCode, String compid,
                              String personalName, PageBean pageBean,
                              List<PageBean> pageBeanList, Integer id, String opid);

    /**
     * 砼产量统计
     *
     * @param TaskStatus       任务单状态
     * @param beginTime        开始时间
     * @param endTime          结束时间
     * @param compid           生产站点
     * @param StirId           搅拌楼编号
     * @param OpId             调度员
     * @param eppname          工程名
     * @param TaskId           任务单ID
     * @param BuilderShortName 施工单位名简称
     * @param ReportCode       报告编号
     * @param Placing          浇灌部位
     * @param PlaceStyle       浇灌方式
     * @param InvoiceType      小票类型
     * @param pageBean         分页
     */
    JSONArray getTotalYield(String TaskStatus, String beginTime, String endTime,
                            String compid, String StirId, String OpId,
                            String eppname, String TaskId, String BuilderShortName,
                            String ReportCode, String Placing, String PlaceStyle, String InvoiceType,
                            PageBean pageBean, String stgid, String opid);


    /**
     * 司机排班信息管理 sp_insertUpDel_VM_PersonalWorkClass
     *
     * @param mark         --操作标识 0 插入 1 更新 2 删除
     * @param compid       企业
     * @param opid         登录用户
     * @param id           操作表的主键  VM_PersonalWorkClass
     * @param vehicleid    车号
     * @param workclass    班次
     * @param personalcode 司机code
     * @param workstartime 上班时间
     * @param workovertime 下班时间
     */
    JSONArray sp_insertUpDel_VM_PersonalWorkClass(Integer mark, String compid, String opid,
                                                  String id, String vehicleid, String workclass,
                                                  String personalcode, String remarks,
                                                  String workstartime, String workovertime);


    /**
     * 搅拌车砼运输明细
     *
     * @param compid    企业
     * @param eppCode   工程代号
     * @param placing   浇筑部位
     * @param taskId    任务单号
     * @param vehicleId 车号
     * @param beginTime 开始时间
     * @param endTime   结束时间
     * @param page      分页
     * @param pageSize  每页显示条数
     */
    PageVO<VehicleWorkloadDetailVO> getVehicleWorkloadDetail(String compid, String personalName, String eppCode, String placing,
                                                             String taskId, String vehicleId,
                                                             String beginTime, String endTime,
                                                             Integer page, Integer pageSize);

    /**
     * 搅拌车砼运输汇总
     *
     * @param compid    企业
     * @param eppCode   工程代号
     * @param placing   浇筑部位
     * @param taskId    任务单号
     * @param vehicleId 车号
     * @param beginTime 开始时间
     * @param endTime   结束时间
     * @param page      分页
     * @param pageSize  每页显示条数
     * @return 乔亚北
     */
    PageVO<VehicleWorkloadSummaryVO> getVehicleWorkloadSummary(String compid, String personalName, String eppCode,
                                                               String placing, String taskId,
                                                               String vehicleId,
                                                               String beginTime, String endTime,
                                                               Integer page, Integer pageSize);

    /**
     * 搅拌车砼运输汇总合计
     *
     * @param compid    企业
     * @param eppCode   工程代号
     * @param placing   浇筑部位
     * @param taskId    任务单号
     * @param vehicleId 车号
     * @param beginTime 开始时间
     * @param endTime   结束时间
     * @param page      分页
     * @param pageSize  每页显示条数
     * @return 乔亚北
     */
    PageVO<VehicleWorkloadSummaryVO> getVehicleWorkloadSummaryCount(String compid, String personalName, String eppCode,
                                                                    String placing,
                                                                    String taskId, String vehicleId,
                                                                    String beginTime, String endTime,
                                                                    Integer page, Integer pageSize);

    /**
     * 搅拌车拖水拖泵明细
     *
     * @param compid    企业
     * @param eppCode   工程代号
     * @param placing   浇筑部位
     * @param taskId    任务单号
     * @param vehicleId 车号
     * @param beginTime 开始时间
     * @param endTime   结束时间
     * @param page      分页
     * @param pageSize  每页显示条数
     */
    PageVO<VehicleWorkTowingPumpDetailVO> getVehicleWorkTowingPumpDetail(String compid, String personalName, String eppCode,
                                                                         String placing, String taskId,
                                                                         String vehicleId,
                                                                         String beginTime, String endTime,
                                                                         Integer page, Integer pageSize);

    /**
     * 搅拌车拖水拖泵汇总
     *
     * @param compid    企业
     * @param eppCode   工程代号
     * @param placing   浇筑部位
     * @param taskId    任务单号
     * @param vehicleId 车号
     * @param beginTime 开始时间
     * @param endTime   结束时间
     * @param page      分页
     * @param pageSize  每页显示条数
     */
    PageVO<VehicleWorkTowingPumpCountVO> getVehicleWorkTowingPumpCount(String compid, String personalName, String eppCode,
                                                                       String placing, String taskId,
                                                                       String vehicleId,
                                                                       String beginTime, String endTime,
                                                                       Integer page, Integer pageSize);

    /**
     * 搅拌车过磅查询
     *
     * @param compid     企业
     * @param eppCode    工程代码
     * @param empName    过磅员
     * @param weightType 过磅类别
     * @param beginTime  开始时间
     * @param endTime    结束时间
     * @param page       分页
     * @param pageSize   每页显示条数
     */
    PageVO<WorkloadStatisticsVo> getWorkloadStatistics(String compid, String eppCode, String empName,
                                                       Integer weightType,
                                                       String beginTime, String endTime,
                                                       Integer page, Integer pageSize);

    /**
     * 司机砼运输明细
     *
     * @param compid       企业
     * @param eppCode      工程代码
     * @param placing      浇筑部位
     * @param taskId       任务单号
     * @param vehicleId    车号
     * @param personalName 司机
     * @param beginTime    开始时间
     * @param endTime      结束时间
     * @param page         分页
     * @param pageSize     每页显示条数
     */
    PageVO<DriverworkloadStatisticsVO> getDriverTransportationDetails(String compid, String eppCode,
                                                                      String placing, String taskId,
                                                                      String vehicleId, String personalName,
                                                                      String beginTime, String endTime,
                                                                      Integer page, Integer pageSize);

    /**
     * 司机砼运输汇总
     *
     * @param compid       企业
     * @param eppCode      工程代码
     * @param placing      浇筑部位
     * @param taskId       任务单号
     * @param vehicleId    车号
     * @param personalName 司机
     * @param beginTime    开始时间
     * @param endTime      结束时间
     * @param page         分页
     * @param pageSize     每页显示条数
     */
    PageVO<DriverTransportationCountVO> getDriverTransportationCount(String compid, String eppCode,
                                                                     String placing, String taskId,
                                                                     String vehicleId, String personalName, String isNewVersion,
                                                                     String beginTime, String endTime,
                                                                     Integer page, Integer pageSize);

    /**
     * 司机砼运输砼产量合计
     *
     * @param compid       企业
     * @param eppCode      工程代码
     * @param placing      浇筑部位
     * @param taskId       任务单号
     * @param vehicleId    车号
     * @param personalName 司机
     * @param beginTime    开始时间
     * @param endTime      结束时间
     * @param page         分页
     * @param pageSize     每页显示条数
     */
    PageVO<DriverTransportationCountVO> getDriverTransportationSum(String compid, String eppCode,
                                                                   String placing, String taskId,
                                                                   String vehicleId, String personalName,
                                                                   String beginTime, String endTime,
                                                                   Integer page, Integer pageSize);

    /**
     * 司机砼运输车数合计
     *
     * @param compid       企业
     * @param eppCode      工程代码
     * @param placing      浇筑部位
     * @param taskId       任务单号
     * @param vehicleId    车号
     * @param personalName 司机
     * @param beginTime    开始时间
     * @param endTime      结束时间
     * @param page         分页
     * @param pageSize     每页显示条数
     */
    PageVO<DriverTransportationCountVO> getDriverTransportationCarNumList(String compid, String eppCode,
                                                                          String placing, String taskId,
                                                                          String vehicleId, String personalName,
                                                                          String beginTime, String endTime,
                                                                          Integer page, Integer pageSize);

    /**
     * 司机拖水拖泵汇总
     *
     * @param compid       企业
     * @param eppCode      工程代码
     * @param placing      浇筑部位
     * @param taskId       任务单号
     * @param vehicleId    车号
     * @param personalName 司机
     * @param beginTime    开始时间
     * @param endTime      结束时间
     * @param page         分页
     * @param pageSize     每页显示条数
     */
    PageVO<DriverTransportationCountVO> getDriverDragPumpCount(String compid, String eppCode,
                                                               String placing, String taskId,
                                                               String vehicleId, String personalName, String isNewVersion,
                                                               String beginTime, String endTime,
                                                               Integer page, Integer pageSize);

    /**
     * 泵车汇总统计
     *
     * @param compid       企业
     * @param eppCode      工程代码
     * @param personalName 司机
     * @param stirId       楼号
     * @param vehicleId    车号
     * @param beginTime    开始时间
     * @param endTime      结束时间
     * @param page         分页
     * @param pageSize     每页显示条数
     */
    PageVO<PumpTruckCountVO> getPumpTruckCount(String compid, String eppCode, String personalName,
                                               String stirId, String vehicleId, String taskId, String beginTime,
                                               String endTime, Integer page, Integer pageSize) throws ErpException;

    /**
     * 泵工汇总统计
     *
     * @param compid       企业
     * @param eppCode      工程代码
     * @param personalName 司机
     * @param stirId       楼号
     * @param vehicleId    车号
     * @param beginTime    开始时间
     * @param endTime      结束时间
     * @param page         分页
     * @param pageSize     每页显示条数
     */
    PageVO<PumpTruckCountVO> getPumpOperatorTruckCount(String compid, String eppCode,
                                                       String personalName,
                                                       String stirId, String vehicleId, String taskId, String isNewVersion,
                                                       String beginTime, String endTime,
                                                       Integer page, Integer pageSize) throws ErpException;

    /**
     * 泵车明细
     *
     * @param compid       企业
     * @param eppCode      工程代码
     * @param personalName 司机
     * @param stirId       楼号
     * @param vehicleId    车号
     * @param beginTime    开始时间
     * @param endTime      结束时间
     * @param page         分页
     * @param pageSize     每页显示条数
     */
    PageVO<PumpTruckDetailsVO> getPumpTruckDetails(String compid, String eppCode, String personalName,
                                                   String stirId, String vehicleId, String typeName, String taskId,
                                                   String beginTime, String endTime,
                                                   Integer page, Integer pageSize);

    /**
     * 工作方量统计
     *
     * @param compid       企业
     * @param eppCode      工程代码
     * @param personalName 司机
     * @param stirId       楼号
     * @param vehicleId    车号
     * @param beginTime    开始时间
     * @param endTime      结束时间
     * @param page         分页
     * @param pageSize     每页显示条数
     */
    PageVO<PumpTruckWorkloadStatisticsVO> getPumpTruckWorkloadStatistics(String compid,
                                                                         String eppCode,
                                                                         String personalName, String taskId,
                                                                         String stirId, String vehicleId,
                                                                         String beginTime, String endTime,
                                                                         Integer page, Integer pageSize);

    /**
     * 泵车合计
     *
     * @param compid       企业
     * @param eppCode      工程代码
     * @param personalName 司机
     * @param stirId       楼号
     * @param vehicleId    车号
     * @param beginTime    开始时间
     * @param endTime      结束时间
     * @param page         分页
     * @param pageSize     每页显示条数
     */
    PageVO<PumpTruckWorkloadStatisticsVO> getPumpTruckSum(String compid,
                                                          String eppCode,
                                                          String personalName,
                                                          String stirId, String vehicleId,
                                                          String beginTime, String endTime,
                                                          Integer page, Integer pageSize);

    /**
     * 搅拌车过磅查询汇总
     *
     * @param compid     企业
     * @param eppCode    工程代码
     * @param empName    过磅员
     * @param weightType 过磅类别
     * @param beginTime  开始时间
     * @param endTime    结束时间
     */
    Map<String, Double> getWorkloadStatisticsCount(String compid, String eppCode, String empName,
                                                   Integer weightType, String beginTime, String endTime);


    /**
     * 查询等待生产的搅拌车
     *
     * @param compid 企业
     */
    List<VehicleIdVO> getWaitCars(String compid);

    /**
     * 根据车号查询司机
     */
    List<String> getDriverByVehicleId(String compid, String vehicleId);
}
