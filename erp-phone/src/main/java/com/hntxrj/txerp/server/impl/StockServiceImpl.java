package com.hntxrj.txerp.server.impl;

import com.alibaba.fastjson.JSONArray;
import com.hntxrj.txerp.dao.StockDao;
import com.hntxrj.txerp.mapper.StockMapper;
import com.hntxrj.txerp.mapper.SystemVarInitMapper;
import com.hntxrj.txerp.server.StockService;
import com.hntxrj.txerp.vo.StirIdVO;
import com.hntxrj.txerp.vo.StockSelectVO;
import com.hntxrj.txerp.vo.StockVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
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
    private final SystemVarInitMapper systemVarInitMapper;


    @Autowired
    public StockServiceImpl(StockDao stockDao, StockMapper stockMapper, SystemVarInitMapper systemVarInitMapper) {
        this.stockDao = stockDao;
        this.stockMapper = stockMapper;
        this.systemVarInitMapper = systemVarInitMapper;
    }

    /**
     * 实时库存
     *
     * @param stirId    搅拌楼号
     * @param compid    企业id
     * @param opid      操作人代号
     * @param queryType 材料查询分类 0全部库位 1 粉液料库位
     * @return 实时库存
     */
    @Override
    public JSONArray realStock(String stirId, String compid, String opid, Integer queryType) {
        return stockDao.realStock(stirId, compid, opid, queryType);
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

        // 从系统变量表中查询出用户设置的不显示的库位的库位代号
        String stock = systemVarInitMapper.getNotShowStock(compid);
        List<String> stkCodes = new ArrayList<>();
        if (stock != null) {
            String[] split = stock.split(",");
            stkCodes = Arrays.asList(split);
        }

        // 获取库存数据
        List<StockVO> stockVOS = stockMapper.getStockByStirId(compid, stirId, stkCodes);
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

    @Override
    public List<StockSelectVO> getAllStockList(String compid, Integer stirId) {
        return stockMapper.getAllStockList(compid, stirId);
    }

    @Override
    public List<String> getSelectStock(String compid) {
        String selectedStock = systemVarInitMapper.getNotShowStock(compid);
        List<String> stkCodeList = new ArrayList<>();
        if (selectedStock != null) {
            String[] stkCodes = selectedStock.split(",");
            stkCodeList = Arrays.asList(stkCodes);
        }
        return stkCodeList;
    }

    @Override
    public void saveStockCodes(String compid, String stkCodes) {
        String selectedStock = systemVarInitMapper.getNotShowStock(compid);
        if (selectedStock == null) {
            Integer maxId = systemVarInitMapper.getMaxId(compid);
            systemVarInitMapper.saveStock(compid, stkCodes, maxId + 1);
        } else {
            systemVarInitMapper.updateStock(compid, stkCodes);
        }
    }
}
