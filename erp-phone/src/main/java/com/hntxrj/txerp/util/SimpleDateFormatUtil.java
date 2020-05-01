package com.hntxrj.txerp.util;

import java.text.SimpleDateFormat;
import java.util.TimeZone;

public class SimpleDateFormatUtil {
    public static SimpleDateFormat getSimpleDateFormat(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
        return sdf;
    }
}
