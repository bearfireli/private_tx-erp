package com.hntxrj.txerp.entity;

import lombok.Data;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;


@Entity
@Data
@Table(name = "wm_configureapply")
public class WmConFigureApply implements Serializable {

    @Id
    @Column(name = "requestnumber")
    private String requestNumber;
    @Column(name = "serialnumber")
    private Integer serialnumber;
    @Column(name = "compid")
    private String compid;
    @Column(name = "requestdep")
    private String requestDep;
    @Column(name = "requestmode")
    private Integer requestMode;
    @Column(name = "requeststatus")
    private Integer requestStatus;
    @Column(name = "department")
    private String department;
    @Column(name = "buyer")
    private String buyer;
    @Column(name = "goodscode")
    private Integer goodsCode;
    @Column(name = "goodsname")
    private String goodsName;
    @Column(name = "specification")
    private String specification;
    @Column(name = "num")
    private double num;
    @Column(name = "stockstatus")
    private String stockStatus;
    @Column(name = "remarks")
    private String remarks;
    @Column(name = "opid")
    private String opId;
    @Column(name = "createtime")
    private String createTime;
    @Column(name = "verifyidone")
    private String verifyIdOne;
    @Column(name = "verifystatusone")
    private boolean verifyStatusOne;
    @Column(name = "verifytimeone")
    private String verifyTimeOne;
    @Column(name = "auditresultone")
    private String auditResultOne;
    @Column(name = "verifyidtwo")
    private String verifyIdTwo;
    @Column(name = "verifystatustwo")
    private boolean verifyStatusTwo;
    @Column(name = "verifytimetwo")
    private String verifyTimeTwo;
    @Column(name = "auditresulttwo")
    private String auditResultTwo;
    @Column(name = "verifyidthree")
    private String verifyIdThree;
    @Column(name = "verifystatusthree")
    private boolean verifyStatusThree;
    @Column(name = "verifytimethree")
    private String verifyTimeThree;
    @Column(name = "auditresultthree")
    private String auditResultThree;
    @Column(name = "verifyidfour")
    private String verifyIdFour;
    @Column(name = "verifystatusfour")
    private boolean verifyStatusFour;
    @Column(name = "verifytimefour")
    private String verifyTimeFour;
    @Column(name = "auditresultfour")
    private String auditResultFour;
    @Column(name = "verifyidfive")
    private String verifyIdFive;
    @Column(name = "verifystatusfive")
    private boolean verifyStatusFive;
    @Column(name = "verifytimefive")
    private String verifyTimeFive;
    @Column(name = "auditresultfive")
    private String auditResultFive;
    @Column(name = "verifyidsix")
    private String verifyIdSix;
    @Column(name = "verifystatussix")
    private boolean verifyStatusSix;
    @Column(name = "verifytimesix")
    private String verifyTimeSix;
    @Column(name = "auditresultsix")
    private String auditResultSix;
    @Column(name = "recstatus")
    private boolean recStatus;
    @Column(name = "updown")
    private String upDown;
    @Column(name = "updownmark")
    private Integer upDownMark;
    @Column(name = "amount")
    private double amount;

}
