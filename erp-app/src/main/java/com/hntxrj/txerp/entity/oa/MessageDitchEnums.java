package com.hntxrj.txerp.entity.oa;

/**
 * 消息推送渠道
 */
public enum MessageDitchEnums {

    // 钉钉
    DING(1),
    // 微信
    WeiChat(2),
    ;


    private Integer ditch;

    int getDitch() {
        return this.ditch;
    }

    MessageDitchEnums(int ditch) {
        this.ditch = ditch;
    }
}
