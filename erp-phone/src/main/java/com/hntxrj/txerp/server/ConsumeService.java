package com.hntxrj.txerp.server;


import com.alibaba.fastjson.JSONArray;
import com.hntxrj.txerp.vo.*;
import com.hntxrj.txerp.vo.*;

public interface ConsumeService {

    /**
     * 生产消耗汇总
     *
     * @param mark            状态
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
    JSONArray spQueryVQueryProduceConsume(String compid, Integer currPage, Integer pageSize, String vehicleID,
                                          String stirId, String taskId, String stgId, String empname,
                                          String placing, String eppName, String builderShorName,
                                          String openTime, String overTime, Integer mark, Integer taskstatus, String opid);


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
     * 原材料统计汇总
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

    PageVO<StockVO> getProductDatail(String compid, Integer stirid);

    Integer getErroPan(String compid,String beginTime,String endTime);
}
