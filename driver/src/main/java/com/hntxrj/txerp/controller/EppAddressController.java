package com.hntxrj.txerp.controller;


import com.hntxrj.txerp.core.web.ResultVO;
import com.hntxrj.txerp.services.EppAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/driver")
public class EppAddressController {


    private final EppAddressService eppAddressService;

    @Autowired
    public EppAddressController(EppAddressService eppAddressService) {
        this.eppAddressService = eppAddressService;
    }

    /**
     * 获取工程地址（任务单送货）地址
     *
     * @param eppCode 工程代号
     * @param compid  企业代号
     * @return 工程地址
     */
    @RequestMapping("/getEppAddress")
    public ResultVO getEppAddress(String eppCode, String compid) {
        return ResultVO.create(eppAddressService.getAddressByEpp(eppCode, compid));
    }

    /**
     * 司机定位接口--30秒一次
     *
     * @param id       小票id
     * @param compid   企业id
     * @param location 司机位置
     * @param token    司机token
     * @return 成功信息
     */
    @PostMapping("/driverLocation")
    public ResultVO driverLocation(Integer id, String compid, String location, String token) {
        eppAddressService.saveDriverLocation(id, compid, location, token);
        return ResultVO.create();
    }
}
