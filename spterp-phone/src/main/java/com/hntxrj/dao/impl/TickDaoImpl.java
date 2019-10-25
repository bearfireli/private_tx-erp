package com.hntxrj.dao.impl;

import com.alibaba.fastjson.JSONArray;
import com.hntxrj.dao.TickDao;
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
public class TickDaoImpl implements TickDao {

    private final Procedure procedure;

    @Autowired
    public TickDaoImpl(Procedure procedure) {
        this.procedure = procedure;
    }

    @Override
    public JSONArray getTicketSqlServer() {
        List<Param> comeParam = new ArrayList<>();
        comeParam.add(new Param(1, ParamType.INPARAM.getType(), 0));
        comeParam.add(new Param(2, ParamType.INPARAM.getType(), null));
        comeParam.add(new Param(3, ParamType.INPARAM.getType(), null));

        procedure.init("sp_cf_wxconf", comeParam);

        try {
            procedure.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return procedure.getResultArray();
    }

    @Override
    public JSONArray tokenticket(String token, String ticket) {
        List<Param> comeParam = new ArrayList<>();
        comeParam.add(new Param(1, ParamType.INPARAM.getType(), 1));
        comeParam.add(new Param(2, ParamType.INPARAM.getType(), token));
        comeParam.add(new Param(3, ParamType.INPARAM.getType(), ticket));

        procedure.init("sp_cf_wxconf", comeParam);

        try {
            procedure.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return procedure.getResultArray();
    }
}
