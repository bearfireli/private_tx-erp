package com.hntxrj.txerp.controller.statistics;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hntxrj.txerp.entity.PageBean;
import com.hntxrj.txerp.server.TaskSaleServer;
import com.hntxrj.txerp.vo.JsonVo;
import com.hntxrj.txerp.vo.JsonVoAndPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/taskSale")
@Scope("prototype")
@EnableAsync
// TODO: 放入统计
public class TaskSaleController {

    private final TaskSaleServer taskSaleServer;

    @Autowired
    public TaskSaleController(TaskSaleServer taskSaleServer) {
        this.taskSaleServer = taskSaleServer;
    }

    /**
     * 泵车工作量统计
     *
     * @param mark         1  泵车工作量汇总统计 2 泵车工作量统计（按司机）3泵车工作量明细汇总 4泵车泵工工作量明细汇总 5泵车工作量-工程方量统计
     * @param compid       企业
     * @param opid         用户
     * @param beginTime    开始时间
     * @param endTime      结束时间
     * @param currPage     当前页
     * @param PageSize     页长度
     * @param vehicleID    车号
     * @param personalname 司机
     * @param eppName      工程名称
     * @param stirid       楼号
     * @return json
     */
    @RequestMapping(value = "/spVVMPumpTruckPersonalWorksrq")
    public JsonVo sp_V_VM_PumpTruckPersonalWorksrq(String mark,
                                                   String compid,
                                                   String opid,
                                                   String beginTime,
                                                   String endTime,
                                                   Integer currPage,
                                                   Integer PageSize,
                                                   @RequestParam(required = false) String vehicleID,
                                                   @RequestParam(required = false) String personalname,
                                                   @RequestParam(required = false) String eppName,
                                                   @RequestParam(required = false) String stirid) {
        //参数不能为""   为"" 时 转化成null
        if (StringUtils.isEmpty(vehicleID)) {
            vehicleID = null;
        }
        if (StringUtils.isEmpty(personalname)) {
            personalname = null;
        }
        if (StringUtils.isEmpty(eppName)) {
            eppName = null;
        }
        if (StringUtils.isEmpty(stirid)) {
            stirid = null;
        }


        JsonVoAndPage jsonVoAndPage = null;
        try {
            jsonVoAndPage = new JsonVoAndPage();
            //调用dao
            JSONArray jsonArray = taskSaleServer.sp_V_VM_PumpTruckPersonalWorksrq(mark, compid,
                    opid, beginTime, endTime, currPage, PageSize, vehicleID, personalname, eppName, stirid);
            //创建分页
            PageBean pageBean = new PageBean(PageSize, currPage);
            //设置code提示信息
            jsonVoAndPage.setCode(0);
            jsonVoAndPage.setMsg("ok");
            jsonVoAndPage.setData(jsonArray);
            JSONObject o = jsonArray.getJSONArray(1).getJSONObject(0);
            Integer recordCount = o.getInteger("recordCount");
            pageBean.setRecordCount(recordCount);
            jsonVoAndPage.setPageBean(pageBean);
        } catch (Exception e) {
            //有异常是直接返回code =1
            e.printStackTrace();
            jsonVoAndPage.setCode(1);
            jsonVoAndPage.setMsg("程序异常");
            return jsonVoAndPage;
        }
        return jsonVoAndPage;
    }
}
