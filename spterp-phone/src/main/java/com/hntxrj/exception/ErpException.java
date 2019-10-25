package com.hntxrj.exception;


/**
 * 所有失败返回都会抛出该异常类，如果一个请求出现该类异常即视为成功
 * 所有为成功的逻辑都需要抛出该异常。
 *
 * 改异常的所有内容来源于ErrEumn枚举中的错误。
 */
public class ErpException extends Exception {

    private final ErrEumn errEumn;

    public ErrEumn getErrEumn() {
        return errEumn;
    }

    public ErpException(ErrEumn errEumn) {
        super("ErpException errCode=" + errEumn.getCode()
                + "errMsg=" + errEumn.getMessage());
        this.errEumn = errEumn;
    }

    public ErpException(Integer code, String msg, ErrEumn errEumn) {
        super("ErpException errCode=" + code
                + "errMsg=" + msg);
        this.errEumn = errEumn;
    }
}
