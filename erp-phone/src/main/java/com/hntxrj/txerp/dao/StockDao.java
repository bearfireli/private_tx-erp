package com.hntxrj.txerp.dao;


import com.alibaba.fastjson.JSONArray;

/**
 * 功能:  库存统计Dao
 *
 * @Auther 李帅
 * @Data 2017-08-17.上午 10:16
 */
public interface StockDao {


    /**
     * 统计
     *
     * @param stirId 搅拌楼Id号
     * @return
     */
    JSONArray realStock(String stirId, String compid, String opid);


    /**
     * 获取库位信息
     *
     * @param compid 企业ID
     * @return {@link JSONArray}
     */
    JSONArray getStock(String compid);

}
