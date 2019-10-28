package com.hntxrj.txerp.util;

import com.hntxrj.txerp.entity.PageBean;
import com.hntxrj.txerp.util.jdbc.procedure.Procedure;

/**
 * 功能:  page常用工具类
 *
 * @Auther 李帅
 * @Data 2017-09-22.下午 6:04
 */
public class CommonUtil {

    /**
     *  获取结果集中的页数
     *
     *      不需要返回值，对象类型直接改变
     *
     * @param procedure
     * @param i
     */
    public static void getrecordCount (Procedure procedure, PageBean pageBean, int i){
        if ( !procedure.getResultArray().isEmpty() ){
            String str = procedure.getResultArray().getJSONArray(i).toString();
            str = str.substring( str.indexOf(":")+1, str.indexOf("}"));
            System.out.println("总数为:" + str);
            if ( str.matches("[0-9]*")){
                /* 对象类型值会传递 */
                pageBean.setRecordCount(Integer.parseInt(str));
            }
        }
    }

}
