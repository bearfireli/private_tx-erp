package com.hntxrj.txerp.vo;


import com.hntxrj.txerp.entity.base.Enterprise;
import com.hntxrj.txerp.entity.base.UserAuth;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class UserVO {

    /**
     * 用户id
     */
    private Integer uid;
    /**
     * 用户名
     */
    private String username;
    /**
     * 用户手机号
     */
    private String phone;
    /**
     * 用户邮箱
     */
    private String email;

    //    /**
//     * 企业代号
//     */
//    private Integer enterprise;
//    /**
//     * 企业名称
//     */
//    private String enterpriseName;
    private String epShortNamelist;
    private String agnamelist;
    /**
     * 企业集合
     */
    List<Enterprise> enterprises;

    /**
     * 用户登录令牌
     */
    private String token;

    private Integer erpType;
    private String bindSaleManName;
    /**
     * 用户头像
     */
    private String header;

    List<UserAuth> userAuths;
    List<AuthGroupVO> authGroups;

//    /**
//     * 用户权限组
//     */
//    private Integer authGroup;
//    /**
//     * 用户权限组名称
//     */
//    private String authGroupName;

    /**
     * 用户状态
     */
    private Integer status;

    /**
     * 用户创建时间
     */
    private Date createTime;
    /**
     * 用户更新时间
     */
    private Date updateTime;

    private String actype;


}
