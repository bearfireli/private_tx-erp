package com.hntxrj.dao;


import com.alibaba.fastjson.JSONArray;

/**
 * 功能: 统计信息Dao层
 *
 * @Auther 李帅
 * @Data 2017-08-28.下午 8:04
 */
public interface TotalAllDao {

    /**
     *  统计查询
     * @param mark 标记
     * @return
     */
    JSONArray totalAll(Integer mark, String compid);

}
