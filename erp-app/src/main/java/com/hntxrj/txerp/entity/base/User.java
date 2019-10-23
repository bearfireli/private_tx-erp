package com.hntxrj.txerp.entity.base;


import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


/**
 * 用户实体类
 */
@Entity
@Data
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer uid;
    private String username;
    private String password;
    private String phone;
    private String email;
    // 1 销售，2 司机
    private Integer erpType;
    private String bindSaleManName;
    private String header;
    private Integer status;
    @Column(insertable = false, updatable = false)
    private Date createTime;
    @Column(insertable = false, updatable = false)
    private Date updateTime;
    private String userFavoriteConfig;

    //新添加用户的hashcode,此字段只在添加用户时使用
    @Transient
    private Integer Identification;



}
