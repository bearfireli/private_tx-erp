package com.hntxrj.txerp.server;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hntxrj.txerp.core.exception.ErpException;
import com.hntxrj.txerp.entity.TaskProduceFormula;
import com.hntxrj.txerp.vo.PageVO;
import com.hntxrj.txerp.vo.TheoryDetailVO;
import com.hntxrj.txerp.vo.TheoryFormulaVO;
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
    JSONArray spQueryVPTPlanFind(Integer mark, String compid, Integer currPage, Integer pageSize, Integer taskStatus,
                                 String eppName, String placing, String taskId, String preTime, String endTime,
                                 String builderName, Integer FormulaStatus, String opid);

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


    Map<String, Object> getFormulaByTaskId(String compid, String taskId);


    JSONObject getFormulaInfo(String compid, String taskId, String stirId);


    //获取配比列表
    PageVO<Map<String, Object>> getFormulaList(Integer taskStatus, String eppCode, String placing, String taskId,
                                               String startTime, String endTime, String compid, String builderCode,
                                               Integer formulaStatus, String opid,
                                               @RequestParam(defaultValue = "1") Integer page,
                                               @RequestParam(defaultValue = "10") Integer pageSize);


    //获取配备模板列表
    PageVO<TheoryFormulaVO> theoryFormulaList(String compid, String searchWord, Integer IdentifyNumber,
                                              String beginTime, String endTime, Integer page, Integer pageSize);

    //获取配比模板详情
    TheoryDetailVO getTheoryFormulaDetail(String compid, Integer stirId, String stgId, String formulaCheckCode,
                                          String theoryFormulaCode,
                                          Integer identifyNumber) throws ErpException;

    //获取实际配比详情
    TheoryDetailVO getStirIdTaskUnRealFormula(String compid, String taskId, Integer stirId);

    //获取理论配比详情
    TheoryDetailVO getStirIdTaskTheoryFormula(String compid, String taskId, Integer stirId);

    //获取配比引用方式 （即引用一套配比还是两套配比）
    Map<String, Integer> getTheoryFormulaMode(String compid);

    //保存理论配比
    void saveTaskUnrealFormula(TaskProduceFormula taskProduceFormula) throws ErpException;

    //保存实际配比
    void saveTaskTheoryFormula(TaskProduceFormula taskProduceFormula) throws ErpException;

    //审核配比
    void verifyFormula(String compid, String taskId, String formulaCode, Integer stirId, Integer verifyStatus);
}
