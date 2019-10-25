package com.hntxrj.entity;

import lombok.Data;

import java.io.Serializable;
import java.sql.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "user_admin")
public class UserAdmin implements Serializable {

    public static final Integer STATUS_USE = 0;

    public static final Integer STATUS_NOT_USE = 1;

    @Id
    private String adminName;
    private String adminPassword;
    private String adminCompid;
    private String adminToken;
    private String adminLoginIp;
    private Date adminLastLoginTime;
    private Integer adminStatus = STATUS_USE;


}
