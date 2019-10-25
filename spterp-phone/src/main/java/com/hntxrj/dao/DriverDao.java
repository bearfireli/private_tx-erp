package com.hntxrj.dao;

import com.alibaba.fastjson.JSONArray;

/**
 * 功能及介绍： 司机模块 数据库操作
 * <p>
 * ========================
 * Created with IntelliJ IDEA.
 * User： 李 帅
 * Date：2018/5/29
 * Time：下午5:38
 * ========================
 *
 * @author 李帅
 */
public interface DriverDao {

    /**
     *  司机排班数据获取
     * @param compId 企业id
     * @param stirId 线号
     * @param vehicleStatus 车辆状态
     * @param vehicleClass 班次
     * @return {@link JSONArray}
     */
    JSONArray getDriverScheduling(String compId,String stirId, String vehicleStatus,
                                  String vehicleClass);

}
