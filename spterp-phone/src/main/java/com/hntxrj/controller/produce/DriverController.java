package com.hntxrj.controller.produce;

import com.alibaba.fastjson.JSONArray;
import com.hntxrj.entity.PageBean;
import com.hntxrj.server.DriverService;
import com.hntxrj.util.CommonUtil;
import com.hntxrj.vo.JsonVo;
import com.hntxrj.vo.JsonVoAndPage;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 功能及介绍： 司机模块
 * <p>
 * ========================
 * Created with IntelliJ IDEA.
 * User： 李 帅
 * Date：2018/5/29
 * Time：下午5:28
 * ========================
 *
 * @author 李帅
 */
@RestController
@RequestMapping(value = "/driver")
@Scope("prototype")
@EnableAsync
public class DriverController {

    /**
     * 司机模块注册
     */
    private final DriverService driverService;

    @Autowired
    public DriverController(DriverService driverService) {
        this.driverService = driverService;
    }


    /**
     * 司机排班列表信息
     *
     * @param compid        企业ID
     * @param stirId        线号/搅拌楼楼号
     * @param vehicleStatus 车状态  3 正在生产  1 等待生产
     * @param vehicleClass  班次
     * @return {@link JsonVo}
     */
    @RequestMapping(value = "getDriverSchedulingLed")
    public JsonVoAndPage getDriverScheduling(String compid, String stirId,
                                             @RequestParam(defaultValue = "") String vehicleStatus,
                                             String vehicleClass) {
        if (StringUtils.isBlank(compid)) {
            compid = null;
        }
        if (StringUtils.isBlank(stirId)) {
            stirId = null;
        }
        if (StringUtils.isBlank(vehicleClass)) {
            vehicleClass = null;
        }
        if (StringUtils.isBlank(vehicleStatus)) {
            vehicleStatus = null;
        }
        JsonVoAndPage jsonVo = new JsonVoAndPage();
        jsonVo.setCode(0);
        jsonVo.setMsg("ok");
        //为了兼容 API.js的写法， 必须传个页，不然报错了就
        jsonVo.setPageBean(new PageBean(0, 10));
        List<JSONArray> list = new ArrayList<>();
        list.add(driverService.getDriverScheduling(compid, stirId, "3", vehicleClass));
        list.add(driverService.getDriverScheduling(compid, stirId, "1", vehicleClass));
        jsonVo.setData(list);
        return jsonVo;
    }

}
