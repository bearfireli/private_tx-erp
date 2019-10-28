package com.hntxrj.txerp.util;

import com.hntxrj.txerp.util.jdbc.procedure.Param;
import com.hntxrj.txerp.util.jdbc.procedure.Procedure;
import com.hntxrj.txerp.util.jdbc.enums.ParamType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.List;

import java.util.ArrayList;

/**
 * Created by 刘浩然 on 2017/8/7.
 */
@Component
public class WriteLog {


    @Autowired
    private Procedure procedure;

    private String functionName;
    private String  type;
    private String content;
    private String opId;

    /**
     * 写日志
     * @param functionName 操作
     * @param type         操作类型
     * @param content      发生事件
     * @param opId         操作人
     */
    public WriteLog init(String functionName, String type, String content, String opId){
        this.functionName = functionName;
        this.type = type;
        this.content = content;
        this.opId = opId;
        return this;
    }



    public void write() throws SQLException {
        List<Param> comParam = new ArrayList<>();
        Param param = new Param(1, ParamType.INPARAM.getType(),functionName);
        comParam.add(param);
        param = new Param(2, ParamType.INPARAM.getType(),type);
        comParam.add(param);
        param = new Param(3, ParamType.INPARAM.getType(),content);
        comParam.add(param);
        param = new Param(4, ParamType.INPARAM.getType(),opId);
        comParam.add(param);
        System.out.println(comParam);
        try {
            procedure.init("SP_insert_DD_DatabaseOPlog", comParam);
            procedure.commit();
        }catch (Exception e){
            throw new SQLException("写入日志失败！");
        }
    }

}
