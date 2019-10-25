package com.hntxrj.controller.statistics;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hntxrj.entity.PageBean;
import com.hntxrj.server.QueryTotalYieIdService;
import com.hntxrj.server.VehicleService;
import com.hntxrj.vo.JsonVo;
import com.hntxrj.vo.JsonVoAndPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 功能: 搅拌车控制器
 *
 * @Auther 李帅
 * @Data 2017-08-15.下午 6:23
 */
@RestController
@RequestMapping("/produce")
@Scope("prototype")
@EnableAsync
public class VehicleController {

    @Autowired
    public void setVehicleService(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    public VehicleController(QueryTotalYieIdService queryTotalYieIdService) {
        this.queryTotalYieIdService = queryTotalYieIdService;
    }

    private VehicleService vehicleService;
    private final QueryTotalYieIdService queryTotalYieIdService;


    /**
     * 搅拌车工作量统计
     *
     * @param currPage         当前页
     * @param beginTime        开始时间
     * @param endTime          结束时间
     * @param taskId           任务单ID
     * @param builderShortName 工程名简称
     * @param placing          浇灌部位
     * @param eppName          工程名称
     * @param vehicleID        车号
     * @param personalCode     司机代号
     * @param compid           站别代号
     * @param personalName     司机名
     * @return
     */
    @RequestMapping("/getTotalVehicle")
    public JsonVoAndPage getTotalVehicle(
            @RequestParam(value = "currPage", defaultValue = "0") Integer currPage,
            Integer mark, String beginTime,
            String endTime, String taskId,
            String builderShortName, String placing,
            String eppName, String vehicleID,
            String personalCode, Integer id,
            String compid, String personalName,
            String opid) {
        JsonVoAndPage vo = new JsonVoAndPage();

        taskId = taskId == null || "".equals(taskId) ? null : taskId;
        builderShortName = builderShortName == null || "".equals(builderShortName) ? null : builderShortName;
        placing = placing == null || "".equals(placing) ? null : placing;
        eppName = eppName == null || "".equals(eppName) ? null : eppName;
        vehicleID = vehicleID == null || "".equals(vehicleID) ? null : vehicleID;
        personalCode = personalCode == null || "".equals(personalCode) ? null : personalCode;
        personalName = personalName == null || "".equals(personalName) ? null : personalName;
        compid = compid == null || "".equals(compid) ? null : compid;
        vo.setCode(1);
        if (mark == 1 || mark == 2) {
            if (beginTime == null || "".equals(beginTime)) {
                vo.setMsg("error,beginTime is null ");
            } else if (endTime == null || "".equals(endTime)) {
                vo.setMsg("error,endTime is null ");
            } else if (mark == null) {
                vo.setMsg("error,mark is null ");
            } else {
                vo.setCode(0);
                vo.setMsg("ok");
                List<PageBean> pageBeanList = vo.getListPageBean();
                pageBeanList.add(new PageBean(20, currPage));
                pageBeanList.add(new PageBean(20, currPage));
                pageBeanList.add(new PageBean(20, currPage));
                pageBeanList.add(new PageBean(20, currPage));


                vo.setData(vehicleService.getTotalVehicle(mark,
                        beginTime, endTime, taskId, builderShortName,
                        placing, eppName, vehicleID, personalCode, compid,
                        personalName, new PageBean(10, currPage),
                        pageBeanList, id, opid));

                vo.setListPageBean(pageBeanList);
            }
        } else if (mark == 3 || mark == 4 || mark == 5 || mark == 6) {  //搅拌详情
            if (id == null || id == 0) {
                vo.setMsg("error,id is null ");
            } else if (compid == null || compid.equals("")) {
                vo.setMsg("error,compid is null ");
            } else {
                vo.setCode(0);
                vo.setMsg("ok");

                PageBean pb = new PageBean(20, currPage);
                vo.setData(vehicleService.getTotalVehicle(mark, beginTime, endTime,
                        taskId, builderShortName, placing, eppName, vehicleID,
                        personalCode, compid, personalName, pb, null,
                        id, opid));
            }
        }/*else if ( mark == 4 ){  //司机详情
            if ( id == null || id == 0 ){
                vo.setMsg("error,id is null ");
            }else if ( compid == null || compid.equals("") ){
                vo.setMsg("error,compid is null ");
            }else{
                vo.setCode(0);
                vo.setMsg("ok");

                PageBean pb = new PageBean(20,currPage);
                vo.setData( vehicleService.getTotalVehicle(mark,beginTime,endTime,taskId,builderShortName,placing,eppName,vehicleID,personalCode,compid,personalName,pb,null) );
            }
        }*/ else {

            if (beginTime == null || "".equals(beginTime)) {
                vo.setMsg("error,beginTime is null ");
            } else if (endTime == null || "".equals(endTime)) {
                vo.setMsg("error,endTime is null ");
            } else if (mark == null) {
                vo.setMsg("error,mark is null ");
            } else {

                if (mark.toString().startsWith("1") && mark.toString().length() == 2 || mark.toString().startsWith("2") && mark.toString().length() == 2) {
                    vo.setCode(0);
                    vo.setMsg("ok");
                    PageBean pb = new PageBean(20, currPage);
                    vo.setData(vehicleService.getTotalVehicle(mark, beginTime,
                            endTime, taskId, builderShortName, placing, eppName,
                            vehicleID, personalCode, compid, personalName, pb,
                            null, id, opid));
                    vo.setPageBean(pb);
                }
            }
        }



        return vo;
    }


    /**
     * 砼产量统计
     *
     * @param TaskStatus       任务单状态
     * @param beginTime        开始时间
     * @param endTime          结束时间
     * @param compid           生产站点
     * @param StirId           搅拌楼编号
     * @param OpId             调度员
     * @param eppname          工程名
     * @param TaskId           任务单ID
     * @param BuilderShortName 施工单位名简称
     * @param ReportCode       报告编号
     * @param Placing          浇灌部位
     * @param PlaceStyle       浇灌方式
     * @param InvoiceType      小票类型
     * @param stgid            砼标号
     * @param currPage         当前页
     * @return
     */
    @RequestMapping("/getTotalYield")
    public JsonVoAndPage getTotalYield(String TaskStatus, String beginTime, String endTime,
                                       String compid, String StirId, String OpId, String stgid,
                                       String eppname, String TaskId, String BuilderShortName,
                                       String ReportCode, String Placing, String PlaceStyle, String InvoiceType,
                                       @RequestParam(value = "currPage", defaultValue = "0") Integer currPage,
                                       String opid) {
        JsonVoAndPage vo = new JsonVoAndPage();
        vo.setCode(1);


        compid = compid == null || "".equals(compid) ? null : compid;
        TaskStatus = TaskStatus == null || "".equals(TaskStatus) ? null : TaskStatus;
        StirId = StirId == null || "".equals(StirId) ? null : StirId;
        OpId = OpId == null || "".equals(OpId) ? null : OpId;
        eppname = eppname == null || "".equals(eppname) ? null : eppname;
        TaskId = TaskId == null || "".equals(TaskId) ? null : TaskId;
        BuilderShortName = BuilderShortName == null || "".equals(BuilderShortName) ? null : BuilderShortName;
        ReportCode = ReportCode == null || "".equals(ReportCode) ? null : ReportCode;
        Placing = Placing == null || "".equals(Placing) ? null : Placing;
        stgid = stgid == null || "".equals(stgid) ? null : stgid;
        PlaceStyle = PlaceStyle == null || "".equals(PlaceStyle) ? null : PlaceStyle;
        InvoiceType = InvoiceType == null || "".equals(InvoiceType) ? null : InvoiceType;

        if (beginTime == null || "".equals(beginTime)) {
            vo.setMsg("error,beginTime is null ");
        } else if (endTime == null || "".equals(endTime)) {
            vo.setMsg("error,endTime is null ");
        } else {

            PageBean pb = new PageBean(10, currPage);
            vo.setMsg("ok");
            vo.setData(vehicleService.getTotalYield(TaskStatus, beginTime,
                    endTime, compid, StirId, OpId, eppname,
                    TaskId, BuilderShortName, ReportCode, Placing,
                    PlaceStyle, InvoiceType, pb, stgid, opid));
            vo.setCode(0);
            vo.setPageBean(pb);
        }
        return vo;
    }


    ///2017年11月8日18:37:06
//	( @Mark 		[int],   --操作标识 0 插入 1 更新 2 删除
//    @compid    		[varchar](2),
//    @OpId               [varchar](10),
//    @id                 [int],
//    @VehicleID_1             [varchar](30),   --车号
//    @WorkClass_2 	        [varchar](10),  -- 班次
//    @PersonalCode_3 	[varchar](10),   -- 司机号
//    @Remarks_4 	[varchar](50),  -- 备注
//    @WorkStarTime_5  	[varchar](19),   -- 上班时间
//    @WorkOverTime_6	[varchar](19),    -- 下班时间
//    @strReturnValue as varchar(50) output

    /**
     * 司机排班信息管理 sp_insertUpDel_VM_PersonalWorkClass_srq
     *
     * @param mark         --操作标识 0 插入 1 更新 2 删除
     * @param compid       企业
     * @param opid         登录用户
     * @param id           操作表的主键  VM_PersonalWorkClass
     * @param vehicleid    车号
     * @param workclass    班次
     * @param personalcode 司机code
     * @param workstartime 上班时间
     * @param workovertime 下班时间
     * @return json
     */
    @RequestMapping(value = "spinsertUpDelVMPersonalWorkClass")
    public JsonVo sp_insertUpDel_VM_PersonalWorkClass(Integer mark,
                                                      String compid,
                                                      String opid,
                                                      String id,
                                                      String vehicleid,
                                                      String workclass,
                                                      String personalcode,
                                                      String remarks,
                                                      String workstartime,
                                                      String workovertime) {
        JsonVo jsonVo = new JsonVo();
        JSONArray jsonArray = vehicleService.sp_insertUpDel_VM_PersonalWorkClass(mark, compid, opid, id, vehicleid, workclass, personalcode, remarks, workstartime, workovertime);
        if (jsonArray == null) {
            jsonVo.setCode(1);
            jsonVo.setMsg("没有更多数据了");
            return jsonVo;
        }
        JSONObject jsonObject = (JSONObject) jsonArray.getJSONArray(0).get(0);
        String result = jsonObject.getString("result");
        //result  不包含成功  就返回失败提示
        if (!result.contains("成功")) {
            jsonVo.setCode(1);
            jsonVo.setMsg(result);
            jsonVo.setData(jsonArray);
            return jsonVo;
        }
        jsonVo.setCode(0);
        jsonVo.setData(jsonArray);
        jsonVo.setMsg(result);
        return jsonVo;
    }


    /**
     * 跟据砼标号统计砼产量
     *
     * @param taskStatus       任务单状态
     * @param beginTime        开始时间
     * @param endTime          结束时间
     * @param compid           生产站点
     * @param stirId           搅拌楼编号
     * @param opId             调度员
     * @param eppname          工程名
     * @param taskId           任务单ID
     * @param builderShortName 施工单位名简称
     * @param reportCode       报告编号
     * @param placing          浇灌部位
     * @param placeStyle       浇灌方式
     * @param invoiceType      小票类型
     * @param pageBean         分页
     * @param opid             操作员id
     * @return
     */
    @RequestMapping("/yieid")
    public JsonVo selectQueryTotalYieId(String taskStatus, String beginTime, String endTime,
                                        String compid, String stirId, String opId,
                                        String eppname, String taskId, String builderShortName,
                                        String reportCode, String placing, String placeStyle,
                                        String invoiceType, PageBean pageBean, String stgid, String opid) {
        JsonVo vo = new JsonVo();
        vo.setCode(1);
        if (stgid == null || "".equals(stgid)) {
            vo.setMsg("erroe,stgid is null");
        }
        if (beginTime == null || "".equals(beginTime)) {
            vo.setMsg("error,beginTime is null ");
        } else if (endTime == null || "".equals(endTime)) {
            vo.setMsg("error,endTime is null ");
        } else {
            vo.setMsg("ok");
            vo.setData(queryTotalYieIdService.selectQueryTotalYieId(taskStatus, beginTime, endTime, compid, stirId,
                    opId, eppname, taskId, builderShortName, reportCode, placing, placeStyle, invoiceType, pageBean, stgid, opid));
            vo.setCode(0);
        }
        return vo;

    }
}
