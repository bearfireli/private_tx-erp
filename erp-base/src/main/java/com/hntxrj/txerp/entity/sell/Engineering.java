package com.hntxrj.txerp.entity.sell;


import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "engineering_master")
public class Engineering implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String engineeringCode;
    private String engineeringFullName;
    private String engineeringShortName;
    private String engineeringAddress;
    private String engineeringLinkMan;
    private String engineeringLinkTel;
    private String remark;
    private Integer del;
    private Integer enterpriseId;
    private Integer createUser;
    @Column(insertable = false, updatable = false)
    private Date createTime;
    private Integer updateUser;
    @Column(insertable = false, updatable = false)
    private Date updateTime;

}
