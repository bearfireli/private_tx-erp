package com.hntxrj.controller.produce;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hntxrj.entity.PageBean;
import com.hntxrj.server.FormulaService;
import com.hntxrj.server.LMTaskServer;
import com.hntxrj.util.WriteLog;
import com.hntxrj.vo.JsonVo;
import com.hntxrj.vo.JsonVoAndPage;
import com.hntxrj.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping(value = "/laboratroy")
@Scope("prototype")
@EnableAsync
public class LaboratroyController {

    private final FormulaService formulaService;
    private final WriteLog writeLog;

    private final LMTaskServer lmTaskServer;

    @Autowired
    public LaboratroyController(FormulaService formulaService, WriteLog writeLog, LMTaskServer lmTaskServer) {
        this.formulaService = formulaService;
        this.writeLog = writeLog;
        this.lmTaskServer = lmTaskServer;
    }


    /**
     * 开具配比  // 存储过程
     *
     * @param mark       0位列表   1 详情
     * @param currPage   当前页
     * @param PageSize   页长度
     * @param TaskStatus 状态
     * @param EppName    施工单位
     * @param Placing    浇筑部位
     * @param TaskId     任务单号
     * @param PreTime    开具时间
     * @param EndTime    结束时间
     * @param request    请求
     * @return jsoN
     */
    @RequestMapping(value = "/spQueryVPTPlanFind")
    public JsonVoAndPage spQueryVPTPlanFind(@RequestParam Integer mark,
                                            @RequestParam(defaultValue = "0") Integer currPage,
                                            @RequestParam(defaultValue = "10") Integer PageSize,
                                            Integer TaskStatus,
                                            String EppName,
                                            String Placing,
                                            String TaskId,
                                            String PreTime,
                                            String EndTime,
                                            String compid,
                                            String builderName,
                                            Integer FormulaStatus,
                                            String opid,
                                            HttpServletRequest request) {
        JsonVoAndPage jsonVoAndPage = new JsonVoAndPage();


        //compid  不能为空
        if (StringUtils.isEmpty(compid)) {
            jsonVoAndPage.setCode(9);
            jsonVoAndPage.setMsg("请登录");
        }
        //对参数进行判断
        if (StringUtils.isEmpty(EppName)) {
            EppName = null;
        }
        if (StringUtils.isEmpty(Placing)) {
            Placing = null;
        }
        if (StringUtils.isEmpty(TaskId)) {
            TaskId = null;
        }
        if (StringUtils.isEmpty(PreTime)) {
            PreTime = null;
        }
        if (StringUtils.isEmpty(EndTime)) {
            EndTime = null;
        }

        // 数据安全性验证


        //当 状态  查询时   不加时间
        JSONArray jsonArray = formulaService.spQueryVPTPlanFind(mark, compid, currPage, PageSize, TaskStatus,
                EppName, Placing, TaskId, PreTime, EndTime, builderName, FormulaStatus, opid);

        PageBean pageBean = new PageBean(PageSize, currPage);
        //jsonarry  不能为空
        if (jsonArray != null && jsonArray.size() > 0) {
            //mark  = 0 是列表查询   要返回pagebean
            if (mark != null && mark == 0) {
                //取统计的总条数
                if (jsonArray.size() >= 2) {
                    String s = jsonArray.getJSONArray(1).get(0).toString();
                    System.out.println(s);
                    String substring = s.substring(s.indexOf(":") + 1, s.indexOf("}"));
                    if (substring.matches("[0-9]*")) {
                        pageBean.setRecordCount(Integer.parseInt(substring));
                    }
                }
                jsonVoAndPage.setCode(0);
                jsonVoAndPage.setMsg("ok");
                jsonVoAndPage.setData(jsonArray);
                jsonVoAndPage.setPageBean(pageBean);
                return jsonVoAndPage;
            } else {
                //否则 是开具详情查询  不用返回pagbean
                jsonVoAndPage.setCode(0);
                jsonVoAndPage.setMsg("ok");
                jsonVoAndPage.setData(jsonArray);
                return jsonVoAndPage;
            }
        } else {
            //返回页面错误code = 1
            jsonVoAndPage.setCode(1);
            jsonVoAndPage.setMsg("result null");
            return jsonVoAndPage;
        }
    }

    /**
     * 开具审核
     *
     * @param TaskId       任务单号
     * @param compid       企业
     * @param opid         用户
     * @param verifystatus 线号审核状态
     * @param stirid       线号
     * @param formulaCode  配比编号
     * @return jsonArray
     */
    @RequestMapping(value = "/spVerifyLMTaskTheoryFormula")
    public JsonVo sp_Verify_LM_TaskTheoryFormula(@RequestParam String TaskId,
                                                 @RequestParam String compid,
                                                 @RequestParam String opid,
                                                 @RequestParam Integer verifystatus,
                                                 @RequestParam Integer stirid,
                                                 String formulaCode) {
        JsonVoAndPage jsonVoAndPage = new JsonVoAndPage();
        JSONArray jsonArray = formulaService.sp_Verify_LM_TaskTheoryFormula(compid, TaskId, opid,
                verifystatus, stirid, formulaCode);
        //jsonArray  不能为空
        if (jsonArray != null && jsonArray.size() > 0) {
            //获取提示信息
            JSONObject o = (JSONObject) jsonArray.getJSONArray(0).get(0);
            String result = o.getString("result");
            //成功就code  = 0
            if (result.contains("成功")) {
                jsonVoAndPage.setCode(0);
                jsonVoAndPage.setMsg(result);
            } else {
                //失败code  =1
                jsonVoAndPage.setCode(1);
                jsonVoAndPage.setMsg(result);
            }
            //设置返回页面的data
            jsonVoAndPage.setData(jsonArray);
            return jsonVoAndPage;
        } else {
            //jsonArry 能不为空
            jsonVoAndPage.setCode(1);
            jsonVoAndPage.setMsg("result null");
            return jsonVoAndPage;
        }


    }


    /**
     * 修改配比
     *
     * @param taskid      任务单
     * @param compid      企业
     * @param formulacode 理论配比好
     * @param opid        业务员
     * @param stgid       砼标记
     * @param slump       塌落度
     * @param whisktime   搅拌时间
     * @param wr1         含水量  wr1
     * @param wr2         含水量 wr2
     * @param wr3
     * @param wr4
     * @param wr5
     * @param wr6
     * @param wr7
     * @param wr8
     * @param mat1        材料  mat1
     * @param mat2        材料  mat2
     * @param mat3
     * @param mat4
     * @param mat5
     * @param mat6
     * @param mat7
     * @param mat8
     * @param mat9
     * @param mat10
     * @param mat11
     * @param mat12
     * @param mat13
     * @param mat14
     * @param mat15
     * @param mat16
     * @param mat17
     * @param mat18
     * @param mat19
     * @param mat20
     * @param mat21
     * @param mat22
     * @param mat23
     * @param mat24
     * @param mat25
     * @param mat26
     * @param mat27
     * @param matv1       材料用量 matv1
     * @param matv2       材料用量 matv2
     * @param matv3
     * @param matv4
     * @param matv5
     * @param matv6
     * @param matv7
     * @param matv8
     * @param matv9
     * @param matv10
     * @param matv11
     * @param matv12
     * @param matv13
     * @param matv14
     * @param matv15
     * @param matv16
     * @param matv17
     * @param matv18
     * @param matv19
     * @param matv20
     * @param matv21
     * @param matv22
     * @param matv23
     * @param matv24
     * @param matv25
     * @param matv26
     * @param matv27
     * @param totalnum    总量
     * @param stirid      线号
     * @return json
     */
    @RequestMapping(value = "/spinsertLMTaskTheoryFormula")
    public JsonVo LMTask(String taskid,
                         String compid,
                         String formulacode,
                         String opid,
                         String stgid,
                         String slump,
                         String whisktime,
                         @RequestParam(defaultValue = "0.00") Double wr1,
                         @RequestParam(defaultValue = "0.00") Double wr2,
                         @RequestParam(defaultValue = "0.00") Double wr3,
                         @RequestParam(defaultValue = "0.00") Double wr4,
                         @RequestParam(defaultValue = "0.00") Double wr5,
                         @RequestParam(defaultValue = "0.00") Double wr6,
                         @RequestParam(defaultValue = "0.00") Double wr7,
                         @RequestParam(defaultValue = "0.00") Double wr8,
                         @RequestParam(defaultValue = "0.00") Double wr9,
                         @RequestParam(defaultValue = "0.00") Double wr10,
                         @RequestParam(defaultValue = "0.00") Double wr11,
                         @RequestParam(defaultValue = "0.00") Double wr12,
                         @RequestParam(defaultValue = "0.00") Double wr13,
                         @RequestParam(defaultValue = "0.00") Double wr14,
                         @RequestParam(defaultValue = "0.00") Double wr15,
                         @RequestParam(defaultValue = "0.00") Double wr16,
                         @RequestParam(defaultValue = "0.00") Double wr17,
                         @RequestParam(defaultValue = "0.00") Double wr18,
                         @RequestParam(defaultValue = "0.00") Double wr19,
                         @RequestParam(defaultValue = "0.00") Double wr20,
                         @RequestParam(defaultValue = "0.00") Double wr21,
                         @RequestParam(defaultValue = "0.00") Double wr22,
                         @RequestParam(defaultValue = "0.00") Double wr23,
                         @RequestParam(defaultValue = "0.00") Double wr24,
                         @RequestParam(defaultValue = "0.00") Double wr25,
                         @RequestParam(defaultValue = "0.00") Double wr26,
                         @RequestParam(required = false) String mat1,
                         @RequestParam(required = false) String mat2,
                         @RequestParam(required = false) String mat3,
                         @RequestParam(required = false) String mat4,
                         @RequestParam(required = false) String mat5,
                         @RequestParam(required = false) String mat6,
                         @RequestParam(required = false) String mat7,
                         @RequestParam(required = false) String mat8,
                         @RequestParam(required = false) String mat9,
                         @RequestParam(required = false) String mat10,
                         @RequestParam(required = false) String mat11,
                         @RequestParam(required = false) String mat12,
                         @RequestParam(required = false) String mat13,
                         @RequestParam(required = false) String mat14,
                         @RequestParam(required = false) String mat15,
                         @RequestParam(required = false) String mat16,
                         @RequestParam(required = false) String mat17,
                         @RequestParam(required = false) String mat18,
                         @RequestParam(required = false) String mat19,
                         @RequestParam(required = false) String mat20,
                         @RequestParam(required = false) String mat21,
                         @RequestParam(required = false) String mat22,
                         @RequestParam(required = false) String mat23,
                         @RequestParam(required = false) String mat24,
                         @RequestParam(required = false) String mat25,
                         @RequestParam(required = false) String mat26,
                         @RequestParam(required = false) String mat27,
                         @RequestParam(defaultValue = "0.00") Double matv1,
                         @RequestParam(defaultValue = "0.00") Double matv2,
                         @RequestParam(defaultValue = "0.00") Double matv3,
                         @RequestParam(defaultValue = "0.00") Double matv4,
                         @RequestParam(defaultValue = "0.00") Double matv5,
                         @RequestParam(defaultValue = "0.00") Double matv6,
                         @RequestParam(defaultValue = "0.00") Double matv7,
                         @RequestParam(defaultValue = "0.00") Double matv8,
                         @RequestParam(defaultValue = "0.00") Double matv9,
                         @RequestParam(defaultValue = "0.00") Double matv10,
                         @RequestParam(defaultValue = "0.00") Double matv11,
                         @RequestParam(defaultValue = "0.00") Double matv12,
                         @RequestParam(defaultValue = "0.00") Double matv13,
                         @RequestParam(defaultValue = "0.00") Double matv14,
                         @RequestParam(defaultValue = "0.00") Double matv15,
                         @RequestParam(defaultValue = "0.00") Double matv16,
                         @RequestParam(defaultValue = "0.00") Double matv17,
                         @RequestParam(defaultValue = "0.00") Double matv18,
                         @RequestParam(defaultValue = "0.00") Double matv19,
                         @RequestParam(defaultValue = "0.00") Double matv20,
                         @RequestParam(defaultValue = "0.00") Double matv21,
                         @RequestParam(defaultValue = "0.00") Double matv22,
                         @RequestParam(defaultValue = "0.00") Double matv23,
                         @RequestParam(defaultValue = "0.00") Double matv24,
                         @RequestParam(defaultValue = "0.00") Double matv25,
                         @RequestParam(defaultValue = "0.00") Double matv26,
                         @RequestParam(defaultValue = "0.00") Double matv27,
                         Double totalnum,
                         String stirid
    ) {
        JsonVo jsonVo = new JsonVo();
        //执行修改操作
        JSONArray jsonArray = lmTaskServer.sp_insert_LM_TaskTheoryFormula(taskid, compid, formulacode, opid, stgid, whisktime, slump,
                wr1, wr2, wr3, wr4, wr5, wr6, wr7, wr8,
                mat1, mat2, mat3, mat4, mat5, mat6, mat7, mat8, mat9, mat10, mat11,
                mat12, mat13, mat14, mat15, mat16, mat17, mat18, mat19, mat20, mat21, mat22,
                mat23, mat24, mat25, mat26, mat27,
                matv1, matv2, matv3, matv4, matv5, matv6, matv7, matv8, matv9, matv10, matv11, matv12, matv13, matv14, matv15, matv16, matv17, matv18, matv19, matv20,
                matv21, matv22, matv23, matv24, matv25, matv26, matv27,
                totalnum,
                stirid);
        //返回值,判断是否修改成功
        System.out.println(jsonArray);
        JSONObject jsonObject = null;
        try {
            //获取分页条数
            jsonObject = (JSONObject) jsonArray.getJSONArray(0).get(0);
            String result = jsonObject.getString("result");
            //修改成功
            if (result.contains("成功")) {
                jsonVo.setCode(0);
                jsonVo.setMsg(result);
                jsonVo.setData(jsonArray);
                return jsonVo;
                //修改失败
            } else {
                jsonVo.setCode(1);
                jsonVo.setMsg(result);
                jsonVo.setData(jsonArray);
                return jsonVo;
            }
        } catch (Exception e) {
            e.printStackTrace();
            jsonVo.setCode(1);
            jsonVo.setData(jsonArray);
            writeLog.init("配比修改", "1", e.toString(), opid);
            try {
                writeLog.write();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            return jsonVo;
        }
    }


    @PostMapping("/getFormulaList")
    public ResultVO getFormulaList(Integer taskStatus,
                                   String eppCode,
                                   String placing,
                                   String taskId,
                                   Long startTime,
                                   Long endTime,
                                   String compid,
                                   String builderCode,
                                   Integer formulaStatus,
                                   String opid,
                                   @RequestParam(defaultValue = "1") Integer page,
                                   @RequestParam(defaultValue = "10") Integer pageSize) {
        SimpleDateFormat sdf = new SimpleDateFormat();
        return ResultVO.create(formulaService.getFormulaList(taskStatus, eppCode, placing, taskId,
                startTime == null ? null : sdf.format(new Date(startTime)),
                endTime == null ? null : sdf.format(new Date(endTime)),
                compid, builderCode, formulaStatus, opid, page, pageSize));

    }

    /**
     * 通过任务单获取配比详情
     *
     * @param compid 企业id
     * @param taskId 任务id
     * @return 配比详情
     */
    @PostMapping("/getFormulaByTaskId")
    public ResultVO getFormulaByTaskId(String compid, String taskId) {
        return ResultVO.create(formulaService.getFormulaByTaskId(compid, taskId));
    }

    @PostMapping("/getFormulaInfo")
    public ResultVO getFormulaInfo(String compid, String taskId, String stirId) {
        return ResultVO.create(formulaService.getFormulaInfo(compid, taskId, stirId));
    }

}
