package com.hntxrj.txerp.mapper;

import com.hntxrj.txerp.vo.DriverWaitLEDVO;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface SystemVarInitMapper {

    /**
     * 查询系统变量表，查询司机App中等待生产的车辆是否显示以及显示几辆
     */
    DriverWaitLEDVO getDriverWaitLED(String compid);


    /**
     * 查询系统变量表，查询手机erp首页销售方量的查询方式
     */
    Integer getQuantityQueryType(String compid);

}
