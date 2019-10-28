package com.hntxrj.txerp.controller.produce;


import com.alibaba.fastjson.JSONArray;
import com.hntxrj.txerp.entity.PageBean;
import com.hntxrj.txerp.server.ConsumeService;
import com.hntxrj.txerp.util.PageRecoedCountUtils;
import com.hntxrj.txerp.vo.JsonVo;
import com.hntxrj.txerp.vo.JsonVoAndPage;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "/consume")
@Scope("prototype")
@EnableAsync
public class ConsumeController {

    private final ConsumeService consumeService;

    @Autowired
    public ConsumeController(ConsumeService consumeService) {
        this.consumeService = consumeService;
    }


    /**
     * 生产消耗汇总   存储过程  sp_Query_V_Account_PTProduceConsume
     *
     * @param mark            状态
     * @param compid          企业
     * @param currPage        当企业
     * @param PageSize        页长度
     * @param vehicleID       车号
     * @param stirId          线号
     * @param taskId          任务单好
     * @param stgId           砼标号
     * @param empName         操作员
     * @param Placing         浇筑部位
     * @param eppName         工程名称
     * @param builderShorName 施工单位简称
     * @param openTime        查询时间
     * @param overTime        救赎时间
     * @return json
     */
    @RequestMapping(value = "/spQueryVAccountPTProduceConsume")
    public JsonVo spQueryVAccountPTProduceConsume(@RequestParam(defaultValue = "0") Integer currPage,
                                                  @RequestParam(defaultValue = "10") Integer PageSize,
                                                  String vehicleID,
                                                  String stirId,
                                                  String taskId,
                                                  String stgId,
                                                  String empName,
                                                  String Placing,
                                                  String eppName,
                                                  String builderShorName,
                                                  @RequestParam String openTime,
                                                  @RequestParam String overTime,
                                                  Integer mark,
                                                  String compid,
                                                  String opid,
                                                  Integer taskstatus,
                                                  HttpServletRequest request) {
//        StringNullUtils.StringNull(stirId,vehicleID,taskId,stgId,empName,Placing,eppName,builderShorName,openTime,overTime);
        //参数不能为空""  防止查询出没数据
        if (StringUtils.isEmpty(stirId)) {
            stirId = null;
        }
        if (StringUtils.isEmpty(vehicleID)) {
            vehicleID = null;
        }
        if (StringUtils.isEmpty(taskId)) {
            taskId = null;
        }
        if (StringUtils.isEmpty(stgId)) {
            stgId = null;
        }
        if (StringUtils.isEmpty(empName)) {
            empName = null;
        }
        if (StringUtils.isEmpty(Placing)) {
            Placing = null;
        }
        if (StringUtils.isEmpty(eppName)) {
            eppName = null;
        }
        if (StringUtils.isEmpty(builderShorName)) {
            builderShorName = null;
        }
        if (StringUtils.isEmpty(openTime)) {
            openTime = null;
        }
        if (StringUtils.isEmpty(overTime)) {
            overTime = null;
        }

        //创建分页
        JsonVoAndPage jsonVoAndPage = new JsonVoAndPage();
        if (StringUtils.isEmpty(compid) && StringUtils.isEmpty(opid)) {
            jsonVoAndPage.setCode(9);
            jsonVoAndPage.setMsg("请登录");
            return jsonVoAndPage;
        }

        //调用dao
        JSONArray jsonArray =
                consumeService.spQueryVQueryProduceConsume(compid, currPage,
                        PageSize, vehicleID, stirId,
                        taskId, stgId, empName, Placing,
                        eppName, builderShorName, openTime,
                        overTime, mark, taskstatus, opid);
        //jsonarry  不能为null
        if (jsonArray != null && jsonArray.size() > 0) {
            //取统计的总条数
            PageBean page = PageRecoedCountUtils.getPage(jsonArray, PageSize, currPage);
            if (page != null) {
                jsonVoAndPage.setPageBean(page);
            }
            jsonVoAndPage.setCode(0);
            jsonVoAndPage.setMsg("ok");
            jsonVoAndPage.setData(jsonArray);
            return jsonVoAndPage;
        } else {
            jsonVoAndPage.setCode(1);
            jsonVoAndPage.setMsg("result null");
            return jsonVoAndPage;
        }
    }

    /**
     * 司机排班
     *
     * @param currPage     当前页
     * @param PageSize     页长度
     * @param PersonalCode 司机
     * @param VehicleID    车号
     * @return
     */
    @RequestMapping(value = "/spQueryVMWorkClassLog")
    public JsonVo spQueryVMWorkClassLog(Integer currPage,
                                        Integer PageSize,
                                        String PersonalCode,
                                        String VehicleID,
                                        String compid,
                                        String opid,
                                        String beginTime,
                                        String endTime,
                                        Integer workclass,  // 0早班 1 中班,  2 晚班
                                        HttpServletRequest request) {
        JsonVoAndPage jsonVoAndPage = new JsonVoAndPage();
//        compid = 不能为空
        if (StringUtils.isEmpty(compid) && StringUtils.isEmpty(opid)) {
            jsonVoAndPage.setCode(9);
            jsonVoAndPage.setMsg("请登录");
            return jsonVoAndPage;
        }

        //参数为空时准成null
        if (StringUtils.isEmpty(PersonalCode)) {
            PersonalCode = null;
        }
        if (StringUtils.isEmpty(VehicleID)) {
            VehicleID = null;
        }
        //调用dao
        JSONArray jsonArray = consumeService.spQueryVMWorkClassLog(compid, currPage, PageSize, PersonalCode, VehicleID, beginTime, endTime, opid, workclass);
        //创建page
        PageBean pageBean = new PageBean(PageSize, currPage);
        //jsaroArry  不能为空
        if (jsonArray != null && jsonArray.size() > 0) {
            if (jsonArray != null) {
                //取统计的总条数
                if (jsonArray.size() >= 2) {

                    String s = jsonArray.getJSONArray(1).get(0).toString();
                    System.out.println(s);
                    String substring = s.substring(s.indexOf(":") + 1, s.indexOf("}"));
                    if (substring.matches("[0-9]*")) {
                        pageBean.setRecordCount(Integer.parseInt(substring));
                    }
                }
            }
            jsonVoAndPage.setCode(0);
            jsonVoAndPage.setMsg("ok");
            jsonVoAndPage.setData(jsonArray);
            jsonVoAndPage.setPageBean(pageBean);
            return jsonVoAndPage;
        } else {
            jsonVoAndPage.setCode(1);
            jsonVoAndPage.setMsg("result null");

            jsonVoAndPage.setPageBean(pageBean);

            return jsonVoAndPage;
        }
    }

    /**
     * 查询企业名称
     *
     * @param currPage 当前页
     * @param pageSize 页长度
     * @param var1     var1
     * @param var2     var2
     * @return
     */
    @RequestMapping(value = "/spQueryUsercomp")
    public JsonVo spQueryUsercomp(@RequestParam(defaultValue = "0") Integer currPage, @RequestParam(defaultValue = "1000") Integer pageSize, String var1, String var2) {
        JsonVoAndPage jsonVoAndPage = new JsonVoAndPage();
        try {
            //掉用dao
            JSONArray jsonArray = consumeService.spQueryUsercomp(currPage, pageSize, var1, var2);
            jsonVoAndPage.setCode(0);
            jsonVoAndPage.setMsg("ok");
            jsonVoAndPage.setData(jsonArray);
            return jsonVoAndPage;
        } catch (Exception e) {
            e.printStackTrace();
            jsonVoAndPage.setCode(1);
            jsonVoAndPage.setMsg("查询出错");
            return jsonVoAndPage;
        }
    }
}
