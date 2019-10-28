package com.hntxrj.txerp.enums;

/**
 * 公共下拉类型枚举
 *
 * @author lhr
 * @version 20180926001
 */
public enum PublicInfoTypeEnums {
    ACCOUNT_TYPE("绑定帐号类型", "ac_type");


    // 公共信息名称
    private String name;
    // 公共信息值
    private String value;

    PublicInfoTypeEnums(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

}
