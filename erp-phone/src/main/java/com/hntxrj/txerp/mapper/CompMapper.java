package com.hntxrj.txerp.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.Date;

@Mapper
public interface CompMapper {

    /**
     * 删除企业
     *
     * @param compid 企业id
     * */
    void deleteComp(String compid, Date updTime);


    /**
     * 更改企业
     *
     * @param compid         企业id
     * @param compName       企业名称
     * @param compShortName  企业简称
     * */
    void updateComp(String compid, String compName, String compShortName,Date updTime);
}
