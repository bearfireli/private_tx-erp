package com.hntxrj.txerp.dao.impl;

import com.alibaba.fastjson.JSONArray;
import com.hntxrj.txerp.dao.FileUPDao;
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
public class FileUPDaoImpl implements FileUPDao {

    private final Procedure procedure;

    @Autowired
    public FileUPDaoImpl(Procedure procedure) {
        this.procedure = procedure;
    }


    //小票签收上传图片
    @Override
    public JSONArray sp_insert_fileImage(String compid, String opid, String base64, String id, Double qiannum, String mark) {
        List<Param> param = new ArrayList<Param>();
        param.add(new Param(1, ParamType.INPARAM.getType(), compid));
        param.add(new Param(2, ParamType.INPARAM.getType(), opid));
        param.add(new Param(3, ParamType.INPARAM.getType(), base64));
        param.add(new Param(4, ParamType.INPARAM.getType(), id));
        param.add(new Param(5, ParamType.INPARAM.getType(), qiannum));
        param.add(new Param(6, ParamType.INPARAM.getType(), mark));


        procedure.init("sp_insert_fileImage", param);
        try {
            procedure.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }


        return procedure.getResultArray();
    }

    //小票签收查看图片
    @Override
    public JSONArray sp_filedown(String id, String compid, String opid) {
        List<Param> param = new ArrayList<Param>();
        param.add(new Param(1, ParamType.INPARAM.getType(), id));
        param.add(new Param(2, ParamType.INPARAM.getType(), compid));
        param.add(new Param(3, ParamType.INPARAM.getType(), opid));


        procedure.init("sp_filedown", param);
        try {
            procedure.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }


        return procedure.getResultArray();
    }
}
