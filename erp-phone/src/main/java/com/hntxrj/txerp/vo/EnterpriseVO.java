package com.hntxrj.txerp.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class EnterpriseVO implements Serializable {
    private String compid;
    private String longName;  //公司名称全称
    private String shortName;  //公司名称简称
    private String addr;   //坐标经纬度
}
