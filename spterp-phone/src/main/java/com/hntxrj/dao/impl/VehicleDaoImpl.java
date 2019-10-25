package com.hntxrj.dao.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hntxrj.dao.VehicleDao;
import com.hntxrj.entity.PageBean;
import com.hntxrj.util.jdbc.procedure.Param;
import com.hntxrj.util.jdbc.procedure.Procedure;
import com.hntxrj.util.jdbc.enums.ParamType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 功能: 搅拌车Dao接口实现类
 *
 * @author 李帅
 * @Data 2017-08-15.下午 6:10
 */
@Component
@Scope("prototype")
public class VehicleDaoImpl implements VehicleDao {

    private final Procedure procedure;

    @Autowired
    public VehicleDaoImpl(Procedure procedure) {
        this.procedure = procedure;
    }


    /**
     * 搅拌车工作量统计
     *
     * @param mark             标记
     * @param beginTime        开始日期
     * @param endTime          结束日期
     * @param taskId           任务单ID
     * @param builderShortName 工程名简称
     * @param placing          浇灌部位
     * @param eppName          工程名称
     * @param vehicleID        车号
     * @param personalCode     司机代号
     * @param compid           站别代号
     * @param pageBean         分页
     * @param pageBeanList     多组分页
     * @return
     */
    @Override
    public JSONArray getTotalVehicle(Integer mark, String beginTime, String endTime, String taskId, String builderShortName, String placing, String eppName, String vehicleID, String personalCode, String compid, String personalName, PageBean pageBean, List<PageBean> pageBeanList, Integer id, String opid) {
        List<Param> params = new ArrayList<Param>();

        /* 1 搅拌车工作量统计 */
        params.add(new Param(1, ParamType.INPARAM.getType(), mark));
        params.add(new Param(2, ParamType.INPARAM.getType(), pageBean.getPageCurr()));
        params.add(new Param(3, ParamType.INPARAM.getType(), pageBean.getPageSize()));
        params.add(new Param(4, ParamType.INPARAM.getType(), beginTime));
        params.add(new Param(5, ParamType.INPARAM.getType(), endTime));
        params.add(new Param(6, ParamType.INPARAM.getType(), taskId));
        params.add(new Param(7, ParamType.INPARAM.getType(), builderShortName));
        params.add(new Param(8, ParamType.INPARAM.getType(), placing));
        params.add(new Param(9, ParamType.INPARAM.getType(), eppName));
        params.add(new Param(10, ParamType.INPARAM.getType(), vehicleID));
        params.add(new Param(11, ParamType.INPARAM.getType(), personalCode));
        //站别代号
        params.add(new Param(12, ParamType.INPARAM.getType(), compid));
        //司机名
        params.add(new Param(13, ParamType.INPARAM.getType(), personalName));
        //id查详情
        params.add(new Param(14, ParamType.INPARAM.getType(), id));
        params.add(new Param(15, ParamType.INPARAM.getType(), opid));
        procedure.init("sp_Query_vehicle", params);

        try {
            procedure.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (mark == 1 || mark == 2) {
            /* 存储页面总数 */
            if (!procedure.getResultArray().isEmpty()) {

                JSONArray jsonArrayAll = procedure.getResultArray().getJSONArray(4);
                Integer recordCount1 = Integer.parseInt(jsonArrayAll.getJSONObject(0).get("recordCount").toString());


                Integer recordCount2 = jsonArrayAll.getJSONObject(1).size() < 1 ? 0 : Integer.parseInt(jsonArrayAll.getJSONObject(1).get("recordCount").toString());

                Integer recordCount3 = Integer.parseInt(jsonArrayAll.getJSONObject(2).get("recordCount").toString());
                /*为 NULL 引发异常。已经处理*/
                Integer recordCount4 = jsonArrayAll.getJSONObject(3).size() < 1 ? 0 : Integer.parseInt(jsonArrayAll.getJSONObject(3).get("recordCount").toString());

                pageBeanList.get(0).setRecordCount(recordCount1);
                pageBeanList.get(1).setRecordCount(recordCount2);
                pageBeanList.get(2).setRecordCount(recordCount3);
                pageBeanList.get(3).setRecordCount(recordCount4);
                /*String str = procedure.getResultArray().getJSONArray(4).toString();
                str = str.substring( str.indexOf(":")+1, str.indexOf("}"));
                System.out.println("总数为:" + str);
                if ( str.matches("[0-9]*")){
                    pageBean.setRecordCount(Integer.parseInt(str));
                }*/
            }
        } else if (mark != 3 && mark != 4 && mark != 5 && mark != 6) {
            /* 存储页面总数 */
            if (!procedure.getResultArray().isEmpty()) {

                JSONObject jsonObject = procedure.getResultArray().getJSONArray(1).getJSONObject(0);

                Integer recordCount = jsonObject.size() < 1 ? 0 : Integer.parseInt(jsonObject.get("recordCount").toString());
                pageBean.setRecordCount(recordCount);

                    /*String str = procedure.getResultArray().getJSONArray(1).toString();
                    str = str.substring(str.indexOf(":") + 1, str.indexOf("}"));
                    System.out.println("总数为:" + str);
                    if (str.matches("[0-9]*")) {
                        pageBean.setRecordCount(Integer.parseInt(str));
                    }*/
            }
        }
        return procedure.getResultArray();
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
     * @param pageBean         分页
     * @return
     */
    @Override
    public JSONArray getTotalYield(String TaskStatus, String beginTime, String endTime,
                                   String compid, String StirId, String OpId,
                                   String eppname, String TaskId, String BuilderShortName,
                                   String ReportCode, String Placing, String PlaceStyle, String InvoiceType, PageBean pageBean, String stgid, String opid) {
        List<Param> params = new ArrayList<>();
        params.add(new Param(1, ParamType.INPARAM.getType(), pageBean.getPageCurr()));
        params.add(new Param(2, ParamType.INPARAM.getType(), pageBean.getPageSize()));
        params.add(new Param(3, ParamType.INPARAM.getType(), TaskStatus));
        params.add(new Param(4, ParamType.INPARAM.getType(), beginTime));
        params.add(new Param(5, ParamType.INPARAM.getType(), endTime));
        params.add(new Param(6, ParamType.INPARAM.getType(), compid));
        params.add(new Param(7, ParamType.INPARAM.getType(), StirId));
        params.add(new Param(8, ParamType.INPARAM.getType(), OpId));
        params.add(new Param(9, ParamType.INPARAM.getType(), eppname));
        params.add(new Param(10, ParamType.INPARAM.getType(), TaskId));
        params.add(new Param(11, ParamType.INPARAM.getType(), BuilderShortName));
        params.add(new Param(12, ParamType.INPARAM.getType(), ReportCode));
        params.add(new Param(13, ParamType.INPARAM.getType(), Placing));
        params.add(new Param(14, ParamType.INPARAM.getType(), PlaceStyle));
        params.add(new Param(15, ParamType.INPARAM.getType(), InvoiceType));
        params.add(new Param(16, ParamType.INPARAM.getType(), stgid));
        params.add(new Param(17, ParamType.INPARAM.getType(), opid));

        procedure.init("sp_Query_Total_Yield", params);

        try {
            procedure.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }

        /* 存储页面总数 */
        if (!procedure.getResultArray().isEmpty()) {
            String str = procedure.getResultArray().getJSONArray(1).toString();
            str = str.substring(str.indexOf(":") + 1, str.indexOf("}"));
            System.out.println("总数为:" + str);
            if (str.matches("[0-9]*")) {
                pageBean.setRecordCount(Integer.parseInt(str));
            }
        }

        return procedure.getResultArray();
    }


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
    @Override
    public JSONArray sp_insertUpDel_VM_PersonalWorkClass(Integer mark, String compid, String opid, String id, String vehicleid, String workclass, String personalcode, String remarks, String workstartime, String workovertime) {
        List<Param> params = new ArrayList<Param>();
        params.add(new Param(1, ParamType.INPARAM.getType(), mark));
        params.add(new Param(2, ParamType.INPARAM.getType(), compid));
        params.add(new Param(3, ParamType.INPARAM.getType(), opid));
        params.add(new Param(4, ParamType.INPARAM.getType(), id));
        params.add(new Param(5, ParamType.INPARAM.getType(), vehicleid));
        params.add(new Param(6, ParamType.INPARAM.getType(), workclass));
        params.add(new Param(7, ParamType.INPARAM.getType(), personalcode));
        params.add(new Param(8, ParamType.INPARAM.getType(), remarks));
        params.add(new Param(9, ParamType.INPARAM.getType(), workstartime));
        params.add(new Param(10, ParamType.INPARAM.getType(), workovertime));
        params.add(new Param(11, ParamType.INPARAM.getType(), null));
        procedure.init("sp_insertUpDel_VM_PersonalWorkClass_srq", params);
        try {
            procedure.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return procedure.getResultArray();
    }
}
