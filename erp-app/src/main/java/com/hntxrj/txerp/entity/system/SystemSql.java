package com.hntxrj.txerp.entity.system;

import java.io.Serializable;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class SystemSql implements Serializable {

    /* sql id */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer ssid;
    /* 名称 */
    private String sqlName;
    /* 描述 */
    private String sqlDescribe;
    /* sql语句 */
    private String sqlContent;

}
