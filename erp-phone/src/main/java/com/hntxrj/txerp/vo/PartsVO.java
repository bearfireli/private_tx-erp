package com.hntxrj.txerp.vo;

import lombok.Data;

import java.sql.Timestamp;

/**
 * @author qyb
 * @ClassName PartsVO
 * @Description TODO
 * @Date 19-7-10 上午11:38
 * @Version 1.0
 **/
@Data
public class PartsVO {
    /*申请单号*/
    private  String requestNumber;
    /*申请模式*/
    private String requestMode;
    /*申请单状态*/
    private String requestStatus;
    /*申请部门*/
    private String name;
    /*申请人*/
    private String buyer;
    /*审核员代号一*/
    private String verifyIdOne;
    /*申请状态一*/
    private String verifyStatusOne;
    /*审核时间一*/
    private String  verifyTimeOne;
    /*审核结果一*/
    private String auditResultOne;
    private String verifyIdTwo;
    private String verifyStatusTwo;
    private String  verifyTimetwo;
    private String auditResultTwo;
    private String verifyIdThree;
    private String verifyStatusThree;
    private String  verifyTimeThree;
    private String auditResultThree;
    private String verifyIdFour;
    private String verifyStatusFour;
    private String  verifyTimeFour;
    private String auditResultFour;
    private String verifyIdFive;
    private String verifyStatusFive;
    private String  verifyTimeFive;
    private String auditResultFive;
    private String verifyIdSix;
    private String verifyStatusSix;
    private String  verifyTimeSix;
    private String auditResultSix;
    /*申请描述*/
    private String requestDep;
    /*配件名称*/
    private String goodsName;
    /*配件规格*/
    private String specification;
    /*数量*/
    private String num;
    /*备注*/
    private String remarks;
    /*申请金额*/
    private String amount;
    /*创建时间*/
    private String  createTime;
    private  String department;
    private String requestCode;
    private  String requestStatusCode;
}
