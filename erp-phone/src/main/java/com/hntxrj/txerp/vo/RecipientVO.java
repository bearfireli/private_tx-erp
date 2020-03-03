package com.hntxrj.txerp.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class RecipientVO implements Serializable {
    private Integer uid;
    private String userName;       //消息推送人姓名
    private String compid;
    private Integer typeId;             //消息推送类型
    private String phone;
}
