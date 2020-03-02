package com.hntxrj.txerp.server.impl;

import com.github.pagehelper.PageInfo;
import com.hntxrj.txerp.mapper.VehicleIdMapper;
import com.hntxrj.txerp.server.VehicleIdService;
import com.hntxrj.txerp.vo.PageVO;
import com.hntxrj.txerp.vo.VehicleIdVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

    /**
     * @author qyb
     * @ClassName VehicleIdServiceImpl
     * @Date 19-6-18 &#x4e0b;&#x5348;5:59
     * @Version 1.0
     **/
    @Service
    public class VehicleIdServiceImpl implements VehicleIdService {

        private final VehicleIdMapper vehicleIdMapper;

    @Autowired
    public VehicleIdServiceImpl(VehicleIdMapper vehicleIdMapper) {
        this.vehicleIdMapper = vehicleIdMapper;
    }


    @Override
    public PageVO<VehicleIdVO> getVehicleId(String compid,String vehicleId) {
        PageVO<VehicleIdVO> pageVO = new PageVO<>();
        List<VehicleIdVO> vehicleIds = vehicleIdMapper.getVehicleId(compid,vehicleId);
        PageInfo<VehicleIdVO> pageInfo = new PageInfo<>(vehicleIds);
        pageVO.format(pageInfo);
        return pageVO;
    }
}
