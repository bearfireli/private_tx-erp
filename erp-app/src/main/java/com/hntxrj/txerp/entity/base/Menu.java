package com.hntxrj.txerp.entity.base;


import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 菜单实体类
 */
@Entity
@Data
public class Menu implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer mid;
    private String menuName;
    private String menuUrl;
    private String menuApi;
    private Integer menuFmid;
    private Integer menuStatus;
    private Integer menuLevel;
    private Integer enterprise;
    private Integer project;
    private Integer updateUser;
    @Column(insertable = false, updatable = false)
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    @Column(insertable = false, updatable = false)
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
}
