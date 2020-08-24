package com.hntxrj.txerp.api;

import com.hntxrj.txerp.core.exception.ErpException;
import com.hntxrj.txerp.server.EppService;
import com.hntxrj.txerp.util.AuthUtilKt;
import com.hntxrj.txerp.vo.PageVO;
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

import javax.servlet.http.HttpServletRequest;

/**
 * 工程api
 */
@Api(tags = {"工程接口"}, description = "工程相关接口")
@RestController
@RequestMapping("/api/epp")
@Slf4j
public class EppApi {

    private final EppService eppService;

    @Autowired
    public EppApi(EppService eppService) {
        this.eppService = eppService;
    }

    /**
     * 获取工程名称下拉
     *
     * @param eppName  工程名称模糊查询
     * @param compid   企业id
     * @param page     页码
     * @param pageSize 每页数量
     * @return 带分页工程下拉列表
     */
    @PostMapping("/getDropDown")
    public ResultVO getDropDown(String eppName, String compid,
                                @RequestParam(defaultValue = "1") Integer page,
                                @RequestParam(defaultValue = "10") Integer pageSize) {
        log.info("【获取工程名称下拉】eppName={}，compid={}, page={}, pageSize={}",
                eppName, compid, page, pageSize);
        return ResultVO.create(eppService.getDropDown(eppName, compid, page, pageSize));
    }

    /**
     * 获取工地端App工程名称下拉
     *
     * @param eppName  工程名称模糊查询
     * @param buildId  企业id
     * @param page     页码
     * @param pageSize 每页数量
     * @return 带分页工程下拉列表
     */
    @PostMapping("/getBuildDropDown")
    public ResultVO getBuildDropDown(String eppName, Integer buildId,
                                     @RequestParam(defaultValue = "1") Integer page,
                                     @RequestParam(defaultValue = "10") Integer pageSize) {
        log.info("【获取工程名称下拉】eppName={}，buildId={}, page={}, pageSize={}",
                eppName, buildId, page, pageSize);
        return ResultVO.create(eppService.getBuildDropDown(eppName, buildId, page, pageSize));
    }

    /**
     * 添加工程名称
     *
     * @param compid    企业代号
     * @param eppName   工程名称模糊查询
     * @param shortName 企业id
     * @param address   页码
     * @param linkMan   每页数量
     * @param phone     每页数量
     * @param remarks   每页数量
     */
    @PostMapping("/addEppInfo")
    public ResultVO addEppName(String compid, String eppName, String shortName, String address, String linkMan,
                               String phone, String remarks) {
        eppService.addEppInfo(compid, eppName, shortName, address, linkMan, phone, remarks);
        return ResultVO.create();
    }

    @ApiOperation("获取主合同和子合同集合")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "eppName", value = "工程名称", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "eppCode", value = "工程名称", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "page", value = "当前页", required = true, dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页条数", required = true, dataType = "Integer", paramType = "query")
    })
    @PostMapping("/getContractEppList")
    public ResultVO getContractEppList(String eppName, @RequestParam(defaultValue = "1") Integer page,
                                       @RequestParam(defaultValue = "10") Integer pageSize, HttpServletRequest request) throws ErpException {

        return ResultVO.create(eppService.getEppList(eppName, AuthUtilKt.getCompid(request), page, pageSize));
    }
}
