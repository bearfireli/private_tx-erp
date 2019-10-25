package com.hntxrj.api;

import com.hntxrj.server.VehicleIdService;
import com.hntxrj.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author qyb
 * 19-6-18 下午5:55
 **/
@RestController
@RequestMapping("/api/vehicleId")
public class VehicleIdApi {

    private final VehicleIdService vehicleIdService;

    @Autowired
    public VehicleIdApi(VehicleIdService vehicleIdService) {
        this.vehicleIdService = vehicleIdService;
    }

    /**
     * 查询车号
     *
     * @param compid 　企业ｉｄ
     */
    @PostMapping("/getVehicleId")
    public ResultVO getVehicleId(String compid) {
        return ResultVO.create(vehicleIdService.getVehicleId(compid));
    }
}
