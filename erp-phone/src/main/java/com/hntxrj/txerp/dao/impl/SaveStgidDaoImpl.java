package com.hntxrj.txerp.dao.impl;

import com.alibaba.fastjson.JSONArray;
import com.hntxrj.txerp.dao.SaveStgidDao;
import com.hntxrj.txerp.util.jdbc.procedure.Param;
import com.hntxrj.txerp.util.jdbc.procedure.Procedure;
import com.hntxrj.txerp.util.jdbc.enums.ParamType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Scope("prototype")
public class SaveStgidDaoImpl implements SaveStgidDao {


    private final Procedure procedure;

    @Autowired
    public SaveStgidDaoImpl(Procedure procedure) {
        this.procedure = procedure;
    }


    @Override
    public JSONArray sp_insertUpDel_SM_StgidInfoPrice(String mark, String compid, String stgid, String performmonth, Double notpumpprice, Double pumpprice, Double towercraneprice, String opid) {
        List<Param> params = new ArrayList<Param>();
        params.add(new Param(1, ParamType.INPARAM.getType(), mark));
        params.add(new Param(2, ParamType.INPARAM.getType(), compid));
        params.add(new Param(3, ParamType.INPARAM.getType(), stgid));
        params.add(new Param(4, ParamType.INPARAM.getType(), performmonth));
        params.add(new Param(5, ParamType.INPARAM.getType(), notpumpprice));
        params.add(new Param(6, ParamType.INPARAM.getType(), pumpprice));
        params.add(new Param(7, ParamType.INPARAM.getType(), towercraneprice));
        params.add(new Param(8, ParamType.INPARAM.getType(), opid));

        List<Param> outParam = new ArrayList<Param>();
        params.add(new Param(9, ParamType.OUTPARAM.getType(), ""));//出参

        procedure.init("sp_insertUpDel_SM_StgidInfoPrice", params, outParam);
        try {
            procedure.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return procedure.getResultArray();
    }


    /**
     * 砼标号管理     sp_insertUpDel_SM_GradePriceInfo
     *
     * @param mark            0 插入 1 更新 2 删除
     * @param compid          企业
     * @param id              无
     * @param stgid           砼标号
     * @param grade           等级
     * @param pumpprice       泵送价格
     * @param notpumpprice    非泵送价格
     * @param pricedifference 差价
     * @param isdefault       是否默认
     * @param towercraneprice 塔吊价格
     * @param recstatus       状态
     * @param opid            用户
     * @return json
     */
    @Override
    public JSONArray sp_insertUpDel_SM_GradePriceInfo(String mark, String compid, String id, String stgid, String grade,
                                                      Double pumpprice, Double notpumpprice, Double pricedifference, Byte isdefault, Double towercraneprice, Byte recstatus, String opid) {
        List<Param> params = new ArrayList<Param>();
        params.add(new Param(1, ParamType.INPARAM.getType(), mark));
        params.add(new Param(2, ParamType.INPARAM.getType(), compid));
        params.add(new Param(3, ParamType.INPARAM.getType(), id));
        params.add(new Param(4, ParamType.INPARAM.getType(), stgid));
        params.add(new Param(5, ParamType.INPARAM.getType(), grade));
        params.add(new Param(6, ParamType.INPARAM.getType(), pumpprice));
        params.add(new Param(7, ParamType.INPARAM.getType(), notpumpprice));
        params.add(new Param(8, ParamType.INPARAM.getType(), pricedifference));
        params.add(new Param(9, ParamType.INPARAM.getType(), isdefault));
        params.add(new Param(10, ParamType.INPARAM.getType(), towercraneprice));
        params.add(new Param(11, ParamType.INPARAM.getType(), recstatus));
        params.add(new Param(12, ParamType.INPARAM.getType(), null));

//
//        List<Param> outParam = new ArrayList<Param>();
//        outParam.add( new Param(1,ParamType.OUTPARAM.getType(),null));//出参

        procedure.init("sp_insertUpDel_SM_GradePriceInfo", params);
        try {
            procedure.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return procedure.getResultArray();
    }


    /**
     * 查看砼标号
     *
     * @param opid     用户
     * @param compid   企业
     * @param stgid    砼标号
     * @param grade    强度等级
     * @param pagesize 页长度
     * @param currpage 当前页
     * @return json
     */
    @Override
    public JSONArray sp_quancha_SM_GradePriceInfo(String opid, String compid, String stgid, String grade, Integer pagesize, Integer currpage) {
        List<Param> params = new ArrayList<Param>();
        params.add(new Param(1, ParamType.INPARAM.getType(), opid));
        params.add(new Param(2, ParamType.INPARAM.getType(), compid));
        params.add(new Param(3, ParamType.INPARAM.getType(), stgid));
        params.add(new Param(4, ParamType.INPARAM.getType(), grade));
        params.add(new Param(5, ParamType.INPARAM.getType(), pagesize));
        params.add(new Param(6, ParamType.INPARAM.getType(), currpage));


        procedure.init("sp_quancha_SM_GradePriceInfo", params);
        try {
            procedure.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return procedure.getResultArray();
    }
}
