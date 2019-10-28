package com.hntxrj.txerp.server;


import com.alibaba.fastjson.JSONArray;
import com.hntxrj.txerp.vo.StirIdVO;
import com.hntxrj.txerp.vo.StockVO;

import java.util.List;

/**
 * 功能: 统计Service层
 *
 * @Auther 李帅
 * @Data 2017-08-17.上午 10:22
 */
public interface StockService {
    /**
     * 实时库存统计
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

    List<StockVO> getStock(String compid, Integer stirId);

    /**
     * 获取信号代号
     *
     * @param compid 企业id
     * @return
     */
    List<StirIdVO> getStirIds(String compid);


}
