package com.hntxrj.txerp.api;

import com.hntxrj.txerp.core.exception.ErpException;
import com.hntxrj.txerp.server.EppService;
import com.hntxrj.txerp.util.AuthUtilKt;
import com.hntxrj.txerp.vo.EppDropDownVO;
import com.hntxrj.txerp.vo.EppInfoVO;
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
 * @author qyb
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
    public ResultVO<PageVO<EppDropDownVO>> getDropDown(String eppName, String compid,
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
    public ResultVO<PageVO<EppDropDownVO>> getBuildDropDown(String eppName, Integer buildId,
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
    public ResultVO<Object> addEppName(String compid, String eppName, String shortName, String address, String linkMan,
                               String phone, String remarks) {
        eppService.addEppInfo(compid, eppName, shortName, address, linkMan, phone, remarks);
        return ResultVO.create();
    }

    @ApiOperation("分页查询工程名称")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "eppCode", value = "工程代号", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "eppName", value = "工程名称", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "recStatus", value = "是否启用", required = true, dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "page", value = "当前页", required = true, dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页条数", required = true, dataType = "Integer", paramType = "query")
    })
    @PostMapping("/getContractEppList")
    public ResultVO<PageVO<EppInfoVO>> getContractEppList(String eppCode, String eppName, Integer recStatus, @RequestParam(defaultValue = "1") Integer page,
                                       @RequestParam(defaultValue = "10") Integer pageSize, HttpServletRequest request) throws ErpException {

        return ResultVO.create(eppService.getEppPageVO(eppCode, eppName, recStatus, AuthUtilKt.getCompid(request), page, pageSize));
    }

    @ApiOperation("根据工程代号查询工程名称")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "eppCode", value = "工程代号", required = true, dataType = "String", paramType = "query")
    })
    @PostMapping("/getEppInfo")
    public ResultVO<EppInfoVO> getEppInfo(String eppCode, HttpServletRequest request) throws ErpException {
        return ResultVO.create(eppService.getEppInfoVO(eppCode, AuthUtilKt.getCompid(request)));
    }

    @ApiOperation("保存或修改工程名称")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "eppCode", value = "工程名称", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "eppName", value = "工程名称", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "shortName", value = "工程简称", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "address", value = "工程地址", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "linkMan", value = "工程联系人", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "linkTel", value = "联系电话", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "remarks", value = "备注", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "accountingAccountCode", value = "科目代码", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "recStatus", value = "启用状态", required = true, dataType = "Integer", paramType = "query"),
    })
    @PostMapping("/saveOrUpdateEppInfo")
    public ResultVO<String> saveOrUpdateEppInfo(String eppCode, String eppName, String shortName, String address, String linkMan,
                                String linkTel, String remarks, String accountingAccountCode, Integer recStatus, HttpServletRequest request) throws ErpException {
        EppInfoVO eppInfoVO =
                new EppInfoVO(AuthUtilKt.getCompid(request), eppCode, eppName, shortName, address, linkMan, linkTel, remarks, accountingAccountCode, recStatus, null);
        return ResultVO.create(eppService.saveOrUpdateEppInfo(eppInfoVO));
    }

    @ApiOperation("修改工程名称的启用状态")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "eppCode", value = "工程代号", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "recStatus", value = "启用状态", required = true, dataType = "Integer", paramType = "query"),
    })
    @PostMapping("/changeRecStatus")
    public ResultVO<Object> changeRecStatus(String eppCode, Integer recStatus, HttpServletRequest request) throws ErpException {
        eppService.changeEppRecStatus(eppCode, AuthUtilKt.getCompid(request), recStatus);
        return ResultVO.create();
    }
}
