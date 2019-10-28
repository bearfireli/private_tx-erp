package com.hntxrj.txerp.util;


/**
 * Created by apple on 2017/4/7.
 */
public class Code {


    public static synchronized String getOpId(){
        String opid = String.valueOf(System.currentTimeMillis());
        opid = opid.substring(0,10);
        return opid;
    }

}
