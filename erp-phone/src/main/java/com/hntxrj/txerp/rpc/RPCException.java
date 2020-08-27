package com.hntxrj.txerp.rpc;

public class RPCException extends RuntimeException {
    public static final RPCException NOT_FOUNT_404 = new RPCException("NOT_FOUNT_404", "无法访问");
    public static final RPCException CALL_ERROR = new RPCException("CALL_ERROR", "请求失败");

    private String errCode;
    private String errMsg;

    public RPCException(String errCode, String errMsg) {
        super(errMsg);
    }


    public String getErrCode() {
        return errCode;
    }

    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }
}
