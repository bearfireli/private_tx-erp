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
 * @Description TODO
 * @Date 19-6-18 下午5:59
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
    public PageVO<VehicleIdVO> getVehicleId(String compid) {
        PageVO<VehicleIdVO> pageVO = new PageVO<>();
        List<VehicleIdVO> vehicleId = vehicleIdMapper.getVehicleId(compid);
        PageInfo<VehicleIdVO> pageInfo = new PageInfo<>(vehicleId);
        pageVO.format(pageInfo);
        return pageVO;
    }
}
