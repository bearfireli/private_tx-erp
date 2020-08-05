package com.hntxrj.txerp.server;


import com.alibaba.fastjson.JSONArray;
import com.hntxrj.txerp.vo.*;

import java.util.List;
import java.util.Map;

public interface ConsumeService {

    /**
     * 生产消耗汇总
     *
     * @param mark             状态
     * @param compid           企业
     * @param currPage         当企业
     * @param pageSize         页长度
     * @param vehicleID        车号
     * @param stirId           线号
     * @param taskId           任务单好
     * @param stgId            砼标号
     * @param empName          操作员
     * @param placing          浇筑部位
     * @param eppName          工程名称
     * @param builderShortName 施工单位简称
     * @param openTime         查询时间
     * @param overTime         救赎时间
     * @return json
     */
    JSONArray spQueryVQueryProduceConsume(String compid, Integer currPage, Integer pageSize, String vehicleID,
                                          String stirId, String taskId, String stgId, String empName,
                                          String placing, String eppName, String builderShortName,
                                          String openTime, String overTime, Integer mark,
                                          Integer taskstatus, String opid);


    /**
     * 生产消耗汇总 list  一次执行3个
     *
     * @param compid          企业
     * @param currPage        当企业
     * @param pageSize        页长度
     * @param vehicleID       车号
     * @param stirId          线号
     * @param taskId          任务单好
     * @param stgId           砼标号
     * @param empname         操作员
     * @param placing         浇筑部位
     * @param eppName         工程名称
     * @param builderShorName 施工单位简称
     * @param openTime        查询时间
     * @param overTime        救赎时间
     * @return json
     */
    JSONArray spspQueryVAccountPTProduceConsumeList3(String compid, Integer currPage, Integer pageSize,
                                                     String vehicleID, Integer stirId, String taskId, String stgId,
                                                     String empname, String placing, String eppName,
                                                     String builderShorName, String openTime, String overTime);

    /**
     * 司机排班
     *
     * @param currPage     当前页
     * @param pageSize     页长度
     * @param personalCode 司机名称
     * @param vehicleID    车号
     * @return jsonarray
     */
    JSONArray spQueryVMWorkClassLog(String compid, Integer currPage, Integer pageSize, String personalCode,
                                    String vehicleID, String beginTime, String endTime, String opid, Integer workclass);

    /**
     * 查询企业名称
     *
     * @param currPage 当前页
     * @param pageSize 页长度
     */
    JSONArray spQueryUsercomp(Integer currPage, Integer pageSize, String var1, String var2);


    /**
     * 任务单消耗列表
     *
     * @param compid    企业id
     * @param beginTime 开始时间
     * @param endTime   结束时间
     * @param vehicleId 车号
     * @param stgId     标号
     * @param taskId    任务单号
     * @param stirId    站别代号
     * @param page      页数
     * @param pageSize  每页数量
     * @return 任务单消耗列表
     */
    PageVO<TaskConsumeVO> getTaskConsumeList(String compid, String beginTime, String endTime,
                                             String vehicleId, String stgId, String taskId, String stirId,
                                             Integer page, Integer pageSize);

    /**
     * 每盘配料明细
     *
     * @param compid    企业id
     * @param beginTime 开始时间
     * @param endTime   结束时间
     * @param vehicleId 车号
     * @param stgId     标号
     * @param taskId    任务单号
     * @param stirId    站别代号
     * @param page      页数
     * @param pageSize  每页数量
     * @return 每盘配料明细
     */
    PageVO<PlatelngredientsVO> getFormulaDetails(String compid, String beginTime, String endTime,
                                                 String vehicleId, String stgId, String taskId, String stirId,
                                                 Integer page, Integer pageSize);

    /**
     * 超差盘数列表
     *
     * @param compid    企业id
     * @param beginTime 开始时间
     * @param endTime   结束时间
     * @param vehicleId 车号
     * @param stgId     标号
     * @param taskId    任务单号
     * @param stirId    站别代号
     * @param page      页数
     * @param pageSize  每页数量
     * @return 超差盘数列表
     */
    List<PlatelngredientsVO> getErrorProductList(String compid, String beginTime, String endTime,
                                                 String vehicleId, String stgId, String taskId, String stirId,
                                                 Integer page, Integer pageSize);


    /**
     * 标号消耗汇总
     *
     * @param compid    企业id
     * @param beginTime 开始时间
     * @param endTime   结束时间
     * @param vehicleId 车号
     * @param taskId    任务单号
     * @param stirId    站别代号
     * @param page      页数
     * @param pageSize  每页数量
     * @return 标号消耗汇总
     */
    PageVO<ConsumeptionTotalVO> getConsumptionTotal(String compid, String beginTime, String endTime,
                                                    String vehicleId, String stgId, String taskId, String stirId,
                                                    Integer page, Integer pageSize);


    /**
     * 标号消耗汇总柱状图
     *
     * @param compid    企业id
     * @param beginTime 开始时间
     * @param endTime   结束时间
     * @param vehicleId 车号
     * @param taskId    任务单号
     * @param stirId    站别代号
     * @return 标号消耗汇总
     */
    List<ConsumptionHistogram> getConsumptionHistogram(String compid, String beginTime, String endTime,
                                                       String vehicleId, String stgId, String taskId, String stirId);

    /**
     * 原材料统计汇总(老版本)
     *
     * @param compid    企业id
     * @param beginTime 开始时间
     * @param endTime   结束时间
     * @param vehicleId 车号
     * @param taskId    任务单号
     * @param stirId    站别代号
     * @param stgId     线号
     * @param page      页数
     * @param pageSize  每页数量
     * @return 原材料统计汇总
     */
    PageVO<RawCollectVO> getMatTotal(String compid, String beginTime, String endTime,
                                     String vehicleId, String taskId, String stirId,
                                     String stgId,
                                     Integer page, Integer pageSize);

    /**
     * 原材料统计汇总 (新版本)
     *
     * @param compid    企业id
     * @param beginTime 开始时间
     * @param endTime   结束时间
     * @param vehicleId 车号
     * @param taskId    任务单号
     * @param stirId    站别代号
     * @param stgId     线号
     * @param page      页数
     * @param pageSize  每页数量
     * @return 原材料统计汇总
     */
    PageVO<RawCollectVO> getProductionConsumptionDetails(String compid, String beginTime, String endTime,
                                                         String vehicleId, String taskId, String stirId,
                                                         String stgId,
                                                         Integer page, Integer pageSize);

    /**
     * 生产消耗汇总合计方量
     *
     * @param compid    企业id
     * @param beginTime 开始时间
     * @param endTime   结束时间
     * @param vehicleId 车号
     * @param taskId    任务单号
     * @param stirId    站别代号
     * @param page      页数
     * @param pageSize  每页数量
     * @return 原材料统计汇总
     */
    PageVO<ConsumePtionCloseVO> getConsumeClose(String compid, String beginTime, String endTime,
                                                String vehicleId, String stgId, String taskId, String stirId,
                                                Integer page, Integer pageSize);

    /**
     * 查询生产材料详细名称
     *
     * @param compid 企业id
     * @param stirId 线号
     * @return 查询材料名
     */
    PageVO<StockVO> getProductDatail(String compid, Integer stirId);

    /**
     * 获取指定时间的超差盘数
     *
     * @param compid    　企业
     * @param beginTime 　开始时间
     * @param endTime   　结束时间
     */
    Integer getErrorPan(String compid, String beginTime, String endTime);

    /**
     * 每车消耗列表
     *
     * @param compid    　企业
     * @param beginTime 　开始时间
     * @param endTime   　结束时间
     * @param stirId    　线号
     * @param stgId     　标号
     * @param taskId    　任务单号
     * @param vehicleId 　车号
     * @param page      　页码
     * @param pageSize  　每页大小
     */
    PageVO<VehicleConsumeVO> getVehicleConsumeList(String compid, String beginTime, String endTime, String stirId,
                                                   String stgId, String taskId, String vehicleId, Integer page,
                                                   Integer pageSize);

    /**
     * 车辆消耗列表汇总
     *
     * @param compid    　企业
     * @param beginTime 　开始时间
     * @param endTime   　结束时间
     * @param stirId    　线号
     * @param stgId     　标号
     * @param taskId    　任务单号
     * @param vehicleId 　车号
     */
    Map<String, Integer> getVehicleConsumeSum(String compid, String beginTime, String endTime, String stirId,
                                                String stgId, String taskId, String vehicleId);

    /**
     * 每车消耗列表
     *
     * @param compid    　企业
     * @param stirId    　线号
     * @param vehicleId 　车号
     */
    RawCollectVO getVehicleConsumeDetail(String compid, String vehicleId, Integer stirId);


}
