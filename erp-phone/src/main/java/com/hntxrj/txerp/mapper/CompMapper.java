package com.hntxrj.txerp.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CompMapper {
    void deleteComp(String compid);

    void updateComp(String compid, String compName, String compShortName);
}
