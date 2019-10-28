package com.hntxrj.txerp.mapper;

import com.hntxrj.txerp.entity.base.Enterprise;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EnterpriseMapper {
    int updateId(Enterprise enterpriseOld, Integer id);
}
