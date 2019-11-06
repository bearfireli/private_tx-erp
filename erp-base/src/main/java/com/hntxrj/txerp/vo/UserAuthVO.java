package com.hntxrj.txerp.vo;

import com.hntxrj.txerp.entity.base.AuthGroup;
import com.hntxrj.txerp.entity.base.Enterprise;
import com.hntxrj.txerp.entity.base.Menu;
import com.hntxrj.txerp.entity.base.User;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class UserAuthVO {

    private Integer uaid;
    private User user;
    private Enterprise enterprise;
    private AuthGroup authGroup;
    private Integer createUser;
    private Date createTime;
    private Date updateTime;
    private Integer updateUser;
    private String eadmin;
    private String driverName;

    List<Menu> menuVOS;
}
