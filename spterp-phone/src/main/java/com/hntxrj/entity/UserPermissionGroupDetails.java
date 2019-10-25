package com.hntxrj.entity;

import lombok.Data;

import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table(name = "user_permissiongroupdetails")
@Data
public class UserPermissionGroupDetails implements Serializable {

    @Id
    @GeneratedValue
    private Integer id;

    @Column(name = "groupclass")
    private String groupClass;
    @Column(name = "menuid")
    private Integer menuId;
    @Column(name = "permissionvalue")
    private Integer permissionValue;
    private String compid;

}
