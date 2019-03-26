package com.hntxrj.txerp.entity.amqp;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class Message implements Serializable {

    /**
     * 发送用户uid
     */
    private Integer sendUid;
    /**
     * 发送用户名
     */
    private String sendUserName;
    /**
     * 接收对象（人、群）
     */
    private Integer sendTo;
    /**
     * 消息内容
     */
    private String msg;
    /**
     * 图片
     */
    private String images;
    /**
     * 跳转地址
     */
    private String url;
    /**
     * 发送时间
     */
    private Date sendTime;
    /**
     * 消息类型
     */
    private Integer type;
}
