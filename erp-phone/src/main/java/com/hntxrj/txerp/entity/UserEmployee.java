package com.hntxrj.txerp.entity;

import lombok.Data;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class UserEmployee implements Serializable {

    @Id
    @Column(name = "Opid")
    private String opId;
    private String compid;
    private String empname;
    @Column(name = "loginname")
    private String loginName;
    private String pwd;
    @Column(name = "groupcode")
    private String groupCode = "";
    private String emptype = "";
    private String securid = "";
    private java.util.Date birthday;
    private String email = "";
    private String motel = "";
    private String contack = "";
    private String postid = "";
    private String addr = "";
    private String addr2 = "";
    private java.util.Date rdate;
    private java.util.Date ldate;
    private String account = "";
    private String memo = "";
    private String tel;
    private String codesx = "";
    private String cardid = "";
    private String useing = "";
    private String codejo = "";
    private String factid = "";
    private String codezo = "";
    private Integer state = 0;
    @Column(name = "usergcode")
    private String userGCode = "";
    @Column(name = "createtime")
    private java.util.Date createTime;
    @Column(name = "Opid_C")
    private String opIdC = "";
    @Column(name = "workclass")
    private String workClass = "";
    @Column(name = "updownmark")
    private Integer upDownMark;
    @Column(name = "updown")
    private String upDown;
    @Column(name = "linesofcredit")
    private double linesOfCredit;
    @Column(name = "recstatus")
    private String recStatus;
    private String idphoto = "";

}
