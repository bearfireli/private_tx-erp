package com.hntxrj.txerp.entity;


import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Entity
public class EppAddress {


    /**
     * 地址类型
     */

    // 手动设置的地址
    public static Integer ADDRESS_TYPE_MANUAL_SETTING = 0;
    // 自动获取签收位置的地址
    public static Integer ADDRESS_TYPE_AUTH = 1;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String eppCode;
    @Column(name = "`address`")
    private String address;
    private String createUser;
    private Timestamp createTime;
    @Column(name = "`order`")
    private Integer order;
    private Boolean status;
    /**
     * 0手动添加，1获取签收位置
     */
    private Integer addressType;
    private String compid;
}
