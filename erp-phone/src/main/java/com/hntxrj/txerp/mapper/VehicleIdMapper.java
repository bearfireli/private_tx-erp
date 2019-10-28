package com.hntxrj.txerp.mapper;

import com.hntxrj.txerp.vo.VehicleIdVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface VehicleIdMapper {

    List<VehicleIdVO> getVehicleId(String compid);
}
