package com.hntxrj.txerp.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SystemSqlMapper {

    String getSqlByName(String sqlName);

}
