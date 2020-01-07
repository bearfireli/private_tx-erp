package com.hntxrj.txerp.api;

import com.hntxrj.txerp.server.EppService;
import com.hntxrj.txerp.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 工程api
 */
@RestController
@RequestMapping("/api/epp")
@Slf4j
public class EppApi {

    private final EppService eppService;
    private ResultVO resultVO;

    @Autowired
    public EppApi(EppService eppService) {
        resultVO = new ResultVO();
        resultVO.setMsg("ok");
        resultVO.setCode(0);
        resultVO.setData(null);
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
        resultVO.setData(eppService.getDropDown(eppName, compid, page, pageSize));
        return resultVO;
    }

    /**
     * 获取工地端App工程名称下拉
     *
     * @param eppName  工程名称模糊查询
     * @param buildId   企业id
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
        resultVO.setData(eppService.getBuildDropDown(eppName, buildId, page, pageSize));
        return resultVO;
    }


}
