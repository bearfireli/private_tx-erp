package com.hntxrj.util.jdbc.enums;

/**
 * @author lhr
 * @create 2018/1/29
 */
public enum DBTypeEnums {

    /**
     * mysql数据库
     */
    MYSQL(0),
    /**
     * sqlserver数据库
     */
    SQLSERVER(1),
    ;

    private int code;


    DBTypeEnums(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
