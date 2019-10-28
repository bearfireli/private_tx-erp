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
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
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
