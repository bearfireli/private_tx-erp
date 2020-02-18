package com.hntxrj.txerp.server.impl;

import com.alibaba.fastjson.JSONArray;
import com.hntxrj.txerp.dao.LMTaskDao;
import com.hntxrj.txerp.server.LMTaskServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;


@Service
@Scope("prototype")

public class LMTaskServerImpl implements LMTaskServer {

    private final LMTaskDao lmTaskDao;

    @Autowired
    public LMTaskServerImpl(LMTaskDao lmTaskDao) {
        this.lmTaskDao = lmTaskDao;
    }

    //修改配比
    @Override
    public JSONArray upDateTaskTheoryFormula(String taskid, String compid, String formulacode, String opid, String stgid, String whisktime, String slump, Double wr1, Double wr2, Double wr3, Double wr4, Double wr5, Double wr6, Double wr7, Double wr8, String mat1, String mat2, String mat3, String mat4, String mat5, String mat6, String mat7, String mat8, String mat9, String mat10, String mat11, String mat12, String mat13, String mat14, String mat15, String mat16, String mat17, String mat18, String mat19, String mat20, String mat21, String mat22, String mat23, String mat24, String mat25, String mat26, String mat27, Double matv1, Double matv2, Double matv3, Double matv4, Double matv5, Double matv6, Double matv7, Double matv8, Double matv9, Double matv10, Double matv11, Double matv12, Double matv13, Double matv14, Double matv15, Double matv16, Double matv17, Double matv18, Double matv19, Double matv20, Double matv21, Double matv22, Double matv23, Double matv24, Double matv25, Double matv26, Double matv27, Double totalnum, String stirid) {
        return lmTaskDao.sp_insert_LM_TaskTheoryFormula(taskid, compid, formulacode, opid, stgid, whisktime, slump, wr1, wr2, wr3, wr4, wr5, wr6, wr7, wr8, mat1, mat2, mat3, mat4, mat5, mat6, mat7, mat8, mat9, mat10, mat11, mat12, mat13, mat14, mat15, mat16, mat17, mat18, mat19, mat20, mat21, mat22, mat23, mat24, mat25, mat26, mat27, matv1, matv2, matv3, matv4, matv5, matv6, matv7, matv8, matv9, matv10, matv11, matv12, matv13, matv14, matv15, matv16, matv17, matv18, matv19, matv20, matv21, matv22, matv23, matv24, matv25, matv26, matv27, totalnum, stirid);
    }
}
