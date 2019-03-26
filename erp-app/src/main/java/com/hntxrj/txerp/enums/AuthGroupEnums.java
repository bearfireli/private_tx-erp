package com.hntxrj.txerp.enums;

public enum AuthGroupEnums {
    USE(0),
    NOUSE(1);


    private int code;

    public int getCode() {
        return code;
    }

    AuthGroupEnums(int code) {
        this.code = code;
    }
}
