package com.hntxrj.dao;

import com.alibaba.fastjson.JSONArray;
import com.hntxrj.entity.PageBean;

import java.util.List;

/**
 * 功能: 搅拌车Dao接口
 *
 * @Auther 李帅
 * @Data 2017-08-15.下午 5:49
 */
public interface VehicleDao {


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
    JSONArray getTotalVehicle(Integer mark, String beginTime, String endTime, String taskId, String builderShortName, String placing, String eppName, String vehicleID, String personalCode, String compid, String personalName, PageBean pageBean, List<PageBean> pageBeanList, Integer id, String opid);

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
    JSONArray getTotalYield(String TaskStatus, String beginTime, String endTime,
                            String compid, String StirId, String OpId,
                            String eppname, String TaskId, String BuilderShortName,
                            String ReportCode, String Placing, String PlaceStyle, String InvoiceType, PageBean pageBean, String stgid, String opid);


    /**
     * 司机排班信息管理 sp_insertUpDel_VM_PersonalWorkClass
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

    JSONArray sp_insertUpDel_VM_PersonalWorkClass(Integer mark, String compid, String opid, String id, String vehicleid, String workclass, String personalcode, String remarks, String workstartime, String workovertime);
}
