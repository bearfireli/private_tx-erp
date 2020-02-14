package com.hntxrj.txerp.vo;

import lombok.Data;

import java.sql.Array;

/*
* 好友实体类
* */
@Data
public class FriendsVO {
    /*需要为该 UserID 添加好友*/
    private String fromAccount;
    /*好友结构体对象*/
    private Array addFriendItem;
    /*好友的 UserID*/
    private String toAccount;
    /*好友备注*/
    private String remark;
    /*分组信息*/
    private String groupName;
    /*加好友来源字段*/
    private String addSource;
    /*形成好友关系时的附言信息*/
    private String addWording;
    /*加好友方式（默认双向加好友方式）：
        Add_Type_Single 表示单向加好友
        Add_Type_Both 表示双向加好友*/
    private String addType;
    /*管理员强制加好友标记：1表示强制加好友，0表示常规加好友方式*/
    private Integer forceAddFlags;
}
