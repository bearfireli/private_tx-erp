package com.hntxrj.dao.impl;

import com.alibaba.fastjson.JSONArray;
import com.hntxrj.dao.ConsumeDao;
import com.hntxrj.util.jdbc.procedure.Param;
import com.hntxrj.util.jdbc.procedure.Procedure;
import com.hntxrj.util.jdbc.enums.ParamType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Scope("prototype")
public class ConsumeDaoImpl implements ConsumeDao {

    private final Procedure procedure;

    @Autowired
    public ConsumeDaoImpl(Procedure procedure) {
        this.procedure = procedure;
    }

    /**
     * 生产消耗汇总
     *
     * @param mark            状态
     * @param compid          企业
     * @param currPage        当企业
     * @param pageSize        页长度
     * @param vehicleID       车号
     * @param stirId          线号
     * @param taskId          任务单好
     * @param stgId           砼标号
     * @param empname         操作员
     * @param placing         浇筑部位
     * @param eppName         工程名称
     * @param builderShorName 施工单位简称
     * @param openTime        查询时间
     * @param overTime        救赎时间
     * @return json
     */
    @Override
    public JSONArray spQueryVQueryProduceConsume(String compid, Integer currPage,
                                                 Integer pageSize, String vehicleID,
                                                 String stirId, String taskId,
                                                 String stgId, String empname,
                                                 String placing, String eppName,
                                                 String builderShorName, String openTime,
                                                 String overTime, Integer mark,
                                                 Integer taskstatus, String opid) {
        List<Param> params = new ArrayList<>();
        params.add(new Param(1, ParamType.INPARAM.getType(), compid));
        params.add(new Param(2, ParamType.INPARAM.getType(), currPage));
        params.add(new Param(3, ParamType.INPARAM.getType(), pageSize));
        params.add(new Param(4, ParamType.INPARAM.getType(), vehicleID));
        params.add(new Param(5, ParamType.INPARAM.getType(), stirId));
        params.add(new Param(6, ParamType.INPARAM.getType(), taskId));
        params.add(new Param(7, ParamType.INPARAM.getType(), stgId));
        params.add(new Param(8, ParamType.INPARAM.getType(), empname));
        params.add(new Param(9, ParamType.INPARAM.getType(), placing));
        params.add(new Param(10, ParamType.INPARAM.getType(), eppName));
        params.add(new Param(11, ParamType.INPARAM.getType(), builderShorName));
        params.add(new Param(12, ParamType.INPARAM.getType(), openTime));
        params.add(new Param(13, ParamType.INPARAM.getType(), overTime));
        params.add(new Param(14, ParamType.INPARAM.getType(), mark));
        params.add(new Param(15, ParamType.INPARAM.getType(), taskstatus));
        params.add(new Param(16, ParamType.INPARAM.getType(), opid));
        procedure.init("sp_Query_V_Account_PTProduceConsume", params);
        try {
            procedure.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return procedure.getResultArray();
    }


    /**
     * 生产消耗汇总 list  一次执行3个
     *
     * @param compid          企业
     * @param currPage        当企业
     * @param pageSize        页长度
     * @param vehicleID       车号
     * @param stirId          线号
     * @param taskId          任务单好
     * @param stgId           砼标号
     * @param empname         操作员
     * @param placing         浇筑部位
     * @param eppName         工程名称
     * @param builderShorName 施工单位简称
     * @param openTime        查询时间
     * @param overTime        救赎时间
     * @return json
     */
    @Override
    public JSONArray spspQueryVAccountPTProduceConsumeList3(String compid, Integer currPage, Integer pageSize, String vehicleID, Integer stirId, String taskId, String stgId, String empname, String placing, String eppName, String builderShorName, String openTime, String overTime) {
        List<Param> params = new ArrayList<>();
        params.add(new Param(1, ParamType.INPARAM.getType(), compid));
        params.add(new Param(2, ParamType.INPARAM.getType(), currPage));
        params.add(new Param(3, ParamType.INPARAM.getType(), pageSize));
        params.add(new Param(4, ParamType.INPARAM.getType(), vehicleID));
        params.add(new Param(5, ParamType.INPARAM.getType(), stirId));
        params.add(new Param(6, ParamType.INPARAM.getType(), taskId));
        params.add(new Param(7, ParamType.INPARAM.getType(), stgId));
        params.add(new Param(8, ParamType.INPARAM.getType(), empname));
        params.add(new Param(9, ParamType.INPARAM.getType(), placing));
        params.add(new Param(10, ParamType.INPARAM.getType(), eppName));
        params.add(new Param(11, ParamType.INPARAM.getType(), builderShorName));
        params.add(new Param(12, ParamType.INPARAM.getType(), openTime));
        params.add(new Param(13, ParamType.INPARAM.getType(), overTime));

        procedure.init("sp_sp_Query_V_Account_PTProduceConsumeList3", params);
        try {
            procedure.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return procedure.getResultArray();
    }


    /**
     * 司机排班
     *
     * @param currPage     当前页
     * @param pageSize     页长度
     * @param personalCode 司机名称
     * @param vehicleID    车号
     * @return jsonarray
     */
    @Override
    public JSONArray spQueryVMWorkClassLog(String compid, Integer currPage, Integer pageSize, String personalCode, String vehicleID, String beginTime, String endTime, String opid, Integer workclass) {
        List<Param> params = new ArrayList<>();
        params.add(new Param(1, ParamType.INPARAM.getType(), compid));
        params.add(new Param(2, ParamType.INPARAM.getType(), currPage));
        params.add(new Param(3, ParamType.INPARAM.getType(), pageSize));
        params.add(new Param(4, ParamType.INPARAM.getType(), personalCode));
        params.add(new Param(5, ParamType.INPARAM.getType(), vehicleID));
        params.add(new Param(6, ParamType.INPARAM.getType(), beginTime));
        params.add(new Param(7, ParamType.INPARAM.getType(), endTime));
        params.add(new Param(8, ParamType.INPARAM.getType(), opid));
        params.add(new Param(9, ParamType.INPARAM.getType(), workclass));
        procedure.init("sp_Query_VM_WorkClassLog", params);
        try {
            procedure.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return procedure.getResultArray();
    }


    /**
     * 查询企业名称
     *
     * @param currPage 当前页
     * @param pageSize 页长度
     * @param var1
     * @param var2
     * @return
     */
    @Override
    public JSONArray spQueryUsercomp(Integer currPage, Integer pageSize, String var1, String var2) {
        List<Param> params = new ArrayList<>();
        params.add(new Param(1, ParamType.INPARAM.getType(), currPage));
        params.add(new Param(2, ParamType.INPARAM.getType(), pageSize));
        params.add(new Param(3, ParamType.INPARAM.getType(), var1));
        params.add(new Param(4, ParamType.INPARAM.getType(), var2));


        procedure.init("sp_Query_User_comp", params);
        try {
            procedure.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return procedure.getResultArray();
    }


}
