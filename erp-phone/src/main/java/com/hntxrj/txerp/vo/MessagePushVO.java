package com.hntxrj.txerp.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class MessagePushVO implements Serializable {

    private String compid;              //企业id
    private Integer typeId;             //推送类型
    private String message;             //提示消息
    private String taskId;              //任务单号
    private String contractUid;         //主合同号
    private String contractDetailCode;  //子合同号
    private String stirId;              //线号
    private String vehicleId;           //车号


}
