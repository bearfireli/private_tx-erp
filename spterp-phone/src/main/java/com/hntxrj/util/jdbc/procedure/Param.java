package com.hntxrj.util.jdbc.procedure;

import lombok.Data;

/**
 * 该对象用于用于存储存储过程参数对象
 * 注意其中code和type的区别
 * code：对用于区分入参出参，具体参照enum中的ParamType
 * type：用于设置参数的类型，暂时用于设置出参的类型
 * data：具体的值
 * Created by 刘浩然 on 2017/7/27.
 */
@Data
public class Param {

    /**
     * 参数位置
     */
    private int location;

    /**
     * 参数类型,
     * 枚举关联ParamType的type属性
     * 例如：出参,入参
     */
    private int code;

    /**
     * 参数类型
     * 使用java.sql的Types enum
     */
    private int type;

    /**
     * 参数对象
     */
    private Object data;


    /**
     * 存储过程有出参 调用该构造器
     * @param location  参数位置
     * @param code      参数类型（入参，出参）
     * @param type      参数类型java.sql.Types
     * @param data      参数值
     */
    public Param(int location, int code, int type, Object data){
        this.location = location;
        this.code = code;
        this.type = type;
        System.out.println(data);
        this.data = data == null ? "" : data;
    }



    /**
     * 无出参调用该构造器
     * @param location      参数位置
     * @param code          参数类型（入参，出参）
     * @param data          参数值
     */
    public Param(int location, int code, Object data){
        this.location = location;
        this.code = code;
        this.data = data == null ? "" : data;
    }

    public Param(){}

    public int getLocation() {
        return location;
    }

    public void setLocation(int location) {
        this.location = location;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
