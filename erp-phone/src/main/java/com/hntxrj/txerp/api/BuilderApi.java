package com.hntxrj.txerp.api;

import com.hntxrj.txerp.core.exception.ErpException;
import com.hntxrj.txerp.core.util.SimpleDateFormatUtil;
import com.hntxrj.txerp.server.BuilderService;
import com.hntxrj.txerp.vo.ResultVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;

@Api(tags = "工地端")
@RestController
@RequestMapping("/api/builder")
@Slf4j
public class BuilderApi {
    private final BuilderService builderService;
    private SimpleDateFormat sdf = SimpleDateFormatUtil.getSimpleDataFormat("yyyy-MM-dd HH:mm:ss");

    @Autowired
    public BuilderApi(BuilderService builderService) {
        this.builderService = builderService;

    }


    @ApiOperation("获取施工单位下拉")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "compid", value = "企业id", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "builderName", value = "施工单位名称", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "page", value = "页码", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页数量", dataType = "int", paramType = "query")
    })
    @PostMapping("/getDropDown")
    public ResultVO getDropDown(String builderName, String compid,
                                @RequestParam(defaultValue = "1") Integer page,
                                @RequestParam(defaultValue = "10") Integer pageSize) {
        return ResultVO.create(builderService.getBuilderDropDown(compid, builderName, page, pageSize));
    }


    @ApiOperation("工地端App发货统计")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "buildId", value = "施工方用户id", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "type", value = "查询时间类型; 1:派车时间；0:离场时间", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "eppCode", value = "工程代码", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "placing", value = "浇筑部位", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "taskId", value = "任务单号", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "stgId", value = "砼标号", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "beginTime", value = "开始时间", dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "endTime", value = "结束时间", dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "page", value = "页数", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页显示多少条", dataType = "int", paramType = "query")
    })
    @PostMapping("/getBuildConcreteCount")
    public ResultVO getBuilderConcreteCount(Integer buildId, String eppCode, String placing,
                                            String taskId, String stgId,
                                            Long beginTime, Long endTime, Integer type,
                                            @RequestParam(defaultValue = "1") Integer page,
                                            @RequestParam(defaultValue = "10") Integer pageSize) throws ErpException {
        return ResultVO.create(builderService.getBuilderConcreteCount(buildId, eppCode, placing, taskId,
                stgId, beginTime == null ? null : sdf.format(new Date(beginTime)),
                endTime == null ? null : sdf.format(new Date(endTime)), type, page, pageSize));

    }

    @ApiOperation("工地端App发货总量（销量）统计")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "buildId", value = "施工方用户id", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "type", value = "查询时间类型; 1:派车时间；0:离场时间", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "eppCode", value = "工程代码", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "placing", value = "浇筑部位", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "taskId", value = "任务单号", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "stgId", value = "砼标号", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "beginTime", value = "开始时间", dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "endTime", value = "结束时间", dataType = "Long", paramType = "query")
    })
    @PostMapping("/getBuildConcreteSum")
    public ResultVO getBuilderConcreteSum(Integer buildId, String eppCode, String placing,
                                          String taskId, String stgId,
                                          Long beginTime, Long endTime, Integer type) throws ErpException {
        return ResultVO.create(builderService.getBuilderConcreteSum(buildId, eppCode, placing, taskId,
                stgId, beginTime == null ? null : sdf.format(new Date(beginTime)),
                endTime == null ? null : sdf.format(new Date(endTime)), type));
    }

    @ApiOperation("获取工地端小票签收列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "buildId", value = "施工方用户id", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "eppCode", value = "工程代码", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "upStatus", value = "签收状态", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "builderCode", value = "施工单位代号", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "placing", value = "浇筑部位", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "taskId", value = "任务单号", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "taskStatus", value = "任务单状态", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "beginTime", value = "开始时间", dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "endTime", value = "结束时间", dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "page", value = "页数", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页数量", dataType = "int", paramType = "query"),
    })
    @PostMapping("/getBuildTaskSaleInvoiceList")
    public ResultVO getBuildTaskSaleInvoiceList(Integer buildId, Long beginTime, Long endTime, String eppCode,
                                                Byte upStatus, String builderCode, String taskId, String placing,
                                                String taskStatus, @RequestParam(defaultValue = "1") Integer page,
                                                @RequestParam(defaultValue = "10") Integer pageSize) throws ErpException {
        return ResultVO.create(builderService.getBuildTaskSaleInvoiceList(buildId,
                beginTime == null ? null : sdf.format(new Date(beginTime)),
                endTime == null ? null : sdf.format(new Date(endTime)),
                eppCode, upStatus, builderCode, taskId, placing, taskStatus, page, pageSize));
    }


    @ApiOperation("调度派车列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "buildId", value = "施工方用户id", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "searchName", value = "搜索关键字", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "page", value = "页数", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页数量", dataType = "int", paramType = "query"),
    })
    @PostMapping("/getBuildSendCarList")
    public ResultVO getBuildSendCarList(Integer buildId,
                                        @RequestParam(defaultValue = "1") Integer page,
                                        @RequestParam(defaultValue = "10") Integer pageSize,
                                        String searchName) throws ErpException {
        return ResultVO.create(builderService.getBuildSendCarList(buildId, searchName, page, pageSize));
    }


    @ApiOperation("工地端获取任务单详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "buildId", value = "施工方用户id", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "taskId", value = "任务单id", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "compid", value = "企业id", required = true, dataType = "String", paramType = "query"),
    })
    @PostMapping("/getBuildTaskPlanDetail")
    public ResultVO getBuildTaskPlanDetail(String compid, String taskId, Integer buildId) throws ErpException {
        return ResultVO.create(builderService.getBuildTaskPlanDetail(compid, taskId, buildId));
    }

    @ApiOperation("工地端获取小票详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "小票id", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "compid", value = "企业id", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "buildId", value = "施工方用户id", required = true, dataType = "String", paramType = "query"),
    })
    @PostMapping("/getBuildTaskSaleInvoiceDetail")
    public ResultVO getTaskSaleInvoiceDetail(String compid, Integer id, Integer buildId) throws ErpException {
        return ResultVO.create(builderService.getBuildTaskSaleInvoiceDetail(compid, id, buildId));
    }


    @ApiOperation("检验工地端用户绑定的合同是否包含小票")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "buildId", value = "施工方用户id", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "id", value = "小票id", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "compid", value = "企业id", required = true, dataType = "String", paramType = "query"),
    })
    @PostMapping("/checkTaskSaleInvoice")
    public ResultVO checkTaskSaleInvoice(Integer buildId, String compid, Integer id) throws ErpException {
        return ResultVO.create(builderService.checkTaskSaleInvoice(buildId, compid, id));
    }


    @ApiOperation("添加施工单位")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "compid", value = "企业id", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "builderName", value = "施工方用户id", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "builderShortName", value = "小票id", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "address", value = "小票id", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "Corporation", value = "法人", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "fax", value = "传真", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "phone", value = "联系电话", required = true, dataType = "String", paramType = "query"),
    })
    @PostMapping("/addBuilderInfo")
    public ResultVO addBuilderInfo(String compid, String builderName, String builderShortName, String address,
                                   String Corporation, String fax, String phone) {
        builderService.addBuilderInfo(compid, builderName, builderShortName, address, Corporation, fax, phone);
        return ResultVO.create();
    }
}
