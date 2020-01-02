package com.hntxrj.txerp.mapper;

import com.hntxrj.txerp.vo.EnterpriseVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.Date;

@Mapper
public interface EnterpriseMapper {



    EnterpriseVO selectEnterpriseAddress(String compid);

    void updateEnterpriseAddress(String compid, String address, String opid, Date updateTime);
}
