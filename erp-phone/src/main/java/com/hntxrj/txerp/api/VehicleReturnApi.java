package com.hntxrj.txerp.api;

import com.hntxrj.txerp.core.util.SimpleDateFormatUtil;
import com.hntxrj.txerp.server.VehicleReturnService;
import com.hntxrj.txerp.vo.ResultVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;

@Api(tags = "退转剩接口", description = "提供退转剩功能相关的 api")
@RestController
@RequestMapping("/api/vehicleReturn")
public class VehicleReturnApi {

    private final VehicleReturnService vehicleReturnService;
    private final SimpleDateFormat sdf = SimpleDateFormatUtil.getDefaultSimpleDataFormat();

    public VehicleReturnApi(VehicleReturnService vehicleReturnService) {
        this.vehicleReturnService = vehicleReturnService;
    }


    @ApiOperation("退转剩列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "compid", value = "企业id", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "vehicleId", value = "车号", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "inEppName", value = "转入工程名称", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "outEppName", value = "转出工程名称", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "remarks", value = "备注", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "beginTime", value = "开始时间", required = true, dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "entTime", value = "结束时间", required = true, dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "page", value = "当前页码", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页条数", dataType = "int", paramType = "query"),
    })
    @PostMapping("/vehicleReturnList")
    public ResultVO vehicleReturnList(String compid, String vehicleId, String inEppName, String outEppName, String remarks,
                                      Long beginTime, Long endTime, @RequestParam(defaultValue = "1") int page,
                                      @RequestParam(defaultValue = "10") int pageSize) {
        return ResultVO.create(vehicleReturnService.vehicleReturnList(compid, vehicleId, inEppName, outEppName, remarks,
                beginTime == null ? null : sdf.format(beginTime),
                endTime == null ? null : sdf.format(endTime), page, pageSize));
    }


    @ApiOperation("退转剩列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "compid", value = "企业id", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "vehicleId", value = "车号", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "taskIdOld", value = "剩转工地任务单号", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "sendTime", value = "派车时间", required = true, dataType = "String", paramType = "query"),
    })
    @PostMapping("/vehicleReturnDetail")
    public ResultVO vehicleReturnDetail(String compid, String vehicleId, String taskIdOld, String sendTime) {
        return ResultVO.create(vehicleReturnService.vehicleReturnDetail(compid, vehicleId, taskIdOld, sendTime));
    }
}
