package com.hntxrj.txerp.dao.impl;

import com.alibaba.fastjson.JSONArray;
import com.hntxrj.txerp.dao.EppDao;
import com.hntxrj.txerp.entity.PageBean;
import com.hntxrj.txerp.util.jdbc.procedure.Param;
import com.hntxrj.txerp.util.jdbc.procedure.Procedure;
import com.hntxrj.txerp.util.jdbc.enums.ParamType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 功能:  工程操作Dao
 *
 * @Auther 李帅
 * @Data 2017-08-11.上午 10:50
 */
@Component
@Scope("prototype")
public class EppDaoImpl implements EppDao {

    @Autowired
    private Procedure procedure;

    /**
     * 加载详情列表
     *
     * @param eppName  工程名
     * @param pageBean 页码相关实体
     * @return
     */
    @Override
    public JSONArray getEppList(String eppName, PageBean pageBean, String compid) {

        List<Param> params = new ArrayList<Param>();
        params.add(new Param(1, ParamType.INPARAM.getType(), 1));
        params.add(new Param(2, ParamType.INPARAM.getType(), pageBean.getPageCurr()));
        params.add(new Param(3, ParamType.INPARAM.getType(), pageBean.getPageSize()));
        params.add(new Param(5, ParamType.INPARAM.getType(), eppName));
        params.add(new Param(6, ParamType.INPARAM.getType(), compid));
        List<Param> outParam = new ArrayList<Param>();
        params.add(new Param(4, ParamType.OUTPARAM.getType(), ""));//出参

        procedure.init("sp_Query_SM_EPPInfo", params, outParam);
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
     * 加载 浇筑部位
     *
     * @param eppCode  工程代号
     * @param pageBean 分页
     * @return
     */
    @Override
    public JSONArray getEppPlacing(String eppCode, String placing, PageBean pageBean, String compid) {
        List<Param> param = new ArrayList<Param>();
        param.add(new Param(1, ParamType.INPARAM.getType(), eppCode));
        param.add(new Param(2, ParamType.INPARAM.getType(), placing));
        param.add(new Param(3, ParamType.INPARAM.getType(), pageBean.getPageCurr()));
        param.add(new Param(4, ParamType.INPARAM.getType(), pageBean.getPageSize()));
        param.add(new Param(5, ParamType.INPARAM.getType(), compid));

        procedure.init("sp_Query_PT_TaskPlan_Placing", param);
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
}
