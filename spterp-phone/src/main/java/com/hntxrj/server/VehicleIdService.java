package com.hntxrj.server;

import com.hntxrj.vo.PageVO;
import com.hntxrj.vo.VehicleIdVO;

/**
 *
 */
public interface VehicleIdService {

    PageVO<VehicleIdVO> getVehicleId(String compid);
}
