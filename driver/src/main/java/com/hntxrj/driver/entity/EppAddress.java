package com.hntxrj.driver.entity;


import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Timestamp;

@Data
public class EppAddress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String eppCode;
    private String address;
    private String createUser;
    private Timestamp createTime;
    private Integer order;
    private Boolean status;
    /**
     * 0手动添加，1获取签收位置
     */
    private Integer addressType;
    private String compid;
}
