package com.hntxrj.txerp.mapper;

import com.hntxrj.txerp.vo.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 车辆工作量
 */
@Mapper
public interface VehicleWorkloadMapper {

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
     */
    List<VehicleWorkloadDetailVO> getVehicleWorkloadDetail(String compid, String personalName, String eppCode,
                                                           String placing, String taskId, String vehicleId,
                                                           String beginTime, String endTime);

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
     */
    List<VehicleWorkloadSummaryVO> getVehicleWorkloadSummary(String compid, String personalName, String eppCode,
                                                             String placing, String taskId, String vehicleId,
                                                             String beginTime, String endTime);

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
     */
    List<VehicleWorkloadSummaryVO> getVehicleWorkloadSummaryCount(String compid, String personalName, String eppCode,
                                                                  String placing, String taskId,
                                                                  String vehicleId,
                                                                  String beginTime, String endTime);

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
     */
    List<VehicleWorkTowingPumpDetailVO> getVehicleWorkTowingPumpDetail(String compid, String personalName,
                                                                       String eppCode, String placing, String taskId,
                                                                       String vehicleId, String beginTime,
                                                                       String endTime);

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
     */
    List<VehicleWorkTowingPumpCountVO> getVehicleWorkTowingPumpCount(String compid, String personalName, String eppCode,
                                                                     String placing, String taskId,
                                                                     String vehicleId,
                                                                     String beginTime, String endTime);

    /**
     * 搅拌车过磅查询
     *
     * @param compid     企业
     * @param eppCode    工程代码
     * @param empName    过磅员
     * @param weightType 过磅类别
     * @param beginTime  开始时间
     * @param endTime    结束时间
     */
    List<WorkloadStatisticsVo> getWorkloadStatistics(String compid, String eppCode, String empName,
                                                     Integer weightType,
                                                     String beginTime, String endTime);

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
     */
    List<DriverworkloadStatisticsVO> getDriverTransportationDetails(String compid, String eppCode,
                                                                    String placing, String taskId,
                                                                    String vehicleId, String personalName,
                                                                    String beginTime, String endTime);

    /**
     * 司机砼运输汇总（老版本）
     *
     * @param compid       企业
     * @param eppCode      工程代码
     * @param placing      浇筑部位
     * @param taskId       任务单号
     * @param vehicleId    车号
     * @param personalName 司机
     * @param beginTime    开始时间
     * @param endTime      结束时间
     */
    List<DriverTransportationCountVO> getDriverTransportationCount(String compid, String eppCode,
                                                                   String placing, String taskId,
                                                                   String vehicleId, String personalName,
                                                                   String beginTime, String endTime);


    /**
     * 司机砼运输汇总(新版本)
     *
     * @param compid       企业
     * @param eppCode      工程代码
     * @param placing      浇筑部位
     * @param taskId       任务单号
     * @param vehicleId    车号
     * @param personalName 司机
     * @param beginTime    开始时间
     * @param endTime      结束时间
     */
    List<DriverTransportationCountVO> getDriverTransportationCountNew(String compid, String eppCode,
                                                                      String placing, String taskId,
                                                                      String vehicleId, String personalName,
                                                                      String beginTime, String endTime);

    /**
     * 司机拖水拖泵汇总（老版本）
     *
     * @param compid       企业
     * @param eppCode      工程代码
     * @param placing      浇筑部位
     * @param taskId       任务单号
     * @param vehicleId    车号
     * @param personalName 司机
     * @param beginTime    开始时间
     * @param endTime      结束时间
     */
    List<DriverTransportationCountVO> getDriverDragPumpCount(String compid, String eppCode, String placing,
                                                             String taskId, String vehicleId,
                                                             String personalName,
                                                             String beginTime, String endTime);

    /**
     * 司机拖水拖泵汇总(新版本)
     *
     * @param compid       企业
     * @param eppCode      工程代码
     * @param placing      浇筑部位
     * @param taskId       任务单号
     * @param vehicleId    车号
     * @param personalName 司机
     * @param beginTime    开始时间
     * @param endTime      结束时间
     */
    List<DriverTransportationCountVO> getDriverDragPumpCountNew(String compid, String eppCode, String placing,
                                                                String taskId, String vehicleId,
                                                                String personalName,
                                                                String beginTime, String endTime);

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
     */
    List<PumpTruckCountVO> getPumpTruckCount(String compid, String eppCode, String personalName,
                                             String stirId, String vehicleId, String taskId,
                                             String beginTime, String endTime);

    /**
     * 泵工汇总统计（老版本）
     *
     * @param compid       企业
     * @param eppCode      工程代码
     * @param personalName 司机
     * @param stirId       楼号
     * @param vehicleId    车号
     * @param beginTime    开始时间
     * @param endTime      结束时间
     */
    List<PumpTruckCountVO> getPumpOperatorTruckCount(String compid, String eppCode,
                                                     String personalName, String stirId,
                                                     String vehicleId, String taskId,
                                                     String beginTime, String endTime);


    /**
     * 泵工汇总统计(新版本)
     *
     * @param compid       企业
     * @param eppCode      工程代码
     * @param personalName 司机
     * @param stirId       楼号
     * @param vehicleId    车号
     * @param beginTime    开始时间
     * @param endTime      结束时间
     */
    List<PumpTruckCountVO> getPumpOperatorTruckCountNew(String compid, String eppCode,
                                                        String personalName, String stirId,
                                                        String vehicleId, String taskId,
                                                        String beginTime, String endTime);

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
     */
    List<PumpTruckDetailsVO> getPumpTruckDetails(String compid, String eppCode, String personalName,
                                                 String stirId, String vehicleId, String typeName, String taskId,
                                                 String beginTime, String endTime);

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
     */
    List<PumpTruckWorkloadStatisticsVO> getPumpTruckWorkloadStatistics(String compid, String eppCode,
                                                                       String personalName, String taskId, String stirId,
                                                                       String vehicleId,
                                                                       String beginTime, String endTime);

    /**
     * 司机砼运输合计
     *
     * @param compid       企业
     * @param eppCode      工程代码
     * @param placing      浇筑部位
     * @param taskId       任务单号
     * @param vehicleId    车号
     * @param personalName 司机
     * @param beginTime    开始时间
     * @param endTime      结束时间
     */
    List<DriverTransportationCountVO> getDriverTransportationSum(String compid, String eppCode,
                                                                 String placing, String taskId,
                                                                 String vehicleId, String personalName,
                                                                 String beginTime, String endTime);

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
     */
    List<PumpTruckWorkloadStatisticsVO> getPumpTruckSum(String compid, String eppCode,
                                                        String personalName, String stirId,
                                                        String vehicleId,
                                                        String beginTime, String endTime);

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
     */
    List<DriverTransportationCountVO> getDriverTransportationCarNumList(String compid, String eppCode, String placing,
                                                                        String taskId, String vehicleId,
                                                                        String personalName, String beginTime,
                                                                        String endTime);


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
    Double getWorkloadStatisticsCount(String compid, String eppCode, String empName, Integer weightType,
                                      String beginTime, String endTime);


    /**
     * 获取等待派车的车辆列表
     */
    List<VehicleIdVO> getWaitCars(String compid);

    /**
     * 根据车号查询司机代号
     */
    List<String> getDriverByVehicleId(String compid, String vehicleId);
}
