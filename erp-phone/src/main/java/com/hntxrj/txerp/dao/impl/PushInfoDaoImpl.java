package com.hntxrj.txerp.dao.impl;

import com.alibaba.fastjson.JSONArray;
import com.hntxrj.txerp.dao.PushInfoDao;
import com.hntxrj.txerp.util.jdbc.enums.ParamType;
import com.hntxrj.txerp.util.jdbc.procedure.Param;
import com.hntxrj.txerp.util.jdbc.procedure.Procedure;
import com.hntxrj.txerp.util.jdbc.sql.SqlBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * 功能:   推送类型Dao实现类
 *
 * @Auther 陈世强
 */
@Repository
@Scope("prototype")
public class PushInfoDaoImpl implements PushInfoDao {

    private final SqlBuilder sqlBuilder;
    private final Procedure procedure;

    @Autowired
    public PushInfoDaoImpl(SqlBuilder sqlBuilder,Procedure procedure) {

        this.sqlBuilder = sqlBuilder;
        this.procedure=procedure;
    }



    /**
     * 获取推送类型
     *
     * @return
     */


    @Override
    public void saveRecipient(String name, String compid, int typeId) {
        List<Param> params = new ArrayList<>();
        params.add(new Param(1, ParamType.INPARAM.getType(), name));
        params.add(new Param(2, ParamType.INPARAM.getType(), compid));
        params.add(new Param(3, ParamType.INPARAM.getType(), typeId));

        procedure.init("Recipient", params);

        try {
            procedure.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        JSONArray result = procedure.getResultArray();
        System.out.println(result);




    }


}
