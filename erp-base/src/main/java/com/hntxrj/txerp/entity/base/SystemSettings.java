package com.hntxrj.txerp.entity.base;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Data
public class SystemSettings implements Serializable {
    /*设置代号*/
    @Id
    private String settingCode;
    /*设置值*/
    private String settingValue;
    /*企业id*/
    private Integer enterprise;
    /*创建时间*/
    @Column(insertable = false, updatable = false)
    private Date createTime;
    /*创建人id*/
    private Integer createUid;
    /*更新时间*/
    @Column(insertable = false, updatable = false)
    private Date updateTime;
    /*更新人代号*/
    private Integer updateUid;
}
