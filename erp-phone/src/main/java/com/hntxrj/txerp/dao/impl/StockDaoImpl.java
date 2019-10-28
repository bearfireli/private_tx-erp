package com.hntxrj.txerp.dao.impl;

import com.alibaba.fastjson.JSONArray;
import com.hntxrj.txerp.dao.StockDao;
import com.hntxrj.txerp.util.jdbc.procedure.Param;
import com.hntxrj.txerp.util.jdbc.procedure.Procedure;
import com.hntxrj.txerp.util.jdbc.enums.ParamType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 功能: 库存统计Dao实现层
 *
 * @Auther 李帅
 * @Data 2017-08-17.上午 10:19
 */
@Component
@Scope("prototype")
public class StockDaoImpl implements StockDao {


    private final Procedure procedure;

    @Autowired
    public StockDaoImpl(Procedure procedure) {
        this.procedure = procedure;
    }

    /**
     * 实时库存统计
     *
     * @param stirId 搅拌楼Id号
     * @return
     */
    @Override
    public JSONArray realStock(String stirId, String compid, String opid) {
        List<Param> paramList = new ArrayList<>();
        paramList.add(new Param(1, ParamType.INPARAM.getType(), compid));
        paramList.add(new Param(2, ParamType.INPARAM.getType(), stirId));

        procedure.init("sp_Query_Sto_Curqty", paramList);

        try {
            procedure.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return procedure.getResultArray();
    }


    /**
     * 获取库位信息
     *
     * @param compid 企业ID
     * @return {@link JSONArray}
     */
    @Override
    public JSONArray getStock(String compid) {
        List<Param> list = new ArrayList<>();
        list.add(new Param(1, ParamType.INPARAM.getType(), compid));
        procedure.init("sp_Query_loadStock", list);
        try {
            procedure.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return procedure.getResultArray();
    }


}
