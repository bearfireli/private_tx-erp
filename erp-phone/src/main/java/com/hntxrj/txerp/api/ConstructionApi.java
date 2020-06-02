package com.hntxrj.txerp.api;

import com.hntxrj.txerp.core.exception.ErpException;
import com.hntxrj.txerp.core.util.SimpleDateFormatUtil;
import com.hntxrj.txerp.server.ConstructionService;
import com.hntxrj.txerp.vo.ResultVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author qyb
 * ConstructionApi
 * 施工单位
 * 19-7-25 下午4:06
 **/
@Api(tags = "邀请码")
@RestController
@RequestMapping("/api/construction")
public class ConstructionApi {

    private final ConstructionService constructionService;

    public ConstructionApi(ConstructionService constructionService) {
        this.constructionService = constructionService;
    }

    @ApiOperation("生成施工方邀请码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "compid", value = "企业id", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "opid", value = "操作员代号", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "contractDetailCodes", value = "子合同号列表", required = true,
                    dataType = "String", paramType = "query")
    })
    @PostMapping("/getInvitationCode")
    public ResultVO getInvitationCode(String compid, Integer opid, String contractDetailCodes) throws ErpException {
        return ResultVO.create(constructionService.getInvitationCode(compid, opid, contractDetailCodes));
    }

    @ApiOperation("施工方邀请码列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "compid", value = "企业id", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "buildCode", value = "施工方代码", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "useStatus", value = "状态 1:已使用; 0:未使用", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "createUser", value = "创建人", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "beginTime", value = "开始时间", dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "endTime", value = "结束时间", dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "page", value = "页码", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页数量", required = true, dataType = "int", paramType = "query")
    })
    @PostMapping("/getInvitationList")
    public ResultVO getInvitationList(String compid, String buildCode, String useStatus, String createUser,
                                      Long beginTime, Long endTime,
                                      @RequestParam(defaultValue = "1") Integer page,
                                      @RequestParam(defaultValue = "10") Integer pageSize) {
        SimpleDateFormat sdf = SimpleDateFormatUtil.getSimpleDataFormat("yyyy-MM-dd HH:mm:ss");
        return ResultVO.create(constructionService.getInvitationList(compid, buildCode,
                useStatus, createUser, beginTime == null ? null : sdf.format(new Date(beginTime)),
                endTime == null ? null : sdf.format(new Date(endTime)), page, pageSize));
    }

    @ApiOperation("作废施工方邀请码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "contractUID", value = "主合同号", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "contractDetailCode", value = "子合同号", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "buildInvitationCode", value = "邀请码", required = true, dataType = "String", paramType = "query")
    })
    @PostMapping("/invalidInvitationCode")
    public ResultVO invalidInvitationCode(String contractUID, String contractDetailCode, String buildInvitationCode) throws ErpException {
        constructionService.invalidInvitationCode(contractUID, contractDetailCode, buildInvitationCode);
        return ResultVO.create();
    }

    @ApiOperation("绑定施工方邀请码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "buildId", value = "施工方用户id", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "buildInvitationCode", value = "邀请码", required = true, dataType = "String", paramType = "query")
    })
    @PostMapping("/saveInvitation")
    public ResultVO saveInvitation(String buildId, String buildInvitationCode) throws ErpException, SQLException {
        constructionService.saveInvitation(buildId, buildInvitationCode);
        return ResultVO.create();
    }


    @ApiOperation("查询绑定合同")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "buildId", value = "施工方用户id", required = true, dataType = "String", paramType = "query")
    })
    @PostMapping("/checkBind")
    public ResultVO checkBind(String buildId) {
        return ResultVO.create(constructionService.checkBind(buildId));
    }

    @ApiOperation("删除合同")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "buildId", value = "施工方用户id", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "contractUid", value = "主合同号", required = true, dataType = "String", paramType = "query")
    })
    @PostMapping("deleteBuildId")
    public ResultVO deleteBuildId(String buildId, String contractUid) throws ErpException {
        constructionService.deleteBuildId(buildId, contractUid);
        return ResultVO.create();
    }

    @ApiOperation("解除用户绑定的合同")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "buildId", value = "施工方用户id", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "contractUID", value = "主合同号", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "contractDetailCode", value = "子合同号", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "buildInvitationCode", value = "施工方邀请码", required = true,
                    dataType = "String", paramType = "query")
    })
    @PostMapping("removeBind")
    public ResultVO removeBind(String buildId, String contractUID, String contractDetailCode,
                               String buildInvitationCode) {
        constructionService.removeBind(buildId, contractUID, contractDetailCode, buildInvitationCode);
        return ResultVO.create();
    }

}
