package com.hntxrj.txerp.entity;

import lombok.Data;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;


@Entity
@Data
@Table(name = "bd_build_accounts")
public class BdBuildAccounts implements Serializable {

    @Id
    @Column(name = "buildid")
    private Integer buildid;
    @Column(name = "buildloginname")
    private String buildLoginName;
    @Column(name = "buildpassword")
    private String buildPassword;

}
