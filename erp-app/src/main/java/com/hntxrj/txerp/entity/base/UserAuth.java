package com.hntxrj.txerp.entity.base;


import lombok.Data;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
public class UserAuth implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer uaid;

    @OneToOne(targetEntity = User.class)
    @JoinColumn(name = "uid")
    private User user;

    @OneToOne(targetEntity = Enterprise.class)
    @JoinColumn(name = "eid")
    private Enterprise enterprise;

    @OneToOne(targetEntity = AuthGroup.class, optional = false)
    @JoinColumn(name = "auth_group", referencedColumnName = "agid", nullable = false)
    private AuthGroup authGroup;

    private Integer createUser;
    @Column(insertable = false, updatable = false)
    private Date createTime;
    @Column(insertable = false, updatable = false)
    private Date updateTime;
    private Integer updateUser;


    @Transient
    private String driverName;



}
