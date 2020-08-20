package com.hntxrj.txerp.server.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hntxrj.SyncPlugin;
import com.hntxrj.txerp.core.exception.ErpException;
import com.hntxrj.txerp.core.exception.ErrEumn;
import com.hntxrj.txerp.core.util.SimpleDateFormatUtil;
import com.hntxrj.txerp.dao.LaboratroyDao;
import com.hntxrj.txerp.entity.*;
import com.hntxrj.txerp.mapper.FormulaMapper;
import com.hntxrj.txerp.mapper.StockMapper;
import com.hntxrj.txerp.mapper.SystemVarInitMapper;
import com.hntxrj.txerp.mapper.TaskPlanMapper;
import com.hntxrj.txerp.repository.*;
import com.hntxrj.txerp.server.FormulaService;
import com.hntxrj.txerp.util.PageInfoUtil;
import com.hntxrj.txerp.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;
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
    private final SystemVarInitMapper systemVarInitMapper;
    private final TaskUnrealFormulaRepository taskUnrealFormulaRepository;
    private final TaskTheoryFormulaRepository taskTheoryFormulaRepository;
    private final TaskProduceFormulaRepository taskProduceFormulaRepository;
    private final TaskProduceFormulaLogRepository taskProduceFormulaLogRepository;
    private final TaskUnrealFormulaLogRepository taskUnrealFormulaLogRepository;
    private final TaskTheoryFormulaLogRepository taskTheoryFormulaLogRepository;
    private final SyncPlugin syncPlugin;
    private final SimpleDateFormat sdf = SimpleDateFormatUtil.getDefaultSimpleDataFormat();

    @Autowired
    public FormulaServiceImpl(LaboratroyDao laboratroyDao, FormulaMapper formulaMapper, StockMapper stockMapper,
                              TaskPlanMapper taskPlanMapper, SystemVarInitMapper systemVarInitMapper,
                              TaskUnrealFormulaRepository taskUnrealFormulaRepository,
                              TaskTheoryFormulaRepository taskTheoryFormulaRepository,
                              TaskProduceFormulaRepository taskProduceFormulaRepository,
                              TaskProduceFormulaLogRepository taskProduceFormulaLogRepository,
                              TaskUnrealFormulaLogRepository taskUnrealFormulaLogRepository,
                              TaskTheoryFormulaLogRepository taskTheoryFormulaLogRepository, SyncPlugin syncPlugin) {
        this.laboratroyDao = laboratroyDao;
        this.formulaMapper = formulaMapper;
        this.stockMapper = stockMapper;
        this.taskPlanMapper = taskPlanMapper;
        this.systemVarInitMapper = systemVarInitMapper;
        this.taskUnrealFormulaRepository = taskUnrealFormulaRepository;
        this.taskTheoryFormulaRepository = taskTheoryFormulaRepository;
        this.taskProduceFormulaRepository = taskProduceFormulaRepository;
        this.taskProduceFormulaLogRepository = taskProduceFormulaLogRepository;
        this.taskUnrealFormulaLogRepository = taskUnrealFormulaLogRepository;
        this.taskTheoryFormulaLogRepository = taskTheoryFormulaLogRepository;
        this.syncPlugin = syncPlugin;
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
        Map<String, Object> produceMap = formulaMapper.getProduceFormulaInfo(compid, taskId, stirId);
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
            formula.put("produceValue", "0");
            if (produceMap != null) {
                formula.put("produceValue", produceMap.get("MatV" + (i + 1)));
            }

            resultMap.remove("Mat" + (i + 1));
            resultMap.remove("MatV" + (i + 1));
            resultMap.remove("WR" + (i + 1));

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
        PageHelper.startPage(page, pageSize);
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


        assert formulaList != null;
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


    @Override
    public PageVO<TheoryFormulaVO> theoryFormulaList(String compid, String searchWord, Integer IdentifyNumber,
                                                     String beginTime, String endTime, Integer page, Integer pageSize) {

        //获取配比引用方式 0:引用一套配比;   1:引用两套配比
        Integer theoryFormulaMode = systemVarInitMapper.getTheoryFormulaMode(compid);
        if (theoryFormulaMode == null) {
            theoryFormulaMode = 0;
        }
        PageHelper.startPage(page, pageSize);
        List<TheoryFormulaVO> theoryFormulaVOS = formulaMapper.theoryFormulaList(compid, searchWord, beginTime, endTime,
                IdentifyNumber, theoryFormulaMode);

        PageInfo<TheoryFormulaVO> pageInfo = new PageInfo<>(theoryFormulaVOS);
        PageVO<TheoryFormulaVO> pageVO = new PageVO<>();
        pageVO.format(pageInfo);
        return pageVO;
    }

    @Override
    public TheoryDetailVO getTheoryFormulaDetail(String compid, Integer stirId, String stgId, String formulaCheckCode,
                                                 String theoryFormulaCode, Integer identifyNumber) throws ErpException {
        TheoryDetailVO theoryDetailVO = new TheoryDetailVO();
        HashMap<String, Object> theoryFormulaDetailMap = formulaMapper.getTheoryFormulaDetail(compid, stgId,
                formulaCheckCode, theoryFormulaCode, identifyNumber);
        if (theoryFormulaCode == null) {
            throw new ErpException(ErrEumn.THEORY_FORMULA_DETAIL_IS_NULL);
        }
        if (theoryFormulaDetailMap == null) {
            throw new ErpException(ErrEumn.THEORY_FORMULA_DETAIL_IS_NULL);
        }
        //给theoryDetailVO对象赋值
        getTheoryDetailVO(theoryFormulaDetailMap, theoryDetailVO);
        //获取材料，库位，材料用量的对应关系，赋值给theoryDetailVO对象
        List<FormulaDataVO> formulas = getTheoryFormulas(compid, stirId, theoryFormulaDetailMap);
        theoryDetailVO.setFormulas(formulas);
        return theoryDetailVO;
    }


    @Override
    public TheoryDetailVO getStirIdTaskUnRealFormula(String compid, String taskId, Integer stirId) {
        TheoryDetailVO theoryDetailVO = new TheoryDetailVO();
        HashMap<String, Object> map = formulaMapper.getStirIdTaskUnRealFormula(compid, taskId, stirId);
        if (map != null) {
            //给theoryDetailVO对象赋值
            getTheoryDetailVO(map, theoryDetailVO);
            List<FormulaDataVO> formulas = getTaskFormulas(compid, stirId, map);
            theoryDetailVO.setFormulas(formulas);
        }
        return theoryDetailVO;
    }

    @Override
    public TheoryDetailVO getStirIdTaskTheoryFormula(String compid, String taskId, Integer stirId) {
        TheoryDetailVO theoryDetailVO = new TheoryDetailVO();
        HashMap<String, Object> map = formulaMapper.getStirIdTaskTheoryFormula(compid, taskId, stirId);
        if (map != null) {
            //给theoryDetailVO对象赋值
            getTheoryDetailVO(map, theoryDetailVO);
            List<FormulaDataVO> formulas = getTaskFormulas(compid, stirId, map);
            theoryDetailVO.setFormulas(formulas);
        }

        return theoryDetailVO;
    }

    @Override
    public Map<String, Integer> getTheoryFormulaMode(String compid) {
        Integer theoryFormulaMode = systemVarInitMapper.getTheoryFormulaMode(compid);
        if (theoryFormulaMode == null) {
            theoryFormulaMode = 0;
        }
        Map<String, Integer> map = new HashMap<>();
        map.put("theoryFormulaMode", theoryFormulaMode);
        return map;
    }

    @Override
    public void saveTaskUnrealFormula(TaskProduceFormula taskProduceFormula) throws ErpException {
        //保存备份配比，同时生成实际配比编号
        TaskProduceFormula saveTaskProduceFormula = saveTaskProduceFormula(taskProduceFormula);
        TaskUnrealFormula taskUnrealFormula = new TaskUnrealFormula();
        BeanUtils.copyProperties(saveTaskProduceFormula, taskUnrealFormula);
        TaskUnrealFormula saveTaskUnrealFormula = taskUnrealFormulaRepository.save(taskUnrealFormula);

        try {
            //与本地数据库进行同步
            syncPlugin.save(saveTaskUnrealFormula, "LM_TaskUnrealFormula", "INS",
                    taskProduceFormula.getCompid());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //保存理论配比日志信息
        TaskUnrealFormulaLog taskUnrealFormulaLog = saveTaskUnrealFormulaLog(taskUnrealFormula);
        try {
            syncPlugin.save(taskUnrealFormulaLog, "LM_taskunrealformula_operlog", "INS",
                    taskProduceFormula.getCompid());
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void saveTaskTheoryFormula(TaskProduceFormula taskProduceFormula) throws ErpException {
        TaskProduceFormula saveTaskProduceFormula = saveTaskProduceFormula(taskProduceFormula);
        TaskTheoryFormula taskTheoryFormula = new TaskTheoryFormula();
        BeanUtils.copyProperties(saveTaskProduceFormula, taskTheoryFormula);
        TaskTheoryFormula saveTaskTheoryFormula = taskTheoryFormulaRepository.save(taskTheoryFormula);
        try {
            //数据同步
            syncPlugin.save(saveTaskTheoryFormula, "LM_tasktheoryformula", "INS",
                    taskProduceFormula.getCompid());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //修改任务单的配比标志状态,并存入sync_data表中
        updateTaskPlanFormulaStatus(saveTaskTheoryFormula);


        //保存实际配比日志信息
        TaskTheoryFormulaLog taskTheoryFormulaLog = saveTaskTheoryFormulaLog(taskTheoryFormula);
        try {
            syncPlugin.save(taskTheoryFormulaLog, "LM_tasktheoryformula_operlog", "INS",
                    taskProduceFormula.getCompid());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //添加和保存配比时修改任务单下面的配比标识状态
    private void updateTaskPlanFormulaStatus(TaskTheoryFormula taskTheoryFormula) throws ErpException {
        if (taskTheoryFormula == null) {
            throw new ErpException(ErrEumn.SAVE_TASK_THEORY_FORMULA_ERROR);
        }
        taskPlanMapper.updateTaskPLanFormulaStatus(taskTheoryFormula.getCompid(), taskTheoryFormula.getTaskId(),
                taskTheoryFormula.getFormulaCode(), taskTheoryFormula.getTheoryFormulaCode(), sdf.format(new Date()));

        HashMap<String, String> map = taskPlanMapper.selectOneByTaskId(taskTheoryFormula.getTaskId(),
                taskTheoryFormula.getCompid());
        map.put("CreateTime", SimpleDateFormatUtil.timeConvert(map.get("CreateTime")));
        map.put("PreTime", SimpleDateFormatUtil.timeConvert(map.get("PreTime")));
        map.put("TaskOverTime", SimpleDateFormatUtil.timeConvert(map.get("TaskOverTime")));
        map.put("VerifyTime", SimpleDateFormatUtil.timeConvert(map.get("VerifyTime")));
        map.put("FormulaTime", SimpleDateFormatUtil.timeConvert(map.get("FormulaTime")));
        map.put("OpenTime", SimpleDateFormatUtil.timeConvert(map.get("OpenTime")));
        map.put("OverTime", SimpleDateFormatUtil.timeConvert(map.get("OverTime")));
        map.put("LinkPipeTime", SimpleDateFormatUtil.timeConvert(map.get("LinkPipeTime")));
        map.put("DownTime", SimpleDateFormatUtil.timeConvert(map.get("DownTime")));
        map.put("AdjustmentTime", SimpleDateFormatUtil.timeConvert(map.get("AdjustmentTime")));
        map.put("LinkPipeOverTime", SimpleDateFormatUtil.timeConvert(map.get("LinkPipeOverTime")));
        map.put("TaskOpenTime", SimpleDateFormatUtil.timeConvert(map.get("TaskOpenTime")));
        map.put("VerifyTimeTwo", SimpleDateFormatUtil.timeConvert(map.get("VerifyTimeTwo")));
        // 数据同步
        try {
            syncPlugin.save(map, "pt_taskplan", "UP", taskTheoryFormula.getCompid());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void verifyFormula(String compid, String taskId, String formulaCode, Integer stirId, Integer verifyStatus) {
        String currentTime = sdf.format(new Date());
        //审核理论配比
        formulaMapper.verifyTaskUnrealFormula(compid, taskId, formulaCode, stirId, verifyStatus, currentTime);
        //把理论配比信息添加到sync_data表中
        HashMap<String, String> unrealFormula = formulaMapper.getTaskUnrealFormula(compid, taskId, stirId);
        unrealFormula.put("VerifyTime", SimpleDateFormatUtil.timeConvert(unrealFormula.get("VerifyTime")));
        try {
            syncPlugin.save(unrealFormula, "LM_taskunrealformula", "UP",
                    compid);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //审核实际配比
        formulaMapper.verifyTaskTheoryFormula(compid, taskId, formulaCode, stirId, verifyStatus, currentTime);
        //把实际配比信息添加到sync_data表中
        HashMap<String, String> taskTheoryFormula = formulaMapper.getTaskTheoryFormula(compid, taskId, stirId);
        taskTheoryFormula.put("VerifyTime", SimpleDateFormatUtil.timeConvert(taskTheoryFormula.get("VerifyTime")));
        try {
            syncPlugin.save(taskTheoryFormula, "LM_tasktheoryformula", "UP",
                    compid);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    //获取配比模板中 配比材料名与材料用量的对应情况；  such as:  mat1-->matV1,mat2-->matV2
    private List<FormulaDataVO> getTheoryFormulas(String compid, Integer stirId, HashMap<String, Object> map) {
        // 获取各个材料名和材料用量的一一对应关系
        Map<String, BigDecimal> matToMatValue = getMatValue(map, 23);
        List<FormulaDataVO> formulaDataVOS = new ArrayList<>();
        //查询库位列表
        List<StockVO> stockVOList = stockMapper.getStock(compid, stirId);
        for (int i = 0; i < 23; i++) {
            FormulaDataVO formulaDataVO = new FormulaDataVO();
            if (i < stockVOList.size()) {
                // 获取库位
                StockVO stockVO = stockVOList.get(i);
                //保存材料名
                if (stockVO.getMatCode() == null) {
                    formulaDataVO.setMat(stockVO.getMatName());
                } else {
                    formulaDataVO.setMat(stockVO.getMatName() + "(" + stockVO.getMatCode() + ")");
                }

                //保存含水率和材料用量
                BigDecimal matValue = matToMatValue.get(formulaDataVO.getMat());
                matToMatValue.remove(formulaDataVO.getMat());
                BigDecimal wr = (BigDecimal) map.get("WR" + (i + 1));
                formulaDataVO.setWr(wr == null ? new BigDecimal(0) : wr);
                formulaDataVO.setMatValue(matValue == null ? new BigDecimal(0) : matValue);
                formulaDataVO.setMatParent(stockVO.getMatParent());
                formulaDataVO.setOrderBy(stockVO.getOderBy());
                formulaDataVO.setSortBy(stockVO.getSortBy());

                //保存库位信息
                if (stockVO.getMatParent() == null) {
                    formulaDataVO.setStockName(stockVO.getStoName());
                } else {
                    formulaDataVO.setStockName(stockVO.getMatParent() + "_" + stockVO.getStoName());
                }
            }
            formulaDataVOS.add(formulaDataVO);
        }

        return formulaDataVOS;
    }


    // 获取理论配比和实际配比  配比材料名与材料用量的对应情况；  such as:  mat1-->matV1,mat2-->matV2
    private List<FormulaDataVO> getTaskFormulas(String compid, Integer stirId, HashMap<String, Object> map) {

        List<FormulaDataVO> formulaDataVOS = new ArrayList<>();

        for (int i = 0; i < 27; i++) {
            FormulaDataVO formulaDataVO = new FormulaDataVO();

            BigDecimal value = (BigDecimal) map.get("MatV" + (i + 1));
            String matName = (String) map.get("Mat" + (i + 1));
            BigDecimal wr = (BigDecimal) map.get("WR" + (i + 1));

            //通过正则匹配获取配比材料名称括号中的材料代号
            String pattern = "(?<=\\()[^\\)]+";
            Pattern p = Pattern.compile(pattern);
            Matcher matcher = p.matcher(matName);
            String matCode = "";
            while (matcher.find()) {
                matCode = matcher.group(0);
            }
            // 通过材料名称查询材料对应的库位名称
            StockVO stockVO = stockMapper.getStockByMatCode(compid, stirId, matCode);

            if (stockVO != null) {
                formulaDataVO.setMatParent(stockVO.getMatParent());
                formulaDataVO.setStockName(stockVO.getStoName());
            }
            formulaDataVO.setSortBy(i+1);
            formulaDataVO.setOrderBy(i+1);
            formulaDataVO.setMat(matName);
            formulaDataVO.setMatValue(value == null ? new BigDecimal("0") : value);
            formulaDataVO.setWr(wr);
            formulaDataVOS.add(formulaDataVO);

        }

        return formulaDataVOS;

    }


    //保存配比的前期操作；保存配比前需要先查一下这条配比在配比备份表是否有备份，如果没有，需要先向备份表添加一条数据
    private TaskProduceFormula saveTaskProduceFormula(TaskProduceFormula taskProduceFormula) throws ErpException {
        if (taskProduceFormula == null) {
            throw new ErpException(ErrEumn.ADD_FORMULA_IS_NULL);
        }
        if (taskProduceFormula.getCompid() == null) {
            throw new ErpException(ErrEumn.ADD_USER_ENTERPRISE_IS_NULL);
        }
        if (taskProduceFormula.getTaskId() == null) {
            throw new ErpException(ErrEumn.ADD_TASK_NOT_FOUND_TASKID);
        }
        if (taskProduceFormula.getStirId() == null) {
            throw new ErpException(ErrEumn.ADD_STIR_ID_IS_NULL);
        }

        taskProduceFormula.setUpDown((byte) 0);
        taskProduceFormula.setUpDownMark(0);
        taskProduceFormula.setRecStatus((byte) 1);
        taskProduceFormula.setOpId("Admin");


        //查询是否在配比备份表中备份
        TaskProduceFormula taskProduce = formulaMapper.getTaskProduceFormula(taskProduceFormula.getCompid(),
                taskProduceFormula.getTaskId(), taskProduceFormula.getStirId());
        if (taskProduce == null) {
            //备份配比表中不存在此配比，需要从LM_TheoryFormula表中查询出模板配比，插入LM_TaskProduceFormula
            taskProduce = formulaMapper.getTaskProduceFormulaByTheory(taskProduceFormula.getCompid(),
                    taskProduceFormula.getStgId(), taskProduceFormula.getTheoryFormulaCode(),
                    taskProduceFormula.getIdentifyNumber());
            if (taskProduce == null) {
                throw new ErpException(ErrEumn.THEORY_FORMULA_DETAIL_IS_NULL);
            }

            //生成实际配比编号
            String formulaCode = getFormulaCode(taskProduceFormula.getCompid());
            taskProduce.setFormulaCode(formulaCode);
            taskProduce.setStirId(taskProduceFormula.getStirId());
            taskProduce.setTaskId(taskProduceFormula.getTaskId());
            taskProduce.setUpDown((byte) 0);
            taskProduce.setUpDownMark(0);
            //保存配比备份信息
            taskProduce = taskProduceFormulaRepository.save(taskProduce);

            try {
                syncPlugin.save(taskProduce, "LM_taskproduceformula", "INS",
                        taskProduceFormula.getCompid());
            } catch (SQLException e) {
                e.printStackTrace();
            }

            //保存配比备份日志信息
            TaskProduceFormulaLog taskProduceFormulaLog = saveTaskProduceFormulaLog(taskProduce);

            try {
                syncPlugin.save(taskProduceFormulaLog, "LM_taskproduceformula_operlog", "INS",
                        taskProduceFormula.getCompid());
            } catch (SQLException e) {
                e.printStackTrace();
            }


        }
        if (taskProduceFormula.getFormulaCode() == null) {
            //说明是添加配比的操作
            taskProduceFormula.setFormulaCode(taskProduce.getFormulaCode());
            taskProduceFormula.setVerifyStatus((byte) 0);
            taskProduceFormula.setMixersDoorFull(0);
            taskProduceFormula.setMixersDoorHalf(0);
        }
        return taskProduceFormula;
    }


    //给TheoryDetailVO进行赋值
    private void getTheoryDetailVO(HashMap<String, Object> map, TheoryDetailVO theoryDetailVO) {
        theoryDetailVO.setSlump((String) map.get("Slump"));
        theoryDetailVO.setStgId((String) map.get("StgId"));
        theoryDetailVO.setTheoryFormulaCode((String) map.get("TheoryFormulaCode"));
        theoryDetailVO.setWhiskTime((Integer) map.get("WhiskTime"));
        theoryDetailVO.setVerifyStatus((Boolean) map.get("VerifyStatus"));
        theoryDetailVO.setFormulaCode((String) map.get("FormulaCode"));
        theoryDetailVO.setVerifyId((String) map.get("VerifyId"));
        theoryDetailVO.setVerifyTime((Timestamp) map.get("VerifyTime"));
        theoryDetailVO.setMixersDoorHalf((Integer) map.get("MixersDoorHalf"));
        theoryDetailVO.setMixersDoorFull((Integer) map.get("MixersDoorFull"));
    }


    //生成实际配比编号
    private String getFormulaCode(String compid) {
        SimpleDateFormat sdf = SimpleDateFormatUtil.getSimpleDataFormat("yyMMdd");
        String date = sdf.format(new Date());
        String preFormulaCode = "LMF_" + compid + date;
        String formulaCode = formulaMapper.getFormulaCode(preFormulaCode);
        if (formulaCode == null) {
            //说明当天还没有用手机开过配比
            formulaCode = preFormulaCode + "0001";
        } else {
            String maxId = formulaCode.substring(6, 16);
            formulaCode = formulaCode.substring(0, 6) + (Integer.parseInt(maxId) + 1);
        }
        return formulaCode;
    }


    //保存备份配比日志信息
    private TaskProduceFormulaLog saveTaskProduceFormulaLog(TaskProduceFormula taskProduce) {
        TaskProduceFormulaLog taskProduceFormulaLog = new TaskProduceFormulaLog();
        BeanUtils.copyProperties(taskProduce, taskProduceFormulaLog);
        taskProduceFormulaLog.setOperType(1);
        taskProduceFormulaLog.setOperId("0225");
        taskProduceFormulaLog.setOperTime(sdf.format(new Date()));
        return taskProduceFormulaLogRepository.save(taskProduceFormulaLog);
    }

    //保存理论配比日志信息
    private TaskUnrealFormulaLog saveTaskUnrealFormulaLog(TaskUnrealFormula taskUnrealFormula) {
        TaskUnrealFormulaLog taskUnrealFormulaLog = new TaskUnrealFormulaLog();
        BeanUtils.copyProperties(taskUnrealFormula, taskUnrealFormulaLog);
        taskUnrealFormulaLog.setOperId("0225");
        taskUnrealFormulaLog.setOperTime(sdf.format(new Date()));
        taskUnrealFormulaLog.setOperType(1);
        return taskUnrealFormulaLogRepository.save(taskUnrealFormulaLog);
    }

    //保存实际配比日志信息
    private TaskTheoryFormulaLog saveTaskTheoryFormulaLog(TaskTheoryFormula taskTheoryFormula) {
        TaskTheoryFormulaLog taskTheoryFormulaLog = new TaskTheoryFormulaLog();
        BeanUtils.copyProperties(taskTheoryFormula, taskTheoryFormulaLog);
        taskTheoryFormulaLog.setOperId("0225");
        taskTheoryFormulaLog.setOperType(1);
        taskTheoryFormulaLog.setOperTime(sdf.format(new Date()));
        return taskTheoryFormulaLogRepository.save(taskTheoryFormulaLog);
    }


    /**
     * 以材料名称为key,材料用量为value,把材料和材料用量的对应关系存进map集合中
     */
    private Map<String, BigDecimal> getMatValue(HashMap<String, Object> map, Integer number) {
        Map<String, BigDecimal> matValueMap = new HashMap<>();
        for (int i = 0; i < number; i++) {
            BigDecimal value = (BigDecimal) map.get("MatV" + (i + 1));
            String matName = (String) map.get("Mat" + (i + 1));
            if (matName != null) {
                matValueMap.put(matName, value);
            }

        }
        return matValueMap;
    }


}
