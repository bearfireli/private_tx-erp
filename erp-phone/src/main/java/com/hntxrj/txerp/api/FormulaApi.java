package com.hntxrj.txerp.api;

import com.hntxrj.txerp.core.exception.ErpException;
import com.hntxrj.txerp.core.util.SimpleDateFormatUtil;
import com.hntxrj.txerp.entity.TaskProduceFormula;
import com.hntxrj.txerp.server.FormulaService;
import com.hntxrj.txerp.vo.ResultVO;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping("/api/formula")
public class FormulaApi {

    private final FormulaService formulaService;
    private final SimpleDateFormat sdf = SimpleDateFormatUtil.getDefaultSimpleDataFormat();

    @Autowired
    public FormulaApi(FormulaService formulaService) {
        this.formulaService = formulaService;
    }


    /**
     * 生活配比列表
     *
     * @param compid        企业id
     * @param taskStatus    任务状态
     * @param eppCode       工程名称代号
     * @param placing       浇筑部位
     * @param taskId        任务单号
     * @param startTime     开始时间
     * @param endTime       结束时间
     * @param builderCode   施工单位代号
     * @param formulaStatus 配比开配状态    1:已开配  0：未开配
     * @param opid          操作员
     * @param page          当前页码
     * @param pageSize      每页条数
     */
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
        SimpleDateFormat sdf = SimpleDateFormatUtil.getSimpleDataFormat("yyyy-MM-dd HH:mm:ss");
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

    @ApiOperation("获取配比模板列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "compid", value = "企业id", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "searchWord", value = "搜索关键字", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "IdentifyNumber", value = "获取配比模板类型(1:实际配比,2:理论配比)",
                    dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "beginTime", value = "开始时间", required = true, dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "endTime", value = "结束时间", required = true, dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "page", value = "当前页码", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页条数", dataType = "int", paramType = "query"),
    })
    @PostMapping("theoryFormulaList")
    public ResultVO theoryFormulaList(String compid, String searchWord,
                                      @RequestParam(defaultValue = "2") Integer IdentifyNumber,
                                      Long beginTime, Long endTime,
                                      @RequestParam(defaultValue = "1") Integer page,
                                      @RequestParam(defaultValue = "10") Integer pageSize) {
        return ResultVO.create(formulaService.theoryFormulaList(compid, searchWord, IdentifyNumber,
                beginTime == null ? null : sdf.format(beginTime),
                endTime == null ? null : sdf.format(endTime), page, pageSize));
    }


    @ApiOperation("获取配比模板详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "compid", value = "企业id", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "stirId", value = "线号", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "stgId", value = "砼标号", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "formulaCheckCode", value = "配比检验号", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "theoryFormulaCode", value = "理论配比编号", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "identifyNumber", value = "配比取值标识号", dataType = "int", paramType = "query"),
    })
    @PostMapping("getTheoryFormulaDetail")
    public ResultVO getTheoryFormulaDetail(String compid, @RequestParam(defaultValue = "1") Integer stirId, String stgId,
                                           String formulaCheckCode, String theoryFormulaCode,
                                           Integer identifyNumber) throws ErpException {

        return ResultVO.create(formulaService.getTheoryFormulaDetail(compid, stirId, stgId, formulaCheckCode, theoryFormulaCode,
                identifyNumber));
    }


    @ApiOperation("获取任务单下某个线号的理论配比")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "compid", value = "企业id", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "taskId", value = "任务单号", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "stirId", value = "线号", required = true, dataType = "int", paramType = "query"),
    })
    @PostMapping("getStirIdTaskUnRealFormula")
    public ResultVO getStirIdTaskUnRealFormula(String compid, String taskId, Integer stirId) {
        return ResultVO.create(formulaService.getStirIdTaskUnRealFormula(compid, taskId, stirId));
    }


    @ApiOperation("获取任务单下某个线号的实际配比")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "compid", value = "企业id", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "taskId", value = "任务单号", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "stirId", value = "线号", required = true, dataType = "int", paramType = "query"),
    })
    @PostMapping("getStirIdTaskTheoryFormula")
    public ResultVO getStirIdTaskTheoryFormula(String compid, String taskId, Integer stirId) {
        return ResultVO.create(formulaService.getStirIdTaskTheoryFormula(compid, taskId, stirId));
    }


    @ApiOperation("保存理论配比")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "taskUnrealFormula", value = "理论配比对象", required = true,
                    dataType = "String", paramType = "query"),
    })
    @PostMapping("saveTaskUnrealFormula")
    public ResultVO saveTaskUnrealFormula(@RequestBody TaskProduceFormula taskProduceFormula) throws ErpException {
        formulaService.saveTaskUnrealFormula(taskProduceFormula);
        return ResultVO.create();
    }

    @ApiOperation("保存实际配比")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "taskUnrealFormula", value = "实际配比对象", required = true,
                    dataType = "String", paramType = "query"),
    })
    @PostMapping("saveTaskTheoryFormula")
    public ResultVO saveTaskTheoryFormula(@RequestBody TaskProduceFormula taskProduceFormula) throws ErpException {
        formulaService.saveTaskTheoryFormula(taskProduceFormula);
        return ResultVO.create();
    }


    @ApiOperation("配比审核")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "compid", value = "企业id", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "taskId", value = "任务单号", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "formulaCode", value = "实际配比编号", required = true, dataType = "String",
                    paramType = "query"),
            @ApiImplicitParam(name = "stirId", value = "搅拌楼号", required = true, dataType = "int", paramType = "query"),
    })
    @PostMapping("verifyFormula")
    public ResultVO verifyFormula(String compid, String taskId, String formulaCode, Integer stirId,
                                  @RequestParam(defaultValue = "0") Integer verifyStatus) {
        formulaService.verifyFormula(compid, taskId, formulaCode, stirId, verifyStatus);
        return ResultVO.create();
    }


    @ApiOperation("获取用户应用配比方式")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "compid", value = "企业id", required = true, dataType = "String", paramType = "query"),
    })
    @PostMapping("getTheoryFormulaMode")
    public ResultVO getTheoryFormulaMode(String compid) {
        return ResultVO.create(formulaService.getTheoryFormulaMode(compid));
    }


}
