package com.hntxrj.txerp.vo;

import lombok.Data;

import java.util.Date;

/**
 * @author lhr
 * @create 2018/2/1
 */
@Data
public class AdminVO {

    public static final Integer STATUS_USE = 0;

    public static final Integer STATUS_NOT_USE = 1;

    private String adminName;
    private String adminCompid;
    private String compName;
    private String adminLoginIp;
    private Date adminLastLoginTime;
    private Integer adminStatus = STATUS_USE;
}
