package com.hntxrj.exception;

public enum ErrEumn {

    /* 企业20100-20199 */
    ADD_ENTERPRISE_NAME_NULL(-20100, "添加企业名称不能为空"),
    ADD_ENTERPRISE_ERROR(-20101, "添加企业失败"),


    /* 合同21000-21099 */
    ADD_CONTRACT_NOT_FOUND_COMPID(-21001, "找不到企业id"),
    ADD_CONTRACT_NOT_FOUND_CONTRACTID(-21002, "找不到合同代号"),
    ADD_CONTRACT_NOT_FOUND_EPPCODE(-21003, "找不到工程代号"),
    ADD_CONTRACT_NOT_FOUND_BUILDERCODE(-21003, "找不到施工单位代号"),
//    ADD_CONTRACT_GRADE_PRICE_ERROR(-210004, "修改合同标号价格失败"),


    ADD_CONTRACT_GRADE_PRICE_ERROR(-21010, "修改合同标号价格失败"),
    ADD_CONTRACTDISTANCESET_ERROR(-21011, "该站距记录已经存在，请从新输入"),
    ADD_CONTRACTPRICEMARKUP_ERROR(-21012, "添加特殊材料失败！"),



    /* 任务单 21101-21199*/
    ADD_TASK_NOT_FOUND_COMPID(-21101, "找不到企业ID"),
    ADD_TASK_NOT_FOUND_TASKID(-21102, "找不到任务单号"),
    ADD_TASK_NOT_FOUND_PRETIME(-21103, "预计时间不能为空！"),
    ADD_TASK_NOT_FOUND_CONTRACT(-21104, "请选择合同！"),
    ADD_TASK_NOT_FOUND_EPP(-21105, "找不到工程名称"),
    ADD_TASK_NOT_FOUND_BUILDER(-21106, "找不到施工单位"),
    ADD_TASK_NOT_FOUND_STGID(-21107, "砼标号不能为空"),
    ADD_TASK_ERROR(-21108, "添加任务单失败"),
    ADD_WMCONFIGUREAPPLY_ERROR(-21109, "添加配件失败"),
    EDIT_WMCONFIGUREAPPLY_ERROR(-21109, "修改配件失败"),
    VERIFY_TASK_ERROR(-21120, "编辑任务单审核状态失败"),

    /* 附件 21201-21299 */
    ADD_CONTRACT_ADJUNCT_UPLOAD_ERROR(-21201, "上传合同附件失败"),
    ADD_CONTRACT_ADJUNCT_UPLOAD_FILE_CONNOT_NULL(-21202, "附件不能为空"),
    ADJUNCT_NOT_EXIST(-21203, "附件不存在"),
    ADJUNCT_STREAM_ERROR(-21204, "文件传输失败"),
    ADJUNCT_SAVE_ERROR(-21205,"保存失败,请重新添加泵车类价格，合同号可能已经存在"),
    ADJUNCT_UPDATE_ERROR(-21207,"修改失败,请重新检查后修改"),
    VERIFY_TICKET_ERROR(-21206, "小票审核状态失败"),
    ADD_PERSONALCODE_ERROR(-21208, "请选择司机"),
    ADD_VEHICLEID_ERROR(-21209, "请选择车号"),
    ADD_WORKCLASS_ERROR(-21210, "请选择班次"),
    SELECT_VEHICLEWORK_ERROR(-21210, "暂无数据"),
    ADD_INVITATION_ERROR(-21211, "生成邀请码失败"),
    ADD_USER_PHONE_EXIST(-21212, "手机号已注册！"),
    INVITATION_USESTATUS_EXIST(-21213, "邀请码已使用！"),
    INVITATION_USESTATUS_VOID(-21213, "邀请码已作废！"),
    INVITATION_COMPID_VOID(-21213, "重复绑定该企业！"),
    INVITATION_NULL(-21215, "邀请码不存在！"),
    ADD_ERROR(-21214, "添加失败！"),
    USER_LOGIN_ERROR(-21216, "用户名或密码错误！"),

    ;


    private Integer code;
    private String message;

    public Integer getCode() {
        return code;
    }


    public String getMessage() {
        return message;
    }

    ErrEumn(Integer code, String message) {
        this.message = message;
        this.code = code;
    }

}
