package com.hntxrj.dao.impl;


import com.alibaba.fastjson.JSONArray;
import com.hntxrj.dao.QueryTotalYieldDao;

import com.hntxrj.entity.PageBean;
import com.hntxrj.util.jdbc.enums.ParamType;
import com.hntxrj.util.jdbc.procedure.Param;
import com.hntxrj.util.jdbc.procedure.Procedure;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Scope("prototype")
public class QueryTotalYieIdDaoImpl implements QueryTotalYieldDao {
    private final Procedure procedure;

    @Autowired
    public  QueryTotalYieIdDaoImpl(Procedure procedure) {
        this.procedure = procedure;
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
     * @return                 json
     */
    @Override
    public JSONArray getQueryTotalYieId(String taskStatus, String beginTime, String endTime, String compid,
                                        String stirId, String opId, String eppname, String taskId, String
                                                builderShortName, String reportCode, String placing, String
                                                placeStyle, String invoiceType, PageBean pageBean, String stgid,
                                        String opid) {
        List<Param> params = new ArrayList<>();
        params.add(new Param(1, ParamType.INPARAM.getType(), pageBean.getPageCurr()));
        params.add(new Param(2, ParamType.INPARAM.getType(), pageBean.getPageSize()));
        params.add(new Param(3, ParamType.INPARAM.getType(), taskStatus));
        params.add(new Param(4, ParamType.INPARAM.getType(), beginTime));
        params.add(new Param(5, ParamType.INPARAM.getType(), endTime));
        params.add(new Param(6, ParamType.INPARAM.getType(), compid));
        params.add(new Param(7, ParamType.INPARAM.getType(), stirId));
        params.add(new Param(8, ParamType.INPARAM.getType(), opId));
        params.add(new Param(9, ParamType.INPARAM.getType(), eppname));
        params.add(new Param(10, ParamType.INPARAM.getType(), taskId));
        params.add(new Param(11, ParamType.INPARAM.getType(), builderShortName));
        params.add(new Param(12, ParamType.INPARAM.getType(), reportCode));
        params.add(new Param(13, ParamType.INPARAM.getType(), placing));
        params.add(new Param(14, ParamType.INPARAM.getType(), placeStyle));
        params.add(new Param(15, ParamType.INPARAM.getType(), invoiceType));
        params.add(new Param(16, ParamType.INPARAM.getType(), stgid));
        params.add(new Param(17, ParamType.INPARAM.getType(), opid));
        procedure.init("sp_Query_Total_Output",params);
        try {
            procedure.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  procedure.getResultArray();
    }
}
