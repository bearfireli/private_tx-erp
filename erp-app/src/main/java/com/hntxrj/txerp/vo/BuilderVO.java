package com.hntxrj.txerp.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class BuilderVO implements Serializable {

    private Integer id;
    private String builderCode;
    private String builderName;
    private String builderShortName;
    private String builderAddress;
    private String builderLinkTel;

    private Integer createUser;
    private Date createTime;

    private Integer updateUser;
    private Date updateTime;

    private Integer enterprise;
    private Integer del;
    private String enterpriseName;
    private String updateUserName;


}
