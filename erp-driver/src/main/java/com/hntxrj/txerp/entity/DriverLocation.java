package com.hntxrj.txerp.entity;


import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Timestamp;

/**
 * 记录司机位置
 */
@Data
@Entity
public class DriverLocation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer dlId;
    private String dlEppCode;
    private Integer dlTsiId;
    private String dlDriverCode;
    private String dlCarId;
    private String address;
    private Timestamp createTime;


}
