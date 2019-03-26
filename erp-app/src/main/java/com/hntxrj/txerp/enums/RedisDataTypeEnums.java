package com.hntxrj.txerp.enums;

/**
 * redis存储数据类型枚举
 *
 * @author haoran liu(zzlhr)
 */
public enum RedisDataTypeEnums {
    USER("用户", "00"),
    ENTERPRISE("企业", "01"),
    TOKEN("令牌", "02"),

    ;


    // 公共信息名称
    private String type;
    // 公共信息值
    private String value;

    RedisDataTypeEnums(String type, String value) {
        this.type = type;
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public String getValue() {
        return value;
    }


}
