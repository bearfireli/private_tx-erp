package com.hntxrj.txerp.controller.stock;

import com.hntxrj.txerp.server.StockService;
import com.hntxrj.txerp.vo.JsonVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 功能: 库存统计控制器
 *
 * @Auther 李帅
 * @Data 2017-08-17.上午 10:24
 */
@RestController
@RequestMapping("/produce")
@Scope("prototype")
@EnableAsync
@Api("库存")
public class StockController {

    private final StockService stockService;

    @Autowired
    public StockController(StockService stockService) {
        this.stockService = stockService;
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
    @ApiOperation("实时库存")
    @RequestMapping("/getRealStock")
    public JsonVo getRealStock(String stirId, String compid, String opid, Integer queryType) {
        JsonVo vo = new JsonVo();
        if (compid == null || "".equals(compid)) {
            vo.setCode(1);
            vo.setMsg("企业id不能为空");
        } else {
            vo.setCode(0);
            vo.setMsg("ok");
            /* 为Null为空 */
            stirId = stirId == null || "".equals(stirId) ? null : stirId;
            vo.setData(stockService.realStock(stirId, compid, opid, queryType));
        }
        return vo;
    }

    /**
     * 获取库位信息
     * <p>
     * 根据企业ID
     *
     * @param compid 企业ID
     * @return {@link JsonVo}
     */
    @RequestMapping(value = "/getRealStock.do")
    public JsonVo getStock(String compid) {
        JsonVo jsonVo = new JsonVo();
        if (compid == null || "".equals(compid)) {
            jsonVo.setCode(1);
            jsonVo.setMsg("企业ID不能为空");
        } else {
            jsonVo.setCode(0);
            jsonVo.setMsg("ok");
            jsonVo.setData(stockService.getStock(compid));
        }
        return jsonVo;
    }


}
