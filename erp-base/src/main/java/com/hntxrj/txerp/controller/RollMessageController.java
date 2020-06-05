package com.hntxrj.txerp.controller;

import com.hntxrj.txerp.core.exception.ErpException;
import com.hntxrj.txerp.core.util.SimpleDateFormatUtil;
import com.hntxrj.txerp.core.web.ResultVO;
import com.hntxrj.txerp.service.RollMassageService;
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

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;

@Api(tags = "消息", description = "提供手机erp滚动消息的 api")
@RestController
@RequestMapping(value = "/message")
@Slf4j
public class RollMessageController {

    private final RollMassageService rollMassageService;

    private final SimpleDateFormat sdf = SimpleDateFormatUtil.getDefaultSimpleDataFormat();

    @Autowired
    public RollMessageController(RollMassageService rollMassageService) {
        this.rollMassageService = rollMassageService;
    }

    @ApiOperation("添加滚动信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "compid", value = "企业id", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "content", value = "消息内容", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "beginTime", value = "开始显示时间", dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "endTime", value = "结束显示时间", dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "type", value = "显示类型0:全部企业都显示；1:对应的企业显示",
                    dataType = "int", paramType = "query"),
    })
    @PostMapping("/addRollMessage")
    public ResultVO addRollMessage(String content, Long beginTime, Long endTime, String compid,
                                   @RequestParam(defaultValue = "0") Byte type, HttpServletRequest request) throws ErpException {
        String token = request.getHeader("token");
        rollMassageService.addRollMessage(token, content, compid, type,
                beginTime == null ? null : sdf.format(new Date(beginTime)),
                endTime == null ? null : sdf.format(new Date(endTime)));
        return ResultVO.create();
    }


    @ApiOperation("删除滚动信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", dataType = "int", paramType = "query"),
    })
    @PostMapping("/removeRollMessage")
    public ResultVO removeRollMessage(Integer id) {
        rollMassageService.removeRollMessage(id);
        return ResultVO.create();
    }


    @ApiOperation("修改滚动信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "compid", value = "企业id", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "content", value = "消息内容", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "beginTime", value = "开始显示时间", dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "endTime", value = "结束显示时间", dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "type", value = "显示类型0:全部企业都显示；1:对应的企业显示",
                    dataType = "int", paramType = "query"),

    })
    @PostMapping("/updateRollMessage")
    public ResultVO updateRollMessage(Integer id, String compid, String content, Long beginTime, Long endTime,
                                      @RequestParam(defaultValue = "0") Byte type) {
        rollMassageService.updateRollMessage(id, compid, content,
                beginTime == null ? null : sdf.format(new Date(beginTime)),
                endTime == null ? null : sdf.format(new Date(endTime)), type);
        return ResultVO.create();
    }


    @ApiOperation("查询滚动信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "compid", value = "企业id", dataType = "String", paramType = "query"),
    })
    @PostMapping("/getRollMessage")
    public ResultVO getRollMessage(String compid) {
        return ResultVO.create(rollMassageService.getRollMessage(compid));
    }
}
