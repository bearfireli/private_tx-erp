package com.hntxrj.txerp.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface FormulaMapper {

    /**
     * 生产配比列表
     *
     * @param compid        企业id
     * @param eppCode       工程名称代号
     * @param builderCode   施工单位代号
     * @param placing       浇筑部位
     * @param taskId        任务单号
     * @param taskStatus    任务单生产状态
     * @param formulaStatus 配比审核状态
     * @param startTime     开始时间
     * @param endTime       结束时间
     */
    List<Map<String, Object>> getFormulaList(String compid, String eppCode, String builderCode,
                                             String placing, String taskId, Integer taskStatus, Integer formulaStatus,
                                             String startTime, String endTime);


    /*根据任务单号获取配比信息*/
    Map<String, Object> getFormulaByTaskId(String compid, String taskId);

    /*获取配比审核状态（单个任务单）*/
    List<Map<String, Object>> getStirIdFormulaStatus(String compid, String taskId);

    /*获取配比审核状态（任务单集合）*/
    List<Map<String, Object>> getStirIdFormulaStatusByTaskIds(String compid, List<String> taskIds);

    /*从LM_TaskTheoryFormula表中获取配比详细信息*/
    Map<String, Object> getFormulaInfo(String compid, String taskId, String stirId);

    /*从LM_TaskUnrealFormula表中获取配比详细信息*/
    Map<String, Object> getProduceFormulaInfo(String compid, String taskId, String stirId);

    /*根据配比编号获取配比信息*/
    Map<String, String> getFormulaInfoByFormulaCode(String compid, String taskId, Integer stirId);
}
