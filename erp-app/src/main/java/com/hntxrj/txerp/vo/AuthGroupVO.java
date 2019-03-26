package com.hntxrj.txerp.vo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.Date;

@Data
public class AuthGroupVO {

    private Integer agid;

    /**
     * 权限组名称
     */
    private String agName;

    /**
     * 权限组状态
     */
    private Integer agStatus;

    /**
     * 所属企业
     */
    @JSONField(name = "enterpriseName")
    private String epShortName;

    private Integer enterprise;

    /**
     * 所属项目
     */
    private Integer project;


    /**
     * 创建时间
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 更新人
     */
    @JSONField(name = "updateUserName")
    private String username;

    private Integer updateUser;

    /**
     * 更新时间
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;


}
