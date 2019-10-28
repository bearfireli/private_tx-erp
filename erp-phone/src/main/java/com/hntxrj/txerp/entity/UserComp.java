package com.hntxrj.txerp.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class UserComp implements Serializable {

    @Id
    private String compid;
    private String longname;
    private String shortname;
    private Integer comptype;
    private String addr;
    private String tel;
    private String fax;
    private String contack;
    private String director;
    private String dmotel;
    private String updid;
    private Date updtime;
    private Date ddate;
    private String facno;
    private String postid;
    private String ename;
    private String useing;
    private String isvisible;
    private double taxrate;
    private String taxnumber;
    private String busnumber;
    private String coderd;
    private Integer roundbit;
    private double timeouts;
    private String updown;
    private Integer updownmark;
    private String recstatus;
    private Integer erpType;


}
