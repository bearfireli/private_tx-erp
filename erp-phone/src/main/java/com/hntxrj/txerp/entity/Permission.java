package com.hntxrj.txerp.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * 功能: 权限实体类
 *
 * @Auther 李帅
 * @Data 2017-09-01.下午 9:20
 */
@lombok.Data
public class Permission implements Serializable {

    //企业
    public String compid;
    //权限组id
    public Integer groupClass;


    //权限组名字
    public String groupName = "";
    //权限组UUID
    public Integer mark = -1;
    public String createUser = "";


    public List<Data> list = new ArrayList<>();
}
