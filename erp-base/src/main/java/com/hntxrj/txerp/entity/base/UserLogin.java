package com.hntxrj.txerp.entity.base;


import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;


/**
 * 用户登录记录
 */
@Data
@Entity
public class UserLogin implements Serializable {

    @Id
    private String id;
    private Integer userId;
    private String userToken;
    private String loginIp = "未知";
    private String loginAddress = "未知";
    private String loginApp = "未知";
    @Column(insertable = false, updatable = false)
    private Date createTime;
    private Date expireTime = new Date(0);
    private String loginUa;
    private String loginVersion;
}
