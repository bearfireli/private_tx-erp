package com.hntxrj.txerp.server.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hntxrj.txerp.dao.LaboratroyDao;
import com.hntxrj.txerp.mapper.FormulaMapper;
import com.hntxrj.txerp.mapper.StockMapper;
import com.hntxrj.txerp.mapper.TaskPlanMapper;
import com.hntxrj.txerp.server.FormulaService;
import com.hntxrj.txerp.util.PageInfoUtil;
import com.hntxrj.txerp.vo.PageVO;
import com.hntxrj.txerp.vo.PriceMarkupVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Service
@Scope("prototype")
@Slf4j
public class FormulaServiceImpl implements FormulaService {

    private final LaboratroyDao laboratroyDao;
    private final FormulaMapper formulaMapper;
    private final StockMapper stockMapper;
    private final TaskPlanMapper taskPlanMapper;

    @Autowired
    public FormulaServiceImpl(LaboratroyDao laboratroyDao, FormulaMapper formulaMapper, StockMapper stockMapper, TaskPlanMapper taskPlanMapper) {
        this.laboratroyDao = laboratroyDao;
        this.formulaMapper = formulaMapper;
        this.stockMapper = stockMapper;
        this.taskPlanMapper = taskPlanMapper;
    }

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
    @Override
    public JSONArray spQueryVPTPlanFind(Integer mark, String compid, Integer currPage, Integer pageSize,
                                        Integer taskStatus, String eppName, String placing, String taskId,
                                        String preTime, String endTime, String builderName,
                                        Integer FormulaStatus, String opid) {

        return laboratroyDao.spQueryVPTPlanFind(mark, compid, currPage, pageSize, taskStatus, eppName,
                placing, taskId, preTime, endTime, builderName, FormulaStatus, opid);

    }


    /**
     * 审核配比
     *
     * @param taskId       任务单号
     * @param compid       企业
     * @param opid         用户
     * @param verifystatus 线号审核状态
     * @param stirid       线号
     * @param formulaCode  配比编号
     * @return jsonArray
     */
    @Override
    public JSONArray sp_Verify_LM_TaskTheoryFormula(String compid, String taskId, String opid,
                                                    Integer verifystatus, Integer stirid, String formulaCode) {
        return laboratroyDao.sp_Verify_LM_TaskTheoryFormula(compid, taskId, opid, verifystatus, stirid, formulaCode);
    }

    @Override
    public Map<String, Object> getFormulaByTaskId(String compid, String taskId) {
        Map<String, Object> formulaMap = formulaMapper.getFormulaByTaskId(compid, taskId);
        formulaMap.put("stirIdFormulaStatus", formulaMapper.getStirIdFormulaStatus(compid, taskId));
        return formulaMap;
    }

    @Override
    public JSONObject getFormulaInfo(String compid, String taskId, String stirId) {
        Map<String, Object> resultMap = formulaMapper.getFormulaInfo(compid, taskId, stirId);
        List<Map<String, Object>> formulas = new ArrayList<>();

        List<Map<String, Object>> stocks = stockMapper.getStockAll(compid);
        // 处理 竖边横
        int i = 0;
        while (i < 27) {
            Map<String, Object> formula = new HashMap<>();
            String mat = String.valueOf(resultMap.get("Mat" + (i + 1)));
            formula.put("mat", mat);
            formula.put("matValue", resultMap.get("MatV" + (i + 1)));
            formula.put("wr", resultMap.get("WR" + (i + 1)));
            resultMap.remove("Mat" + (i + 1));
            resultMap.remove("MatV" + (i + 1));
            resultMap.remove("WR" + (i + 1));
            // 处理类型
            // 取出库存id

            String pattern = "\\((.*?)\\)";
            Pattern p = Pattern.compile(pattern);
            Matcher m = p.matcher(mat); // 获取 matcher 对象
            String stockId = null;
            while (m.find()) {
                stockId = m.group(1);
            }
            for (Map<String, Object> stkItem : stocks) {
                if (stockId != null && stockId.equals(stkItem.get("MatCode"))) {
                    formula.put("matParent", stkItem.get("MatParent"));
                }
            }

            formulas.add(formula);
            i++;
        }


        resultMap.put("formulas", formulas);

        return JSONObject.parseObject(JSON.toJSONString(resultMap));
    }


    /**
     * 开具配比
     *
     * @param taskStatus    任务单生产状态  0:等待生产   1:正在生产  2:暂停  3:完成  4:删除
     * @param eppCode       工程名称代号
     * @param placing       浇筑部位
     * @param builderCode   施工单位代号
     * @param formulaStatus 配比审核状态
     * @param opid          当前操作员
     * @return jsoN
     */
    @Override
    public PageVO<Map<String, Object>> getFormulaList(Integer taskStatus, String eppCode, String placing,
                                                      String taskId, String startTime, String endTime, String compid,
                                                      String builderCode, Integer formulaStatus,
                                                      String opid, Integer page, Integer pageSize) {
        log.info("【查询配比列表】startTime={}, endTime={}", startTime, endTime);
        PageHelper.startPage(page, pageSize, "formulaStatus, TaskId desc");
        List<Map<String, Object>> formulaList = formulaMapper.getFormulaList(
                compid, eppCode, builderCode, placing, taskId, taskStatus, formulaStatus, startTime, endTime);

        if (formulaList != null) {
            for (Map<String, Object> map : formulaList) {
                StringBuilder ppName = new StringBuilder();
                //根据compid和taskId查询出每个任务单对应的加价项目编号集合。
                List<String> ppCodes = taskPlanMapper.getPPCodeByTaskId(compid, String.valueOf(map.get("TaskId")));
                for (String ppCode : ppCodes) {
                    PriceMarkupVO priceMarkupVO = taskPlanMapper.getPriceMarkupByPPCode(compid, ppCode);
                    ppName.append(priceMarkupVO.getPPName());
                    ppName.append(",");
                }
                if (ppName.length() > 0) {
                    ppName.deleteCharAt(ppName.length() - 1);
                }
                //把加价项目名称赋值给配比列表map集合传递到前台。
                map.put("ppName", ppName);
            }
        }


        PageInfo<Map<String, Object>> pageInfo = new PageInfo<>(formulaList);
        PageInfoUtil<Map<String, Object>> pageInfoListVO = new PageInfoUtil<>();
        PageVO<Map<String, Object>> resultObj = pageInfoListVO.init(pageInfo);
        List<Map<String, Object>> resultList = resultObj.getArr();
        // 获取每个任务单的开配线号详情
        List<String> taskIds = new ArrayList<>();
        for (Map<String, Object> stringObjectMap : resultList) {
            taskIds.add(String.valueOf(stringObjectMap.get("TaskId")));
        }
        if (!resultList.isEmpty()) {
            List<Map<String, Object>> formulaStatuses = formulaMapper.getStirIdFormulaStatusByTaskIds(compid, taskIds);
            for (Map<String, Object> stringObjectMap : resultList) {
                List<Map<String, Object>> formulaStatusMap = new ArrayList<>();
                for (Map<String, Object> status : formulaStatuses) {
                    if (status.get("TaskId").equals(stringObjectMap.get("TaskId"))) {
                        formulaStatusMap.add(status);
                    }
                }
                stringObjectMap.put("formulaStatus", formulaStatusMap);
            }
            resultObj.setArr(resultList);
        }

        return resultObj;
    }

}
