package com.hntxrj.vo;

import com.hntxrj.entity.UserPermissionGroup;
import lombok.Data;

/**
 * @author lhr
 * @create 2018/1/19
 */
@Data
public class PermissionGroupVO {

    private Integer id;
    private String groupName;
    private String groupClass;
    private String groupStatus;
    private java.util.Date createTime;
    private String createUser;
    private java.util.Date updateTime;
    private String compid;
    private String compName;


}
