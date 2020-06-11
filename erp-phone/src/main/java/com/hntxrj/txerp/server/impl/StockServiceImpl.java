package com.hntxrj.txerp.server.impl;

import com.alibaba.fastjson.JSONArray;
import com.hntxrj.SyncPlugin;
import com.hntxrj.txerp.dao.StockDao;
import com.hntxrj.txerp.mapper.StockMapper;
import com.hntxrj.txerp.mapper.SystemVarInitMapper;
import com.hntxrj.txerp.server.StockService;
import com.hntxrj.txerp.vo.PublicStockVO;
import com.hntxrj.txerp.vo.StirIdVO;
import com.hntxrj.txerp.vo.StockSelectVO;
import com.hntxrj.txerp.vo.StockVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.*;

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
    private final SyncPlugin syncPlugin;


    @Autowired
    public StockServiceImpl(StockDao stockDao, StockMapper stockMapper, SystemVarInitMapper systemVarInitMapper, SyncPlugin syncPlugin) {
        this.stockDao = stockDao;
        this.stockMapper = stockMapper;
        this.systemVarInitMapper = systemVarInitMapper;
        this.syncPlugin = syncPlugin;
    }

    /**
     * 实时库存
     *
     * @param stirId 搅拌楼号
     * @param compid 企业id
     * @param opid   操作人代号
     * @return 实时库存
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

        //从系统变量表中查询是否显示骨料
        Integer stockAggregateIsShow = systemVarInitMapper.getStockAggregateShow(compid);

        // 获取库存数据
        List<StockVO> stockVOS = stockMapper.getStockByStirId(compid, stirId, stockAggregateIsShow);
        // 获取所有公共罐的数据
        List<PublicStockVO> publicStockVOs = stockMapper.getPublicStockByStirId(compid, stirId);


        //循环查询,查询是否时公共罐以及公共罐是否显示
        List<StockVO> resultVOS = new ArrayList<>();
        boolean publicShow;
        for (StockVO stockVO : stockVOS) {
            publicShow = false;
            for (PublicStockVO publicStockVO : publicStockVOs) {
                if (stockVO.getOderBy().equals(publicStockVO.getErpStockCode())
                        && publicStockVO.getPublicUse() == 1) {
                    publicShow = true;
                    break;
                }
            }
            if (!publicShow) {
                resultVOS.add(stockVO);
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

    @Override
    public Map<String, Integer> getStockAggregateShow(String compid) {
        Map<String, Integer> map = new HashMap<>();
        map.put("aggregateIsShow", 0);
        Integer aggregateIsShow = systemVarInitMapper.getStockAggregateShow(compid);
        if (aggregateIsShow != null) {
            map.put("aggregateIsShow", aggregateIsShow);
        }
        return map;
    }

    @Override
    public void setStockAggregateShow(String compid, Integer aggregateIsShow) {
        Integer varValue = systemVarInitMapper.getStockAggregateShow(compid);
        if (varValue == null) {
            //保存用户设置实时库存是否显示
            Integer maxId = systemVarInitMapper.getMaxId(compid);
            systemVarInitMapper.saveStockAggregateShow(compid, aggregateIsShow, maxId + 1);

            Map<String, String> stockAggregateMap = systemVarInitMapper.getStockAggregate(compid);
            try {
                syncPlugin.save(stockAggregateMap, "De_SystemVarInit", "INS", compid);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            //修改用户设置实时库存是否显示
            systemVarInitMapper.updateStockAggregateShow(compid, aggregateIsShow);
            Map<String, String> stockAggregateMap = systemVarInitMapper.getStockAggregate(compid);
            try {
                syncPlugin.save(stockAggregateMap, "De_SystemVarInit", "UP", compid);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
