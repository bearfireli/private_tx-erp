package com.hntxrj.txerp.util;

import com.alibaba.fastjson.JSONArray;
import com.hntxrj.txerp.util.jdbc.procedure.Param;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * 日志
 * @author lhr
 * @create 2018/1/15
 */
@Slf4j
public class LogUtil {


    public static void procedureInfo(String procedureName,
                                     List<Param> params, JSONArray result){
        log.info("【userLogin】存储过程={}, param={}, result={}",
                procedureName, params, result);
    }


}
