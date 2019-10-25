package com.hntxrj.dao.impl;

import com.alibaba.fastjson.JSONArray;
import com.hntxrj.dao.LMTaskDao;
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
public class LMTaskDaoImpl implements LMTaskDao {

    private final Procedure procedure;

    @Autowired
    public LMTaskDaoImpl(Procedure procedure) {
        this.procedure = procedure;
    }

    //配比修改
    @Override
    public JSONArray sp_insert_LM_TaskTheoryFormula(String taskid, String compid, String formulacode, String opid,
                                                    String stgid, String whisktime, String slump, Double wr1,
                                                    Double wr2, Double wr3, Double wr4, Double wr5, Double wr6,
                                                    Double wr7, Double wr8, String mat1, String mat2, String mat3,
                                                    String mat4, String mat5, String mat6, String mat7, String mat8,
                                                    String mat9, String mat10, String mat11, String mat12,
                                                    String mat13, String mat14, String mat15, String mat16,
                                                    String mat17, String mat18, String mat19, String mat20,
                                                    String mat21, String mat22, String mat23, String mat24,
                                                    String mat25, String mat26, String mat27, Double matv1,
                                                    Double matv2, Double matv3, Double matv4, Double matv5,
                                                    Double matv6, Double matv7, Double matv8, Double matv9,
                                                    Double matv10, Double matv11, Double matv12, Double matv13,
                                                    Double matv14, Double matv15, Double matv16, Double matv17,
                                                    Double matv18, Double matv19, Double matv20, Double matv21,
                                                    Double matv22, Double matv23, Double matv24, Double matv25,
                                                    Double matv26, Double matv27, Double totalnum, String stirid) {
        List<Param> list = new ArrayList<>();
        //处理totalnum
        String totalStr = totalnum.toString();

        // 按照小数点进行分割
        String[] totalStrs = totalStr.split("\\.");
        if (totalStrs.length > 1 && totalStrs[1].length() > 2) {
            // 保留两位小数
            totalStr = totalStrs[0] + totalStrs[1].substring(0, 2);
        }
        System.out.println("formulacode:"+formulacode);

        list.add(new Param(1, ParamType.INPARAM.getType(), taskid));
        list.add(new Param(2, ParamType.INPARAM.getType(), compid));
        list.add(new Param(3, ParamType.INPARAM.getType(), formulacode));
        list.add(new Param(4, ParamType.INPARAM.getType(), opid));
        list.add(new Param(5, ParamType.INPARAM.getType(), stgid));
        list.add(new Param(6, ParamType.INPARAM.getType(), slump));
        list.add(new Param(7, ParamType.INPARAM.getType(), whisktime));
        list.add(new Param(8, ParamType.INPARAM.getType(), wr1));
        list.add(new Param(9, ParamType.INPARAM.getType(), wr2));
        list.add(new Param(10, ParamType.INPARAM.getType(), wr3));
        list.add(new Param(11, ParamType.INPARAM.getType(), wr4));
        list.add(new Param(12, ParamType.INPARAM.getType(), wr5));
        list.add(new Param(13, ParamType.INPARAM.getType(), wr6));
        list.add(new Param(14, ParamType.INPARAM.getType(), wr7));
        list.add(new Param(15, ParamType.INPARAM.getType(), wr8));
        list.add(new Param(16, ParamType.INPARAM.getType(), mat1));
        list.add(new Param(17, ParamType.INPARAM.getType(), mat2));
        list.add(new Param(18, ParamType.INPARAM.getType(), mat3));
        list.add(new Param(19, ParamType.INPARAM.getType(), mat4));
        list.add(new Param(20, ParamType.INPARAM.getType(), mat5));
        list.add(new Param(21, ParamType.INPARAM.getType(), mat6));
        list.add(new Param(22, ParamType.INPARAM.getType(), mat7));
        list.add(new Param(23, ParamType.INPARAM.getType(), mat8));
        list.add(new Param(24, ParamType.INPARAM.getType(), mat9));
        list.add(new Param(25, ParamType.INPARAM.getType(), mat10));
        list.add(new Param(26, ParamType.INPARAM.getType(), mat11));
        list.add(new Param(27, ParamType.INPARAM.getType(), mat12));
        list.add(new Param(28, ParamType.INPARAM.getType(), mat13));
        list.add(new Param(29, ParamType.INPARAM.getType(), mat14));
        list.add(new Param(30, ParamType.INPARAM.getType(), mat15));
        list.add(new Param(31, ParamType.INPARAM.getType(), mat16));
        list.add(new Param(32, ParamType.INPARAM.getType(), mat17));
        list.add(new Param(33, ParamType.INPARAM.getType(), mat18));
        list.add(new Param(34, ParamType.INPARAM.getType(), mat19));
        list.add(new Param(35, ParamType.INPARAM.getType(), mat20));
        list.add(new Param(36, ParamType.INPARAM.getType(), mat21));
        list.add(new Param(37, ParamType.INPARAM.getType(), mat22));
        list.add(new Param(38, ParamType.INPARAM.getType(), mat23));
        list.add(new Param(39, ParamType.INPARAM.getType(), mat24));
        list.add(new Param(40, ParamType.INPARAM.getType(), mat25));
        list.add(new Param(41, ParamType.INPARAM.getType(), mat26));
        list.add(new Param(42, ParamType.INPARAM.getType(), mat27));
        list.add(new Param(43, ParamType.INPARAM.getType(), matv1));
        list.add(new Param(44, ParamType.INPARAM.getType(), matv2));
        list.add(new Param(45, ParamType.INPARAM.getType(), matv3));
        list.add(new Param(46, ParamType.INPARAM.getType(), matv4));
        list.add(new Param(47, ParamType.INPARAM.getType(), matv5));
        list.add(new Param(48, ParamType.INPARAM.getType(), matv6));
        list.add(new Param(49, ParamType.INPARAM.getType(), matv7));
        list.add(new Param(50, ParamType.INPARAM.getType(), matv8));
        list.add(new Param(51, ParamType.INPARAM.getType(), matv9));
        list.add(new Param(52, ParamType.INPARAM.getType(), matv10));
        list.add(new Param(53, ParamType.INPARAM.getType(), matv11));
        list.add(new Param(54, ParamType.INPARAM.getType(), matv12));
        list.add(new Param(55, ParamType.INPARAM.getType(), matv13));
        list.add(new Param(56, ParamType.INPARAM.getType(), matv14));
        list.add(new Param(57, ParamType.INPARAM.getType(), matv15));
        list.add(new Param(58, ParamType.INPARAM.getType(), matv16));
        list.add(new Param(59, ParamType.INPARAM.getType(), matv17));
        list.add(new Param(60, ParamType.INPARAM.getType(), matv18));
        list.add(new Param(61, ParamType.INPARAM.getType(), matv19));
        list.add(new Param(62, ParamType.INPARAM.getType(), matv20));
        list.add(new Param(63, ParamType.INPARAM.getType(), matv21));
        list.add(new Param(64, ParamType.INPARAM.getType(), matv22));
        list.add(new Param(65, ParamType.INPARAM.getType(), matv23));
        list.add(new Param(66, ParamType.INPARAM.getType(), matv24));
        list.add(new Param(67, ParamType.INPARAM.getType(), matv25));
        list.add(new Param(68, ParamType.INPARAM.getType(), matv26));
        list.add(new Param(69, ParamType.INPARAM.getType(), matv27));
        list.add(new Param(70, ParamType.INPARAM.getType(), totalStr));
        list.add(new Param(71, ParamType.INPARAM.getType(), Integer.parseInt(stirid)));
        list.add(new Param(72, ParamType.INPARAM.getType(), null));
        procedure.init("sp_insert_LM_TaskTheoryFormula", list);
        try {
            procedure.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }


        return procedure.getResultArray();
    }
}
