package com.hntxrj.txerp.entity;

import lombok.Data;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
public class UserLoginHistory implements Serializable {

    @Id
    @GeneratedValue
    private String opid;
    private String loginIp;
    private java.util.Date createTime;
    private String token;
    private String driver;
    private String compid;


}
