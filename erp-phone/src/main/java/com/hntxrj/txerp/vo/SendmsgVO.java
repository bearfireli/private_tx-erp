package com.hntxrj.txerp.vo;

import lombok.Data;

import java.sql.Array;

@Data
public class SendmsgVO {

    /*1：把消息同步到 From_Account 在线终端和漫游上；
        2：消息不同步至 From_Account；
        若不填写默认情况下会将消息存 From_Account 漫游*/
    private Integer syncOtherMachine;
    /*消息发送方 Identifier（用于指定发送消息方帐号）*/
    private String fromAccount;
    /*消息接收方 Identifier*/
    private String toAccount;
    /*消息离线保存时长（单位：秒），最长为7天（604800秒）
    若设置该字段为0，则消息只发在线用户，不保存离线
    若设置该字段超过7天（604800秒），仍只保存7天
    若不设置该字段，则默认保存7天*/
    private Integer msgLifeTime;
    /*消息随机数，由随机函数产生，用于后台定位问题*/
    private Integer msgRandom;
    /*消息时间戳，UNIX 时间戳（单位：秒）*/
    private Integer msgTimeStamp;
    /*消息内容，具体格式请参考 消息格式描述（注意，一条消息可包括多种消息元素，MsgBody 为 Array 类型）*/
    private Array msgBody;
    /*TIM 消息对象类型，目前支持的消息对象包括：TIMTextElem(文本消息)，TIMFaceElem(表情消息)，TIMLocationElem(位置消息)，
    TIMCustomElem(自定义消息)*/
    private String msgType;
    /*对于每种 MsgType 用不同的 MsgContent 格式*/
    private String msgContent;
    /*离线推送信息配置*/
    private Object offlinePushInfo;
}

