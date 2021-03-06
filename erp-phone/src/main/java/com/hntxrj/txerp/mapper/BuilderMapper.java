package com.hntxrj.txerp.mapper;

import com.hntxrj.txerp.entity.BuilderInfo;
import com.hntxrj.txerp.vo.*;
import org.apache.ibatis.annotations.Mapper;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

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
     * @param stgId               　　　砼标号
     * @param beginTime           　　开始时间
     * @param endTime             　　　结束时间
     * @return 产销统计列表
     */
    List<ConcreteVO> getBuilderConcreteCount(List<String> contractDetailCodes, List<String> contractUIDList,
                                             String eppCode, String placing, String taskId, String stgId,
                                             String beginTime, String endTime, Integer type);

    /**
     * 从生产消耗表中查询生产方量（此方法没有被调用，工地App不需要查询生产方量）
     *
     * @param contractDetailCodes 　子合同号集合
     * @param contractUIDList     　主合同号集合
     * @param taskId              　　任务单号
     * @param produceBeginTime    　　开始生产时间
     * @param produceEndTime      　　　结束生产时间
     */
    BigDecimal getProductConcreteByTaskId(List<String> contractDetailCodes, List<String> contractUIDList,
                                          String taskId, String produceBeginTime, String produceEndTime);

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
    List<TaskSaleInvoiceListVO> getBuildTaskSaleInvoiceList(List<String> contractDetailCodes,
                                                            List<String> contractUIDList, String beginTime,
                                                            String endTime, String eppCode, Byte upStatus,
                                                            String builderCode, String taskId, String placing,
                                                            String taskStatus);

    /**
     * 工地端调度派车列表
     *
     * @param contractDetailCodes 企业代号
     * @param contractUIDList     企业代号
     * @param searchWords         搜索关键字
     * @return 调度派车列表
     */
    List<SendCarListVO> getBuildSendCarList(List<String> contractDetailCodes, List<String> contractUIDList,
                                            String searchWords);

    /**
     * 查询产销统计中发货方量统计
     *
     * @param contractDetailCodes 　子合同号集合
     * @param contractUIDList     　主合同号集合
     * @param eppCode             　工程代码
     * @param placing             　浇筑部位
     * @param taskId              　　任务单号
     * @param stgId               　　　砼标号
     * @param beginTime           　　开始时间
     * @param endTime             　　　结束时间
     */
    BigDecimal getBuilderConcreteSum(List<String> contractDetailCodes, List<String> contractUIDList, String eppCode,
                                     String placing, String taskId, String stgId, String beginTime, String endTime,
                                     Integer type);


    /**
     * 查询任务单集合下的所有车辆
     *
     * @param contractDetailCodes 子合同号
     * @param contractUIDList     主合同号
     * @param taskIds             任务单号集合
     * @return 车辆集合
     */
    List<DispatchVehicle> getCarsByTaskIds(List<String> contractDetailCodes, List<String> contractUIDList,
                                           List<String> taskIds);

    // 根据施工单位名称和施工单位简称获取施工单位信息。
    Map<String, String> getBuilderInfoByName(String compid, String builderName, String builderShortName);

    /**
     * 施工方绑定合同下任务单的加价项目集合
     *
     * @param compid              企业id
     * @param taskId              任务单号
     * @param contractDetailCodes 子合同号
     * @param contractUIDList     主合同号
     * @return 车辆集合
     */
    List<String> getBuildPPCodeByTaskId(String compid, String taskId, List<String> contractUIDList,
                                        List<String> contractDetailCodes);

    /**
     * 施工方绑定合同下的任务单详情
     *
     * @param compid              企业id
     * @param taskId              任务单号
     * @param contractDetailCodes 子合同号
     * @param contractUIDList     主合同号
     * @return 车辆集合
     */
    TaskPlanVO getBuildTaskPlanByTaskId(String compid, String taskId,
                                        List<String> contractUIDList, List<String> contractDetailCodes);


    /**
     * 施工方绑定合同下的小票详情
     *
     * @param compid              企业id
     * @param id                  小票id
     * @param contractDetailCodes 子合同号
     * @param contractUIDList     主合同号
     * @return 车辆集合
     */
    TaskSaleInvoiceDetailVO getTaskSaleInvoiceDetailVO(Integer id, String compid,
                                                       List<String> contractUIDList, List<String> contractDetailCodes);

    //获取最大的施工单位代号
    String getMaxBuilderCode(String compid);

    //添加施工单位
    void addBuilderInfo(String compid, String builderCode, String builderName, String builderShortName,
                        String address, String corporation, String fax, String linkTel, String nowDate);
}
