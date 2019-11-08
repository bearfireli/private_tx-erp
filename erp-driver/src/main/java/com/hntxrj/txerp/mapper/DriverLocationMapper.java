package com.hntxrj.txerp.mapper;

import com.hntxrj.txerp.entity.DriverLocation;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DriverLocationMapper {

    List<DriverLocation> getLastDriverLocationByCompid(String compid);
}
