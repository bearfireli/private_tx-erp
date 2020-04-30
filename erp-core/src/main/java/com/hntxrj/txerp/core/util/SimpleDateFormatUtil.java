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
     * 获取默认空参的SimpleDateFormat构造
     */
    public static SimpleDateFormat getSimpleDataFormat() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
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


}
