package com.hntxrj.txerp.mapper;

import com.hntxrj.txerp.vo.VehicleIdVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface VehicleIdMapper {

    /**
     * 查询车号集合
     *
     * @param compid    　企业id
     * @param vehicleId   车辆id
     */
    List<VehicleIdVO> getVehicleId(String compid,String vehicleId);
}
