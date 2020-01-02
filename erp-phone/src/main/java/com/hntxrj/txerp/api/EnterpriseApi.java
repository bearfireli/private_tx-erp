package com.hntxrj.txerp.api;

import com.hntxrj.txerp.server.BuilderService;
import com.hntxrj.txerp.server.EnterpriseService;
import com.hntxrj.txerp.vo.EnterpriseVO;
import com.hntxrj.txerp.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/enterprise")
@Slf4j
public class EnterpriseApi {
    private final EnterpriseService enterpriseService;
    private ResultVO resultVO;

    @Autowired
    public EnterpriseApi(EnterpriseService enterpriseService) {
        this.enterpriseService = enterpriseService;
    }

    /**
     * 设置搅拌站地址坐标
     *
     * @param compid   企业
     * @param address  地址坐标
     * @param opid     操作员代号
     *
     * */
    @RequestMapping("/setEnterpriseAddress")
    public ResultVO setEnterpriseAddress(String compid,String address,String opid) {
        enterpriseService.setEnterpriseAddress(compid, address, opid);
        return resultVO.create();
    }

    /**
     * 获取搅拌站地址坐标
     *
     * @param compid   企业
     *
     * */
    @RequestMapping("/getEnterpriseAddress")
    public ResultVO getEnterpriseAddress(String compid) {
        EnterpriseVO enterpriseVO= enterpriseService.getEnterpriseAddress(compid);

        return ResultVO.create(enterpriseVO);
    }


}
