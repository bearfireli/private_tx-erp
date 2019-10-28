package com.hntxrj.txerp.server.impl;

import com.alibaba.fastjson.JSONArray;
import com.hntxrj.txerp.dao.StockDao;
import com.hntxrj.txerp.mapper.StockMapper;
import com.hntxrj.txerp.server.StockService;
import com.hntxrj.txerp.vo.StirIdVO;
import com.hntxrj.txerp.vo.StockVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 功能:
 *
 * @Auther 李帅
 * @Data 2017-08-17.上午 10:23
 */
@Service
@Scope("prototype")
public class StockServiceImpl implements StockService {

    private final StockDao stockDao;
    private final StockMapper stockMapper;

    @Autowired
    public StockServiceImpl(StockDao stockDao, StockMapper stockMapper) {
        this.stockDao = stockDao;
        this.stockMapper = stockMapper;
    }

    /**
     * 实时库存统计
     *
     * @param stirId 搅拌楼Id号
     * @return
     */
    @Override
    public JSONArray realStock(String stirId, String compid, String opid) {
        return stockDao.realStock(stirId, compid, opid);
    }

    /**
     * 获取库位信息
     *
     * @param compid 企业ID
     * @return {@link JSONArray}
     */
    @Override
    public JSONArray getStock(String compid) {
        return stockDao.getStock(compid);
    }

    @Override
    public List<StockVO> getStock(String compid, Integer stirId) {
        // 获取库存数据
        List<StockVO> stockVOS = stockMapper.getStockByStirId(compid, stirId);
        // 替换公共罐数据
        List<StockVO> publicStockVOs = stockMapper.getPublicStockByStirId(compid, stirId);

        List<StockVO> resultVOS = new ArrayList<>();
        boolean isPublic;
        for (StockVO item : stockVOS) {
            isPublic = false;
            for (StockVO publicItem : publicStockVOs) {
                if (item.getOderBy().equals(publicItem.getOderBy())) {
                    resultVOS.add(publicItem);
                    isPublic = true;
                    break;
                }
            }
            if (!isPublic) {
                resultVOS.add(item);
            }
        }

        return resultVOS;
    }

    @Override
    public List<StirIdVO> getStirIds(String compid) {
        return stockMapper.getStirIds(compid);
    }
}
