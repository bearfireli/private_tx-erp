package com.hntxrj.txerp.entity;

import lombok.Data;

import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table(name = "user_permissiongroup")
@Data
public class UserPermissionGroup implements Serializable {


    public static final String USE = "0";
    public static final String NOT_USE = "1";

    @Id
    @GeneratedValue
    private Integer id;
    @Column(name = "groupname")
    private String groupName;
    @Column(name = "groupclass")
    private String groupClass;
    @Column(name = "groupstatus")
    private String groupStatus = USE;
    @Column(name = "createtime")
    private java.util.Date createTime;
    @Column(name = "createuser")
    private String createUser;
    @Column(name = "updatetime")
    private java.util.Date updateTime;
    @Column(name = "compid")
    private String compid;

}
