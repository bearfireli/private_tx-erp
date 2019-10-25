package com.hntxrj.txerp.mapper;

import com.hntxrj.txerp.entity.EppAddress;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EppAddressMapper {

    List<EppAddress> getEppAddress(String compid, String eppCode);

    int getCountByEppCode(String compid, String eppCode);

}
