package com.hntxrj.txerp.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class IMGroupMemberVO implements Serializable {
    private String memberAccount;       //群成员账号
    private String role;        //群成员角色
    private String joinTime;        //加入群组时间
    private String unreadMsgNum;        //未读信息
}
