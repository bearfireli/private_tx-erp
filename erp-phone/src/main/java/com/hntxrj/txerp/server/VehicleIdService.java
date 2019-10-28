package com.hntxrj.txerp.server;

import com.hntxrj.txerp.vo.PageVO;
import com.hntxrj.txerp.vo.VehicleIdVO;

/**
 *
 */
public interface VehicleIdService {

    PageVO<VehicleIdVO> getVehicleId(String compid);
}
