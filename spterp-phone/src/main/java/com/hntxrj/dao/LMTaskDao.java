package com.hntxrj.dao;


import com.alibaba.fastjson.JSONArray;

public interface LMTaskDao {
    //配比修改
    JSONArray sp_insert_LM_TaskTheoryFormula(String taskid, String compid, String formulacode, String opid, String stgid, String whisktime, String slump, Double wr1, Double wr2, Double wr3, Double wr4, Double wr5, Double wr6, Double wr7, Double wr8, String mat1, String mat2, String mat3, String mat4, String mat5, String mat6, String mat7, String mat8, String mat9, String mat10, String mat11, String mat12, String mat13, String mat14, String mat15, String mat16, String mat17, String mat18, String mat19, String mat20, String mat21, String mat22, String mat23, String mat24, String mat25, String mat26, String mat27, Double matv1, Double matv2, Double matv3, Double matv4, Double matv5, Double matv6, Double matv7, Double matv8, Double matv9, Double matv10, Double matv11, Double matv12, Double matv13, Double matv14, Double matv15, Double matv16, Double matv17, Double matv18, Double matv19, Double matv20, Double matv21, Double matv22, Double matv23, Double matv24, Double matv25, Double matv26, Double matv27, Double totalnum, String stirid);
}
