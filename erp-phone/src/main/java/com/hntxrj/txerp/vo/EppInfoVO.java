package com.hntxrj.txerp.vo;

import java.util.Date;

/**
 * @author ws
 */
public class EppInfoVO {

    private String compid;
    private String eppCode;
    private String eppName;
    private String shortName;
    private String address;
    private String linkMan;
    private String linkTel;
    private String remarks;
    private String accountingAccountCode;
    private Integer recStatus;
    private Date createTime;
    /**
     * 无参
     */
    public EppInfoVO() {
    }

    /**
     * 全参
     */
    public EppInfoVO(String compid, String eppCode, String eppName, String shortName, String address, String linkMan,
                     String linkTel, String remarks, String accountingAccountCode, Integer recStatus, Date createTime) {
        this.compid = compid;
        this.eppCode = eppCode;
        this.eppName = eppName;
        this.shortName = shortName;
        this.address = address;
        this.linkMan = linkMan;
        this.linkTel = linkTel;
        this.remarks = remarks;
        this.accountingAccountCode = accountingAccountCode;
        this.recStatus = recStatus;
        this.createTime = createTime;
    }

    public String getCompid() {
        return compid;
    }

    public void setCompid(String compid) {
        this.compid = compid;
    }

    public String getEppCode() {
        return eppCode;
    }

    public void setEppCode(String eppCode) {
        this.eppCode = eppCode;
    }

    public String getEppName() {
        return eppName;
    }

    public void setEppName(String eppName) {
        this.eppName = eppName;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLinkMan() {
        return linkMan;
    }

    public void setLinkMan(String linkMan) {
        this.linkMan = linkMan;
    }

    public String getLinkTel() {
        return linkTel;
    }

    public void setLinkTel(String linkTel) {
        this.linkTel = linkTel;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getAccountingAccountCode() {
        return accountingAccountCode;
    }

    public void setAccountingAccountCode(String accountingAccountCode) {
        this.accountingAccountCode = accountingAccountCode;
    }

    public Integer getRecStatus() {
        return recStatus;
    }

    public void setRecStatus(Integer recStatus) {
        this.recStatus = recStatus;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
