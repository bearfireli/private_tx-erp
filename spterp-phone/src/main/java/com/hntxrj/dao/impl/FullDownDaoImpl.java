package com.hntxrj.dao.impl;

import com.alibaba.fastjson.JSONArray;
import com.hntxrj.dao.FullDownDao;
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
 * 功能:  下拉加载Dao层实现类
 *
 * @Auther 李帅
 * @Data 2017-08-14.上午 11:28
 */
@Component
@Scope("prototype")
public class FullDownDaoImpl implements FullDownDao {

    private final Procedure procedure;

    @Autowired
    public FullDownDaoImpl(Procedure procedure) {
        this.procedure = procedure;
    }


    /**
     * 加载下拉详细内容
     *
     * @param id 1 浇筑方式 2  水泥品种 3  石料要求 4  泵送否 5 塌落度 6  任务单类型 7 企业信息下拉
     * @return
     */
    @Override
    public JSONArray loadFullDown(int id) {
        List<Param> param = new ArrayList<Param>();
        param.add(new Param(1, ParamType.INPARAM.getType(), id));

        procedure.init("sp_Query_FullDown", param);
        try {
            procedure.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return procedure.getResultArray();
    }

    /**
     * 加载砼标号下拉
     *
     * @param ContractUID   合同UID
     * @param CContractCode 子合同编号
     * @param pageBean      分页信息
     * @return
     */
    @Override
    public JSONArray loadStgId(String ContractUID, String CContractCode, PageBean pageBean) {
        List<Param> param = new ArrayList<Param>();
        param.add(new Param(1, ParamType.INPARAM.getType(), ContractUID));
        param.add(new Param(2, ParamType.INPARAM.getType(), CContractCode));
        param.add(new Param(3, ParamType.INPARAM.getType(), pageBean.getPageCurr()));
        param.add(new Param(4, ParamType.INPARAM.getType(), pageBean.getPageSize()));


        procedure.init("sp_Query_V_SM_Stgid", param);
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
     * 下拉查询带参数带分页
     *
     * @param type     类型
     * @param param    参数
     * @param pageBean 分页
     * @return
     */
    @Override
    public JSONArray getFullDown(Integer type, String param, PageBean pageBean, String compid) {

        List<Param> params = new ArrayList<Param>();
        /* table 表名, showField 显示的列 , whereField where条件   */
        String table = null, showField = null, whereField = null;
        /*  是否分组 0 不分组   1 分组 */
        Integer ifGroup = 0; //默认不分组
        if (type == 1) { //车号
            table = "MP_StockIn";
            showField = "VehicleID";
            whereField = "VehicleID";
            ifGroup = 1;
        } else if (type == 2) {  //司机
            table = "VM_PersonalInfo";
            showField = "PersonalCode,PersonalName";
            whereField = "PersonalName";
        } else if (type == 3) {  //站别
            table = "User_comp";
            showField = "compid,shortname";
            whereField = "shortname";
        } else if (type == 4) { //搅拌楼
            table = "DD_StirInfoSet";
            showField = "StirId,StirName";
            whereField = "StirName";
        } else if (type == 5) {  //调度员
            table = "User_employee";
            showField = "OpId,empname";
            whereField = "OpId";
        }
        params.add(new Param(1, ParamType.INPARAM.getType(), table));
        params.add(new Param(2, ParamType.INPARAM.getType(), showField));
        params.add(new Param(3, ParamType.INPARAM.getType(), whereField));
        params.add(new Param(4, ParamType.INPARAM.getType(), ifGroup));
        params.add(new Param(5, ParamType.INPARAM.getType(), param));
        params.add(new Param(6, ParamType.INPARAM.getType(), pageBean.getPageCurr()));
        params.add(new Param(7, ParamType.INPARAM.getType(), pageBean.getPageSize()));
        params.add(new Param(8, ParamType.INPARAM.getType(), compid));

        procedure.init("sp_Query_DropDown", params);

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
