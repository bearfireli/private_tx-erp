package com.hntxrj.txerp.core.util;


import java.text.SimpleDateFormat;
import java.util.TimeZone;

/**
 * 时间格式转换类
 *
 * @author nsk
 */
public class SimpleDateFormatUtil {

    /**
     * 所有的时间格式转换都要通过此工具类转换，不能直接通过new SimpleDateFormat转换
     * 避免产生时区错误
     *
     * @param pattern 时间格式（比如："yyyy-MM-dd"）
     */
    public static SimpleDateFormat getSimpleDataFormat(String pattern) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
        return simpleDateFormat;
    }


    /**
     * 获取常用的截止到时分秒的simpleDateFormat
     */
    public static SimpleDateFormat getDefaultSimpleDataFormat() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
        return simpleDateFormat;
    }


    /**
     * 此方法用于把dataTime类型的数据转换成String类型，在手机erp进行增删改操作时向sync_data表中插入数据时调用
     */
    public static String timeConvert(Object dataTime) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
        String time = null;
        if (dataTime != null) {
            time = simpleDateFormat.format(dataTime);
        }
        return time;
    }


}
