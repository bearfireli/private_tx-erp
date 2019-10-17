package com.hntxrj.driver.mapper;

import com.hntxrj.driver.entity.EppAddress;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EppAddressMapper {

    List<EppAddress> getEppAddress(String compid, String eppCode);

    int getCountByEppCode(String compid, String eppCode);

}
