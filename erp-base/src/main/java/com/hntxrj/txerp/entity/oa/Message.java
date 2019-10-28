package com.hntxrj.txerp.entity.oa;

import lombok.Data;

import java.util.List;

/**
 * 消息对象
 */
@Data
public class Message {


    private Integer sendUser;

    private String sendUserName;

    private String sendTime;

    private String sendMessage;

    /**
     * 推送渠道
     *
     * @see MessageDitchEnums
     */
    private Integer ditch;

    /**
     * 消息类型
     *
     * @see MessageTypeEnums
     */
    private Integer messageType;

    private List<Integer> toUsers;

}
