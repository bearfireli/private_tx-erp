package com.hntxrj.txerp.api;

import com.hntxrj.txerp.server.StockService;
import com.hntxrj.txerp.vo.ResultVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "实时库存")
@RestController
@RequestMapping("/api/stock")
public class StockApi {
    private final StockService stockService;

    @Autowired
    public StockApi(StockService stockService) {
        this.stockService = stockService;
    }


    @ApiOperation("实时库存")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "compid", value = "企业id", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "stirId", value = "线号", dataType = "int", required = true, paramType = "query"),
    })
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
    @ApiOperation("获取线号集合")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "compid", value = "企业id", required = true, dataType = "String", paramType = "query"),
    })
    @PostMapping("/getStirIds")
    public ResultVO getStirIds(String compid) {
        return ResultVO.create(stockService.getStirIds(compid));
    }

    /**
     * 根据线号获取所有的库位信息
     */
    @ApiOperation("根据线号获取所有的库位信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "compid", value = "企业id", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "stirId", value = "线号", required = true, dataType = "int", paramType = "query"),
    })
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


    /**
     * 获取用户设置的骨料是否显示的信息
     *
     * @param compid 企业id
     */
    @RequestMapping("/getStockAggregateShow")
    public ResultVO getStockAggregateShow(String compid) {
        return ResultVO.create(stockService.getStockAggregateShow(compid));
    }

    /**
     * 设置实时库存骨料是否显示
     *
     * @param compid          企业id
     * @param aggregateIsShow 骨料是否显示 0:显示；  1:不显示
     */
    @RequestMapping("/setStockAggregateShow")
    public ResultVO setStockAggregateShow(String compid, Integer aggregateIsShow) {
        stockService.setStockAggregateShow(compid, aggregateIsShow);
        return ResultVO.create();
    }


}
