package com.hntxrj.txerp.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class PushTypeVO implements Serializable {

    private Integer typeId;     //消息推送类型代号
    private String typeName;        //消息推送类型名称
    private List<RecipientVO> recipientVOList;      //消息推送人集合
}
