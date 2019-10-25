package com.hntxrj.dao;

import com.alibaba.fastjson.JSONArray;

public interface ConsumeDao {

    /**
     * 生产消耗汇总
     * @param mark  状态
     * @param compid 企业
     * @param currPage 当企业
     * @param pageSize 页长度
     * @param vehicleID 车号
     * @param stirId 线号
     * @param taskId 任务单好
     * @param stgId 砼标号
     * @param empname  操作员
     * @param placing 浇筑部位
     * @param eppName  工程名称
     * @param builderShorName 施工单位简称
     * @param openTime 查询时间
     * @param overTime 救赎时间
     * @return json
     */
    JSONArray spQueryVQueryProduceConsume(String compid, Integer currPage, Integer pageSize, String vehicleID, String stirId, String taskId, String stgId, String empname, String placing, String eppName, String builderShorName, String openTime, String overTime, Integer mark, Integer taskstatus, String opid);

    /**
     * 生产消耗汇总 list  一次执行3个
     * @param compid 企业
     * @param currPage 当企业
     * @param pageSize 页长度
     * @param vehicleID 车号
     * @param stirId 线号
     * @param taskId 任务单好
     * @param stgId 砼标号
     * @param empname  操作员
     * @param placing 浇筑部位
     * @param eppName  工程名称
     * @param builderShorName 施工单位简称
     * @param openTime 查询时间
     * @param overTime 救赎时间
     * @return json
     */
    JSONArray spspQueryVAccountPTProduceConsumeList3(String compid, Integer currPage, Integer pageSize, String vehicleID, Integer stirId, String taskId, String stgId, String empname, String placing, String eppName, String builderShorName, String openTime, String overTime);


    /**
     *司机排班
     * @param currPage 当前页
     * @param pageSize 页长度
     * @param personalCode 司机名称
     * @param vehicleID 车号
     * @return jsonarray
     */
    JSONArray spQueryVMWorkClassLog(String compid, Integer currPage, Integer pageSize, String personalCode, String vehicleID,String beginTime, String endTime,String opid,Integer workclass);

    /**
     * 查询企业名称
     * @param currPage 当前页
     * @param pageSize 页长度
     * @param var1
     * @param var2
     * @return
     */
    JSONArray spQueryUsercomp(Integer currPage, Integer pageSize, String var1, String var2);
}
