package com.hntxrj.txerp.api;

import com.hntxrj.txerp.server.VehicleIdService;
import com.hntxrj.txerp.vo.ResultVO;
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
     * @param compid    　企业id
     * @param vehicleId 车辆id
     */
    @PostMapping("/getVehicleId")
    public ResultVO getVehicleId(String compid, String vehicleId) {
        return ResultVO.create(vehicleIdService.getVehicleId(compid, vehicleId));
    }
}
