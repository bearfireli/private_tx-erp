package com.hntxrj.txerp.vo;

import lombok.Data;

import javax.persistence.Column;
import java.io.Serializable;

@Data
public class LoginClibationVO implements Serializable {

    //账号
    @Column(name = "build_login_name")
    private String loginName;
    //密码
    @Column(name = "build_password")
    private String password;
    @Column(name = "build_id")
    private String id;
    //toKen 令牌,用来区别身份
    private String supplierToKen;
    private String buildName;


}
