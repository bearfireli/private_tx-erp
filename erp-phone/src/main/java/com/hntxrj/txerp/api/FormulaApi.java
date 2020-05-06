package com.hntxrj.txerp.api;

import com.hntxrj.txerp.server.FormulaService;
import com.hntxrj.txerp.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping("/api/formula")
public class FormulaApi {

    private final FormulaService formulaService;

    @Autowired
    public FormulaApi(FormulaService formulaService) {
        this.formulaService = formulaService;
    }





    /**
     * 生活配比列表
     * @param compid 企业id
     * @param taskStatus 任务状态
     * @param eppCode 工程名称代号
     * @param placing 浇筑部位
     * @param taskId 任务单号
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @param builderCode 施工单位代号
     * @param formulaStatus 配比开配状态    1:已开配  0：未开配
     * @param opid 操作员
     * @param page 当前页码
     * @param pageSize 每页条数
     * */
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
        SimpleDateFormat sdf = com.hntxrj.txerp.core.util.SimpleDateFormatUtil.getSimpleDataFormat("yyyy-MM-dd HH:mm:ss");
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
