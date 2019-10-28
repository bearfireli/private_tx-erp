package com.hntxrj.txerp.dao.impl;

import com.alibaba.fastjson.JSONArray;
import com.hntxrj.txerp.dao.StockInDao;
import com.hntxrj.txerp.dao.UserCompDao;
import com.hntxrj.txerp.util.jdbc.procedure.Param;
import com.hntxrj.txerp.util.jdbc.procedure.Procedure;
import com.hntxrj.txerp.util.jdbc.enums.ParamType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Component
@Scope("prototype")
public class StockInDaoImpl implements StockInDao {

    private final Procedure procedure;

    @Autowired
    public StockInDaoImpl(Procedure procedure) {
        this.procedure = procedure;
    }

    @Resource
    private UserCompDao userCompDao;
    @Value("${app.spterp.erptype}")
    public Integer erpType;

    /**
     * 材料过磅统计
     *
     * @param mark
     * @param sup_linkMan 联系人
     * @param mu          母公司
     * @param empName     过磅员
     * @param vehicleID   车号
     * @param for_name    运输商
     * @param matNS       入库库位
     * @param sup_name    供货商
     * @param stI_status  记录状态
     * @param beginTime   开始时间
     * @param endTime     结束时间
     * @param compid      当前登录用户的企业
     * @param opid        登录用户
     * @param currPage    当前页
     * @param pageSize    页长度
     * @return json
     */
    @Override
    public JSONArray sp_V_MP_StockInQuerysql(String mark, String sup_linkMan, String mu, String empName, String vehicleID, String for_name, String sto_name, String matNS, String sup_name, Boolean stI_status, String beginTime, String endTime, String compid, String opid, Integer currPage, Integer pageSize) {
        List<Param> comeParam = new ArrayList<>();
        comeParam.add(new Param(1, ParamType.INPARAM.getType(), mark));
        comeParam.add(new Param(2, ParamType.INPARAM.getType(), sup_linkMan));
        comeParam.add(new Param(3, ParamType.INPARAM.getType(), mu));
        comeParam.add(new Param(4, ParamType.INPARAM.getType(), empName));
        comeParam.add(new Param(5, ParamType.INPARAM.getType(), vehicleID));
        comeParam.add(new Param(6, ParamType.INPARAM.getType(), for_name));
        comeParam.add(new Param(7, ParamType.INPARAM.getType(), sto_name));
        comeParam.add(new Param(8, ParamType.INPARAM.getType(), matNS));
        comeParam.add(new Param(9, ParamType.INPARAM.getType(), sup_name));
        comeParam.add(new Param(10, ParamType.INPARAM.getType(), stI_status));
        comeParam.add(new Param(11, ParamType.INPARAM.getType(), beginTime));
        comeParam.add(new Param(12, ParamType.OUTPARAM.getType(), endTime));
        comeParam.add(new Param(13, ParamType.OUTPARAM.getType(), compid));
        comeParam.add(new Param(14, ParamType.OUTPARAM.getType(), opid));
        comeParam.add(new Param(15, ParamType.OUTPARAM.getType(), currPage));
        comeParam.add(new Param(16, ParamType.OUTPARAM.getType(), pageSize));
        //TODO:第三方原材料过磅统计
        Integer currErpType = userCompDao.findUserCompByCompid(compid).getErpType();
        if (currErpType != null && currErpType == erpType) {
            procedure.init("sp_V_MP_StockInQuerysql_Thr", comeParam);
        } else {
            procedure.init("sp_V_MP_StockInQuerysql", comeParam);
        }
        try {
            procedure.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return procedure.getResultArray();
    }

    /**
     * srqq 材料过磅  下拉
     *
     * @param id
     * @param sqlwhere
     * @param compid   1 联系人下拉 MP_Supplier   2  过磅员查询 User_employee 3 运输商查询      这个表  MP_Forwarder 4 入库库位  MP_Stock 5 材料名称    V_MatCodeToName 6 供货商名称 MP_Supplier
     * @param opid     查询条件   @return json
     */
    @Override
    public JSONArray sp_query_downsrq(String id, String sqlwhere, String compid, String opid, Integer PageSize, Integer currPage) {
        List<Param> comeParam = new ArrayList<>();
        comeParam.add(new Param(1, ParamType.INPARAM.getType(), id));
        comeParam.add(new Param(2, ParamType.INPARAM.getType(), sqlwhere));
        comeParam.add(new Param(3, ParamType.INPARAM.getType(), compid));
        comeParam.add(new Param(4, ParamType.INPARAM.getType(), opid));
        comeParam.add(new Param(5, ParamType.INPARAM.getType(), PageSize));
        comeParam.add(new Param(6, ParamType.INPARAM.getType(), currPage));

        procedure.init("sp_query_downsrq", comeParam);

        try {
            procedure.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return procedure.getResultArray();
    }


    /**
     * srqq  原材料过磅查询详情
     *
     * @param mark       1 list表     2 详情
     * @param beginTime  开始时间
     * @param endTime    结束时间
     * @param compid     当前登录用户的企业
     * @param opid       登录用户
     * @param currPage
     * @param pageSize
     * @param sup_name   供货商
     * @param for_name   运输商
     * @param empName    过磅员
     * @param sto_name   入库库位
     * @param matNS      材料名称
     * @param vehicleID  车号
     * @param saletype   业务类别
     * @param stI_status 记录状态
     * @return json
     */
    @Override
    public JSONArray sp_V_MP_StockInQuerylistsrq(String mark, String beginTime, String endTime, String compid, String opid, Integer currPage,
                                                 Integer pageSize, String sup_name, String for_name, String empName,
                                                 String sto_name, String matNS, String vehicleID, Integer saletype, Integer stI_status, String StICode) {
        List<Param> comeParam = new ArrayList<>();
        comeParam.add(new Param(1, ParamType.INPARAM.getType(), mark));
        comeParam.add(new Param(2, ParamType.INPARAM.getType(), beginTime));
        comeParam.add(new Param(3, ParamType.INPARAM.getType(), endTime));
        comeParam.add(new Param(4, ParamType.INPARAM.getType(), compid));
        comeParam.add(new Param(5, ParamType.INPARAM.getType(), opid));
        comeParam.add(new Param(6, ParamType.INPARAM.getType(), currPage));
        comeParam.add(new Param(7, ParamType.INPARAM.getType(), pageSize));
        comeParam.add(new Param(8, ParamType.INPARAM.getType(), sup_name));
        comeParam.add(new Param(9, ParamType.INPARAM.getType(), for_name));
        comeParam.add(new Param(10, ParamType.INPARAM.getType(), empName));
        comeParam.add(new Param(11, ParamType.INPARAM.getType(), sto_name));
        comeParam.add(new Param(12, ParamType.INPARAM.getType(), matNS));
        comeParam.add(new Param(13, ParamType.INPARAM.getType(), vehicleID));
        comeParam.add(new Param(14, ParamType.INPARAM.getType(), saletype));
        comeParam.add(new Param(15, ParamType.INPARAM.getType(), stI_status));
        comeParam.add(new Param(16, ParamType.INPARAM.getType(), StICode));
        //TODO:第三方原材料过磅查询
        Integer currErpType = userCompDao.findUserCompByCompid(compid).getErpType();
        if (currErpType != null && currErpType == erpType) {
            procedure.init("sp_V_MP_StockInQuerylistsrq_THr", comeParam);
        } else {
            procedure.init("sp_V_MP_StockInQuerylistsrq", comeParam);
        }

        try {
            procedure.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        JSONArray result = procedure.getResultArray();
        return result;
    }


    /**
     * 原材料统计汇总
     *
     * @param mark      1原材林明细汇总  2原材林汇总统计  3 材料入库汇总   4 实时库存 查询
     * @param beginTime
     * @param endTime
     * @param compid
     * @param opid
     * @param currPage
     * @param pageSize
     * @param matname   材料名称
     * @param sup_name  供货商名称
     * @param vehicleID 车号
     * @return json
     */
    @Override
    public JSONArray sp_V_MP_StockInQueryManNsrq(String mark, String beginTime, String endTime, String compid, String opid,
                                                 Integer currPage, Integer pageSize, String matname, String sup_name, String vehicleID) {
        List<Param> comeParam = new ArrayList<>();
        comeParam.add(new Param(1, ParamType.INPARAM.getType(), mark));
        comeParam.add(new Param(2, ParamType.INPARAM.getType(), compid));
        comeParam.add(new Param(3, ParamType.INPARAM.getType(), opid));
        comeParam.add(new Param(4, ParamType.INPARAM.getType(), beginTime));
        comeParam.add(new Param(5, ParamType.INPARAM.getType(), endTime));
        comeParam.add(new Param(6, ParamType.INPARAM.getType(), currPage));
        comeParam.add(new Param(7, ParamType.INPARAM.getType(), pageSize));
        comeParam.add(new Param(8, ParamType.INPARAM.getType(), matname));
        comeParam.add(new Param(9, ParamType.INPARAM.getType(), sup_name));
        comeParam.add(new Param(10, ParamType.INPARAM.getType(), vehicleID));
        //TODO:第三方原材料统计汇总
        Integer currErpType = userCompDao.findUserCompByCompid(compid).getErpType();
        if (currErpType != null && currErpType == erpType) {
            procedure.init("sp_V_MP_StockInQueryManNsrq_Thr", comeParam);
        } else {
            procedure.init("sp_V_MP_StockInQueryManNsrq", comeParam);
        }
        try {
            procedure.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return procedure.getResultArray();
    }

    /**
     * srqq 搅拌车过磅查询
     *
     * @param mark       1列表
     * @param beginTime  开始时间
     * @param endTime    结束时间
     * @param compid     企业
     * @param opid       用户
     * @param currPage   当前页
     * @param pageSize   页长度
     * @param empname    过磅员
     * @param weightType 过磅类别
     * @param vehicleID  车号
     * @param eppname    业务员
     * @return
     */
    @Override
    public JSONArray sp_Query_VM_VehicleWeightListsrq(Integer mark, String beginTime, String endTime, String compid, String opid, Integer currPage, Integer pageSize, String empname, Integer weightType, String vehicleID, String eppname) {
        List<Param> comeParam = new ArrayList<>();
        comeParam.add(new Param(1, ParamType.INPARAM.getType(), mark));
        comeParam.add(new Param(2, ParamType.INPARAM.getType(), compid));
        comeParam.add(new Param(3, ParamType.INPARAM.getType(), opid));
        comeParam.add(new Param(4, ParamType.INPARAM.getType(), beginTime));
        comeParam.add(new Param(5, ParamType.INPARAM.getType(), endTime));
        comeParam.add(new Param(6, ParamType.INPARAM.getType(), currPage));
        comeParam.add(new Param(7, ParamType.INPARAM.getType(), pageSize));
        comeParam.add(new Param(8, ParamType.INPARAM.getType(), empname));
        comeParam.add(new Param(9, ParamType.INPARAM.getType(), weightType));
        comeParam.add(new Param(10, ParamType.INPARAM.getType(), vehicleID));
        comeParam.add(new Param(11, ParamType.INPARAM.getType(), eppname));


        procedure.init("sp_Query_VM_VehicleWeightListsrq", comeParam);

        try {
            procedure.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return procedure.getResultArray();
    }
}
