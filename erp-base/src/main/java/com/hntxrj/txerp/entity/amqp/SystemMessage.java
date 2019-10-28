package com.hntxrj.txerp.entity.amqp;

import lombok.Data;

/**
 * 系统消息
 *
 */
@Data
public class SystemMessage {
    Integer uid;
    String userName;
    Integer eid;
    String msg;
}
