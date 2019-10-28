package com.hntxrj.txerp.entity;

import lombok.Data;

import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table(name = "User_permissiongroups")
@Data
public class UserPermissionGroups implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "opid")
    private String opId;
    @Column(name = "groupcode")
    private Integer groupCode;


}
