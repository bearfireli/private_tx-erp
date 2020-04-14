package com.hntxrj.txerp.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserLoginVO implements Serializable {
    private Integer uid;           //用户id
    private String userName;       //用户名
    private String phoneNum;       //手机号
    private String enterpriseName; //企业名称
    private String shortName;      //企业简称
    private String loginIP;        //用户登录的ip
    private String loginTime;      //登陆时间
    private String loginUa;        //用户登录的设备
    private String loginVersion;   //用户登录的版本
}
