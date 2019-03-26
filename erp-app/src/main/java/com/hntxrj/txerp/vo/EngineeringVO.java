package com.hntxrj.txerp.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class EngineeringVO implements Serializable {
    private Integer id;
    private String engineeringCode;
    private String engineeringFullName;
    private String engineeringShortName;
    private String engineeringAddress;
    private String engineeringLinkMan;
    private String engineeringLinkTel;
    private String remark;
    private Integer del;
    private Integer enterpriseId;
    private Integer createUser;
    private Date createTime;
    private Integer updateUser;
    private Date updateTime;
    private String enterpriseName;
    private String createUserName;
    private String updateUserName;
}
