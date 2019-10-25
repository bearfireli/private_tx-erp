package com.hntxrj.mapper;

import com.hntxrj.vo.VehicleIdVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface VehicleIdMapper {

    List<VehicleIdVO> getVehicleId(String compid);
}
