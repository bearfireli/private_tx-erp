package com.hntxrj.txerp.mapper;

import com.hntxrj.txerp.vo.DriverWaitLEDVO;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface SystemVarInitMapper {

    DriverWaitLEDVO getDriverWaitLED(String compid);
}
