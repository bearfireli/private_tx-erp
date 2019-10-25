package com.hntxrj.controller.stock;

import com.hntxrj.server.StockService;
import com.hntxrj.vo.JsonVo;
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
public class StockController {

    private final StockService stockService;

    @Autowired
    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

    /**
     * 实时库存
     *
     * @param stirId 搅拌楼号
     * @return
     */
    @RequestMapping("/getRealStock")
    public JsonVo getRealStock(String stirId, String compid, String opid) {
        JsonVo vo = new JsonVo();
        if (compid == null || "".equals(compid)) {
            vo.setCode(1);
            vo.setMsg("企业id不能为空");
        } else {
            vo.setCode(0);
            vo.setMsg("ok");
            /* 为Null为空 */
            stirId = stirId == null || "".equals(stirId) ? null : stirId;
            vo.setData(stockService.realStock(stirId, compid, opid));
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
