package com.hntxrj.txerp.server.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hntxrj.txerp.mapper.VehicleReturnMapper;
import com.hntxrj.txerp.server.VehicleReturnService;
import com.hntxrj.txerp.vo.PageVO;
import com.hntxrj.txerp.vo.VehicleReturnVO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VehicleReturnServiceImpl implements VehicleReturnService {

    private final VehicleReturnMapper vehicleReturnMapper;

    public VehicleReturnServiceImpl(VehicleReturnMapper vehicleReturnMapper) {
        this.vehicleReturnMapper = vehicleReturnMapper;
    }


    @Override
    public PageVO<VehicleReturnVO> vehicleReturnList(String compid, String vehicleId, String inEppName, String outEppName,
                                                     String remarks, String beginTime, String endTime, int page, int pageSize) {
        PageVO<VehicleReturnVO> pageVO = new PageVO<>();
        PageHelper.startPage(page, pageSize);
        List<VehicleReturnVO> vehicleReturnVOS = vehicleReturnMapper.vehicleReturnList(compid, vehicleId, inEppName,
                outEppName, remarks, beginTime, endTime);

        PageInfo<VehicleReturnVO> pageInfo = new PageInfo<>(vehicleReturnVOS);
        pageVO.format(pageInfo);
        return pageVO;
    }

    @Override
    public VehicleReturnVO vehicleReturnDetail(String compid, String vehicleId, String taskIdOld, String sendTime) {
        return vehicleReturnMapper.vehicleReturnDetail(compid, vehicleId, taskIdOld, sendTime);
    }
}
