package com.hntxrj.txerp.entity.base;


import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 企业实体类
 */
@Entity
@Data
public class Enterprise implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer eid;
    private String epName;
    private String epShortName;
    private String epLink;
    private String epRemark;
    private Integer epType;
    private Integer epStatus;
    @Column(insertable = false, updatable = false)
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    @Column(insertable = false, updatable = false)
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
    @Column(name = "`delete`", insertable = false)
    private Integer delete;
    private String collectionCode;
    private String epAddress;

}
