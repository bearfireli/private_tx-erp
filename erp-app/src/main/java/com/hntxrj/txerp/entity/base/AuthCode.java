package com.hntxrj.txerp.entity.base;


import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 验证码表
 */
@Data
@Entity
public class AuthCode implements Serializable {

    // 绑定账号发送的验证码
    public static final Integer AU_TYPE_BING = 1;

    @Id
    private String auId;
    private String auPhone;
    private String auValue;
    private Integer auType;
    @Column(insertable = false, updatable = false)
    private java.sql.Timestamp createTime;
    private Integer del;


}
