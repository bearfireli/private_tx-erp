package com.hntxrj.server;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hntxrj.vo.PageVO;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * 配比服务
 */
public interface FormulaService {
    /**
     * 开具配比
     *
     * @param mark       0位列表
     * @param currPage   当前页
     * @param pageSize   页长度
     * @param taskStatus 状态
     * @param eppName    施工单位
     * @param placing    浇筑部位
     * @param taskId     任务单号
     * @param preTime    开具时间
     * @param endTime    结束时间
     * @return jsoN
     */
    JSONArray spQueryVPTPlanFind(Integer mark, String compid, Integer currPage, Integer pageSize, Integer taskStatus, String eppName, String placing, String taskId, String preTime, String endTime, String builderName, Integer FormulaStatus, String opid);

    /**
     * 开具审核
     *
     * @param taskId       任务单号
     * @param compid       企业
     * @param opid         用户
     * @param verifystatus 线号审核状态
     * @param stirid       线号
     * @param formulaCode  配比编号
     * @return jsonArray
     */
    JSONArray sp_Verify_LM_TaskTheoryFormula(String compid, String taskId, String opid,
                                             Integer verifystatus, Integer stirid, String formulaCode);


    JSONObject getFormulaByTaskId(String compid, String taskId);


    JSONObject getFormulaInfo(String compid, String taskId, String stirId);


    PageVO<Map<String, Object>> getFormulaList(Integer taskStatus, String eppCode, String placing, String taskId,
                                               String startTime, String endTime, String compid, String builderCode,
                                               Integer formulaStatus, String opid,
                                               @RequestParam(defaultValue = "1") Integer page,
                                               @RequestParam(defaultValue = "10") Integer pageSize);

}
