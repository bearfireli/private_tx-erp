package com.hntxrj.txerp.entity.oa;

/**
 * 消息类型
 */
public enum MessageTypeEnums {

    ;


    private Integer type;

    int getDitch() {
        return this.type;
    }

    MessageTypeEnums(int type) {
        this.type = type;
    }
}
