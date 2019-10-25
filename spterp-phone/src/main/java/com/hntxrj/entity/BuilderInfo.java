package com.hntxrj.entity;


import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Data
@Table(name = "sm_builderinfo")
public class BuilderInfo implements Serializable {

    @Id
    private String compid;
    private String opid;
    private Timestamp createtime;
    private String buildercode;
    private String buildername;
    private String buildershortname;
    private String address;
    private String linktel;
    private String fax;
    private String corporation;
    private String recstatus;
    private String updown;
    private long updownmark;
}
