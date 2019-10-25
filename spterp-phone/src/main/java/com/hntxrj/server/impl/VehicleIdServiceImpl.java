package com.hntxrj.server.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hntxrj.dao.VehicleDao;
import com.hntxrj.mapper.VehicleIdMapper;
import com.hntxrj.mapper.VehicleWorkloadMapper;
import com.hntxrj.server.VehicleIdService;
import com.hntxrj.vo.PageVO;
import com.hntxrj.vo.VehicleIdVO;
import com.hntxrj.vo.VehicleWorkloadSummaryVO;
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
