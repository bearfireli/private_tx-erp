package com.hntxrj.txerp.mapper;

import com.hntxrj.txerp.entity.BuilderInfo;
import com.hntxrj.txerp.vo.*;
import org.apache.ibatis.annotations.Mapper;

import java.math.BigDecimal;
import java.util.List;

/**
 * 施工单位 mapper
 */
@Mapper
public interface BuilderMapper {

    /**
     * 获取施工单位下拉
     *
     * @param builderName 施工单位名称
     * @param compid      企业id
     * @return 施工单位下拉对象
     */
    List<BuilderDropDownVO> getBuilderDropDown(String compid, String builderName);

    /**
     * 获取施工单位信息
     *
     * @param builderCode 施工单位代号
     * @param compid      企业id
     */
    BuilderInfo getBuilderInfo(String builderCode, String compid);

    /**
     * 工地端App产销统计
     *
     * @param contractDetailCodes 　子合同号集合
     * @param contractUIDList     　主合同号集合
     * @param eppCode             　工程代码
     * @param placing             　浇筑部位
     * @param taskId              　　任务单号
     * @param stgId               　　　砼标记
     * @param beginTime           　　开始时间
     * @param endTime             　　　结束时间
     * @return 产销统计列表
     */
    List<ConcreteVO> getBuilderConcreteCount(List<String> contractDetailCodes, List<String> contractUIDList, String eppCode, String placing, String taskId, String stgId, String beginTime, String endTime, Integer timeStatus);

    /**
     * 从生产消耗表中查询生产方量
     *
     * @param contractDetailCodes 　子合同号集合
     * @param contractUIDList     　主合同号集合
     * @param taskId              　　任务单号
     * @param produceBeginTime    　　开始生产时间
     * @param produceEndTime      　　　结束生产时间
     */
    BigDecimal getProductConcreteByTaskId(List<String> contractDetailCodes, List<String> contractUIDList, String taskId, String produceBeginTime, String produceEndTime);

    /**
     * 获取小票签收列表
     *
     * @param contractDetailCodes 企业
     * @param contractUIDList     企业
     * @param beginTime           开始时间
     * @param endTime             结束时间
     * @param eppCode             工程代号
     * @param upStatus            签收状态
     * @param builderCode         施工单位代号
     * @param taskId              任务单id
     * @param placing             浇筑部位
     * @return 小票签收列表
     */
    List<TaskSaleInvoiceListVO> getBuildTaskSaleInvoiceList(List<String> contractDetailCodes, List<String> contractUIDList, String beginTime, String endTime, String eppCode, Byte upStatus, String builderCode, String taskId, String placing, String taskStatus);

    /**
     * 调度派车列表
     *
     * @param contractDetailCodes 企业代号
     * @param contractUIDList     企业代号
     * @param searchName          搜索关键字
     * @return 调度派车列表
     */
    List<SendCarListVO> getBuildSendCarList(List<String> contractDetailCodes, List<String> contractUIDList, String searchName);

    /**
     * 查询产销统计中发货方量统计
     *
     * @param contractDetailCodes 　子合同号集合
     * @param contractUIDList     　主合同号集合
     * @param eppCode             　工程代码
     * @param placing             　浇筑部位
     * @param taskId              　　任务单号
     * @param stgId               　　　砼标记
     * @param beginTime           　　开始时间
     * @param endTime             　　　结束时间
     */
    BigDecimal getBuilderConcreteSum(List<String> contractDetailCodes, List<String> contractUIDList, String eppCode, String placing, String taskId, String stgId, String beginTime, String endTime, Integer timeStatus);


    /**
     * 查询任务单集合下的所有车辆
     *
     * @param contractDetailCodes 子合同号
     * @param contractUIDList     主合同号
     * @param taskIds             任务单号集合
     * @return 车辆集合
     */
    List<DispatchVehicle> getCarsByTaskIds(List<String> contractDetailCodes, List<String> contractUIDList, List<String> taskIds);
}
