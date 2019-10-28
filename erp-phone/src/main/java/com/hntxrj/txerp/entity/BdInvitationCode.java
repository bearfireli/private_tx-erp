package com.hntxrj.txerp.entity;

import lombok.Data;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;


@Entity
@Data
@Table(name = "bd_invitation_code")
public class BdInvitationCode implements Serializable {
    @Id
    @Column(name = "buildinvitationcode")
    private String buildInvitationCode;
    @Column(name = "compid")
    private String compid;
    @Column(name = "buildcode")
    private String buildCode;
    @Column(name = "usestatus")
    private Integer useStatus;
    @Column(name = "createuser")
    private Integer createUser;
    @Column(name = "createtime")
    private String  createTime;

}
