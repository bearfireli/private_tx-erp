package com.hntxrj.txerp.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.Date;
import java.util.List;

@Mapper
public interface CompMapper {

    /**
     * 删除企业
     *
     * @param compid     企业id
     * @param updateTime 修改时间
     */
    void deleteComp(String compid, Date updateTime);


    /**
     * 更改企业
     *
     * @param compid        企业id
     * @param compName      企业名称
     * @param compShortName 企业简称
     * @param updateTime    修改时间
     */
    void updateComp(String compid, String compName, String compShortName, Date updateTime);

    //获取所有的车辆集合
    List<String> getAllCompId();

}
