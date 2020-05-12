package com.hntxrj.txerp.api;

import com.hntxrj.txerp.server.StockService;
import com.hntxrj.txerp.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/stock")
public class StockApi {
    private final StockService stockService;

    @Autowired
    public StockApi(StockService stockService) {
        this.stockService = stockService;
    }


    @PostMapping("/getStock")
    public ResultVO getStock(String compid, Integer stirId) {
        return ResultVO.create(stockService.getStock(compid, stirId));
    }

    /**
     * 获取线号集合
     *
     * @param compid 企业id
     * @return 线号集合
     */
    @PostMapping("/getStirIds")
    public ResultVO getStirIds(String compid) {
        return ResultVO.create(stockService.getStirIds(compid));
    }

    /**
     * 根据线号获取所有的库位信息
     */
    @PostMapping("/getAllStockList")
    public ResultVO getAllStockList(String compid, Integer stirId) {
        return ResultVO.create(stockService.getAllStockList(compid, stirId));
    }

    /**
     * 获取用户选中的不显示的库位代号列表
     */
    @RequestMapping("/getSelectStock")
    public ResultVO getSelectStock(String compid) {
        return ResultVO.create(stockService.getSelectStock(compid));
    }

    /**
     * 保存用户选中的不显示的库位代号
     *
     * @param compid   企业id
     * @param stkCodes 用户选中的不展示的库位代号
     */
    @RequestMapping("/saveStockCodes")
    public ResultVO saveStockCodes(String compid, String stkCodes) {
        stockService.saveStockCodes(compid, stkCodes);
        return ResultVO.create();
    }
}
