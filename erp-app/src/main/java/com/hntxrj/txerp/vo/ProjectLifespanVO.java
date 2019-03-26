package com.hntxrj.txerp.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class ProjectLifespanVO implements Serializable {
    private Integer pid;
    private String projectName;
    private Integer enterpriseId;
    private String enterpriseName;
    //开始使用时间
    private Date startTime;
    //到期时间
    private Date expireTime;
    //创建时间
    private Date createTime;
    //更新时间
    private Date updateTime;
    //更新用户
    private Integer updateUser;
    private String updateUserName;
}
