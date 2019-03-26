package com.hntxrj.txerp.entity.sell;


import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Data
@Table(name = "builder_master")
public class Builder implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String builderCode;
    private String builderName;
    private String builderShortName;
    private String builderAddress;
    private String builderLinkTel;

    private Integer createUser;
    @Column(insertable = false, updatable = false)
    private Date createTime;

    private Integer updateUser;
    @Column(insertable = false, updatable = false)
    private Date updateTime;

    private Integer enterprise;
    private Integer del;

}
