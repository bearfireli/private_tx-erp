package com.hntxrj.txerp.entity.base;

import lombok.Data;


import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Data
@Entity
public class UserStatistic implements Serializable {

    @Id
    private Integer id;
    private String typeName;
    private Integer typeCode;
    private Integer value;
    private String statDate;
    private Integer compid;
    private Integer uid;
    private String methodName;
    private String functionName;
    private String appName;
    private Integer appCode;
}