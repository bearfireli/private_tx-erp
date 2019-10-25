package com.hntxrj.dao.impl;

import com.alibaba.fastjson.JSONArray;
import com.hntxrj.dao.BuilderDao;
import com.hntxrj.entity.PageBean;
import com.hntxrj.util.jdbc.procedure.Param;
import com.hntxrj.util.jdbc.procedure.Procedure;
import com.hntxrj.util.jdbc.enums.ParamType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * 功能:   施工单位Dao
 *
 * @Auther 李帅
 * @Data 2017-08-11.上午 11:09
 */
@Component
@Scope("prototype")
public class BuilderDaoImpl implements BuilderDao {

    @Autowired
    private Procedure procedure;

    /**
     * 加载详情列表
     *
     * @param builderName 工程名
     * @param pageBean    页码相关实体
     * @return
     */
    @Override
    public JSONArray getBuilderList(String builderName, PageBean pageBean, String compid) {
        List<Param> params = new ArrayList<Param>();
        params.add(new Param(1, ParamType.INPARAM.getType(), 1));
        params.add(new Param(2, ParamType.INPARAM.getType(), pageBean.getPageCurr()));
        params.add(new Param(3, ParamType.INPARAM.getType(), pageBean.getPageSize()));
        params.add(new Param(5, ParamType.INPARAM.getType(), builderName));
        List<Param> outParam = new ArrayList<Param>();
        //出参
        params.add(new Param(4, ParamType.OUTPARAM.getType(), ""));
        params.add(new Param(6, ParamType.INPARAM.getType(), compid));

        procedure.init("sp_Query_SM_BuilderInfo", params, outParam);
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
     * 添加修改删除  施工单位
     *
     * @param Mark               操作标识 0 插入 1 更新 2 删除
     * @param compid             企业ID
     * @param OpId               操作员ID
     * @param BuilderCode        施工单位代号
     * @param BuilderName_1      施工单位名
     * @param BuilderShortName_2 施工单位名简称
     * @param Address_3          地址
     * @param CreateTime_4       创建时间
     * @param Corporation_5      法人
     * @param Fax_6              传真
     * @param LinkTel_7          联系电话
     * @param RecStatus_8        记录状态(有效)   0未启用 1启用(0无效1有效)
     * @return
     */
    @Override
    public JSONArray insertUpDel_SM_BUILDERINFO(Integer Mark, String compid, String OpId, String BuilderCode, String BuilderName_1, String BuilderShortName_2, String Address_3, Timestamp CreateTime_4,
                                                String Corporation_5, String Fax_6, String LinkTel_7, byte RecStatus_8) {
        List<Param> param = new ArrayList<Param>();
        param.add(new Param(1, ParamType.INPARAM.getType(), Mark));
        param.add(new Param(2, ParamType.INPARAM.getType(), compid));
        param.add(new Param(3, ParamType.INPARAM.getType(), OpId));
        param.add(new Param(4, ParamType.INPARAM.getType(), BuilderCode));
        param.add(new Param(5, ParamType.INPARAM.getType(), BuilderName_1));
        param.add(new Param(6, ParamType.INPARAM.getType(), BuilderShortName_2));
        param.add(new Param(7, ParamType.INPARAM.getType(), Address_3));
        param.add(new Param(8, ParamType.INPARAM.getType(), CreateTime_4));
        param.add(new Param(9, ParamType.INPARAM.getType(), Corporation_5));
        param.add(new Param(10, ParamType.INPARAM.getType(), Fax_6));
        param.add(new Param(11, ParamType.INPARAM.getType(), LinkTel_7));
        param.add(new Param(12, ParamType.INPARAM.getType(), RecStatus_8));
        param.add(new Param(13, ParamType.OUTPARAM.getType(), ""));

        procedure.init("sp_insertUpDel_SM_BUILDERINFO", param);
        try {
            procedure.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return procedure.getResultArray();
    }
}
