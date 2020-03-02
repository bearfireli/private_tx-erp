package com.hntxrj.txerp.server;

import com.hntxrj.txerp.vo.PageVO;
import com.hntxrj.txerp.vo.VehicleIdVO;

/**
 *
 */
public interface VehicleIdService {

    /**
     * 查询车号
     *
     * @param compid    　企业id
     * @param vehicleId   车辆id
     */
    PageVO<VehicleIdVO> getVehicleId(String compid,String vehicleId);
}
