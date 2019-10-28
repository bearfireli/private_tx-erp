package com.hntxrj.txerp.entity;


import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Data
@Table(name = "sm_eppinfo")
public class EppInfo {

  @Id
  private String compid;
  private String eppcode;
  private String eppname;
  private String shortname;
  private String address;
  private String linkman;
  private String linktel;
  private String opid;
  private java.sql.Timestamp createtime;
  private String remarks;
  private String recstatus;
  private String updown;
  private String demarcatemap;
  private long updownmark;


}
