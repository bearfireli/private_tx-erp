package com.hntxrj.entity;

import java.util.Date;
import java.io.Serializable;
import lombok.Data;
import javax.persistence.*;
import java.math.BigDecimal;

//@Entity
@Data
public class PushMessage implements Serializable{

    /* 消息id */
    private String pushMessageId;
    /* 消息内容 */
    private String pushMessgaeContent;
    /* 推送类型 */
    private String pushType;
    /* 消息接收人 */
    private String pushReceiver;
    /* 分组，一个通知中所有记录（通知 */
    private String pushGroup;
    /* 0未读1已读 */
    private String readStatus;
    /* 企业id */
    private String compid;

}
