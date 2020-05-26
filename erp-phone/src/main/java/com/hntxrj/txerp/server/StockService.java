package com.hntxrj.txerp.server;


import com.alibaba.fastjson.JSONArray;
import com.hntxrj.txerp.vo.StirIdVO;
import com.hntxrj.txerp.vo.StockSelectVO;
import com.hntxrj.txerp.vo.StockVO;

import java.util.List;
import java.util.Map;

/**
 * 功能: 统计Service层
 *
 * @Auther 李帅
 * @Data 2017-08-17.上午 10:22
 */
public interface StockService {
    /**
     * 实时库存
     *
     * @param stirId    搅拌楼号
     * @param compid    企业id
     * @param opid      操作人代号
     * @param queryType 材料查询分类 0全部库位 1 粉液料库位
     * @return 实时库存
     */
    JSONArray realStock(String stirId, String compid, String opid, Integer queryType);


    /**
     * 获取库位信息
     *
     * @param compid 企业ID
     * @return {@link JSONArray}
     */
    JSONArray getStock(String compid);

    /**
     * 实时库存
     */
    List<StockVO> getStock(String compid, Integer stirId);

    /**
     * 获取信号代号
     *
     * @param compid 企业id
     * @return
     */
    List<StirIdVO> getStirIds(String compid);


    /**
     * 根据线号获取所有的库位信息
     */
    List<StockSelectVO> getAllStockList(String compid, Integer stirId);

    /**
     * 获取用选中的库位代号列表
     */
    List<String> getSelectStock(String compid);

    /**
     * 保存用户选中的不显示的库位代号
     *
     * @param compid   企业id
     * @param stkCodes 用户选中的不展示的库位代号
     */
    void saveStockCodes(String compid, String stkCodes);

    /**
     * 获取用户设置的骨料是否显示的信息
     *
     * @param compid 企业id
     */
    Map<String, Integer> getStockAggregateShow(String compid);

    /**
     * 设置实时库存骨料是否显示
     *
     * @param compid          企业id
     * @param aggregateIsShow 骨料是否显示
     */
    void setStockAggregateShow(String compid, Integer aggregateIsShow);
}
