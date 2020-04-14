package com.hntxrj.txerp.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserLoginVO implements Serializable {
    private Integer uid;          //用户id
    private String loginIP;       //用户登录的ip
    private String loginTime;     //登陆时间
    private String loginUa;       //用户登录的设备
    private String loginVersion;  //用户登录的版本
}
