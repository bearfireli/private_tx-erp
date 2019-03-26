package com.hntxrj.txerp.entity.base;


import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


/**
 * 权限组
 */
@Entity
@Data
public class AuthGroup implements Serializable {

    /**
     * 权限组id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer agid;

    /**
     * 权限组名称
     */
    private String agName;

    /**
     * 权限组状态
     */
    private Integer agStatus;

    /**
     * 所属企业
     */
    private Integer enterprise;

    /**
     * 所属项目
     */
    private Integer project;

    /**
     * 创建时间
     */
    @Column(insertable = false, updatable = false)
    private Date createTime;

    /**
     * 更新人
     */
    private Integer updateUser;

    /**
     * 更新时间
     */
    @Column(insertable = false, updatable = false)
    private Date updateTime;

}
