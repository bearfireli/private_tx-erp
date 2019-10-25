package com.hntxrj.txerp.entity.base;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity
@Data
public class UserBindDriver {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer bindDriverId;
    private Integer uid;


    private String driverCode;
    private String compid;
    private Date createTime;
}
