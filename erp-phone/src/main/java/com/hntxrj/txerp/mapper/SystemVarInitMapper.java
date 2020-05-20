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

    /**
     * 查询系统变量表，查询用户设置的不展示的库位
     */
    String getNotShowStock(String compid);

    /**
     * 保存用户设置的手机erp实时库存中不显示的库位
     */
    void saveStock(String compid, String stkCodes,Integer maxId);

    /**
     * 修改用户设置的手机erp实时库存中不显示的库位
     */
    void updateStock(String compid, String stkCodes);

    // 获取系统变量表中该企业最大的id值
    Integer getMaxId(String compid);
}
