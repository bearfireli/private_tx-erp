package com.hntxrj.controller.statistics;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.hntxrj.server.StatisticsService;
import com.hntxrj.server.TotalAllService;
import com.hntxrj.vo.JsonVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 功能:  统计控制器
 *
 * @Auther 李帅
 * @Data 2017-08-28.下午 7:54
 */
@RequestMapping("/total")
@RestController
@Scope("prototype")
@EnableAsync
public class TotalAllController {
    private final TotalAllService totalAllService;
    private final StatisticsService statisticsService;

    @Autowired
    public TotalAllController(TotalAllService totalAllService, StatisticsService statisticsService) {
        this.totalAllService = totalAllService;
        this.statisticsService = statisticsService;
    }

    /**
     * 统计查询
     *
     * @param mark   标记
     * @param compid 企业
     * @return josn
     */
    @RequestMapping("/info")
    public JsonVo totalAll(Integer mark, String compid) {
        JsonVo vo = new JsonVo();
        //mark 不能为空
        if (mark != null && mark > 0) {
            //调用到dao  将到返回的jsonarry 设置到返回页面的json
            vo.setData(totalAllService.totalAll(mark, compid));
            //设置返回提示code
            vo.setCode(0);
            vo.setMsg("ok");
        } else {
            //返回错误提示code   =1
            vo.setCode(1);
            vo.setMsg("error,mark is Null!");
        }
        return vo;
    }


    @RequestMapping("/phoneStatistics")
    public String phoneStatistics(String compid, int type, String beginTime, String endTime) {
        return JSON.toJSONString(statisticsService.phoneStatistics(compid, type, beginTime, endTime));
    }


}
