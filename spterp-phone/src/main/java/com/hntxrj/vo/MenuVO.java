package com.hntxrj.vo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @author lhr
 * @create 2018/1/17
 */
@Data
public class MenuVO {

    private Integer id;

    @JSONField(name="pId")
    private int menuFid;

    @JSONField(name = "name")
    private String menuName;

    private String menuIcon;

    private Integer menuGroup;

    private String menuUrl;

    private String menuDoUrl;

    private Integer menuDoTaxis;

    private Integer menuIdentification;

    private java.util.Date createTime;

    private String createUser;

    private java.util.Date updateTime;

    private String menuStatus;

    private Integer menuSort;

    private Integer menuType;

    private String menuAlias;

    /* 控制节点是否打开 */
    private Boolean open = true;

    /*  */
    private Boolean checked = false;

}
