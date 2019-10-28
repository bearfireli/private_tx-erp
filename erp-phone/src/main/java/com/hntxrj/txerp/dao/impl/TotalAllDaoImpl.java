package com.hntxrj.txerp.dao.impl;

import com.alibaba.fastjson.JSONArray;
import com.hntxrj.txerp.dao.TotalAllDao;
import com.hntxrj.txerp.util.jdbc.procedure.Param;
import com.hntxrj.txerp.util.jdbc.procedure.Procedure;
import com.hntxrj.txerp.util.jdbc.enums.ParamType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 功能:  统计查询Dao实现层
 *
 * @Auther 李帅
 * @Data 2017-08-28.下午 8:05
 */
@Component
@Scope("prototype")
public class TotalAllDaoImpl implements TotalAllDao {

    @Autowired
    public void setProcedure(Procedure procedure) {
        this.procedure = procedure;
    }

    private Procedure procedure;

    /**
     * 统计查询
     *
     * @param mark 标记
     * @return
     */
    @Override
    public JSONArray totalAll(Integer mark, String compid) {
        List<Param> comeParam = new ArrayList<>();
        comeParam.add(new Param(1, ParamType.INPARAM.getType(), mark));
        comeParam.add(new Param(2, ParamType.INPARAM.getType(), compid));
        procedure.init("sp_Query_All_Total", comeParam);
        try {
            procedure.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return procedure.getResultArray();
    }


}
