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
}
