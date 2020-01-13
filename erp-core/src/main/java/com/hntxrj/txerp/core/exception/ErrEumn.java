package com.hntxrj.txerp.core.exception;

public enum ErrEumn {

    /* 企业20100-20199 */
    ADD_ENTERPRISE_NAME_NULL(-20100, "添加企业名称不能为空"),
    ADD_ENTERPRISE_ERROR(-20101, "添加企业失败"),
    UPDATE_ENTERPRISE_ERROR(-20102, "编辑企业失败"),
    DELETE_ENTERPRISE_ERROR(-20103, "删除企业失败"),


    /* 合同21000-21099 */
    ADD_CONTRACT_NOT_FOUND_COMPID(-21001, "找不到企业id"),
    ADD_CONTRACT_NOT_FOUND_CONTRACTID(-21002, "找不到合同代号"),
    ADD_CONTRACT_NOT_FOUND_EPPCODE(-21003, "找不到工程代号"),
    ADD_CONTRACT_NOT_FOUND_BUILDERCODE(-21005, "找不到施工单位代号"),
//    ADD_CONTRACT_GRADE_PRICE_ERROR(-210004, "修改合同标号价格失败"),


    ADD_CONTRACT_GRADE_PRICE_ERROR(-21010, "修改合同标号价格失败"),
    ADD_CONTRACTDISTANCESET_ERROR(-21011, "该站距记录已经存在，请从新输入"),
    ADD_CONTRACTPRICEMARKUP_ERROR(-21012, "添加特殊材料失败！"),
    VERIFY_CONTRACT_ERROR(-21013, "合同已经审核，无法编辑！"),


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
    ADJUNCT_SAVE_ERROR(-21205, "保存失败,请重新添加泵车类价格，合同号可能已经存在"),
    DRIVER_SAVE_ERROR(-21205, "添加失败,请重新选择排班信息"),
    ADJUNCT_UPDATE_ERROR(-21207, "修改失败,请重新检查后修改"),
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

    USER_NO_EXIT(-10000, "用户不存在"),
    ADD_USER_ERR(-10001, "添加用户失败"),
    UPDATE_USER_PARAMS_ERR(-10002, "修改用户-非法参数"),
    UPDATE_USER_ERR(-10003, "编辑用户失败"),
    DELECT_USER_ERR(-10004, "删除用户失败"),
    LOGIN_ERR(-10005, "用户名或密码错误"),
    EXPIRE_TOKEN(-10006, "身份认证过期,请重新登录"),
    PARAM_IS_NULL(-10007, "参数为空"),
    OLD_PASSWORD_ERROR(-10007, "旧密码输入错误"),


    ADD_USER_UID_IS_NULL(-10008, "uid不能为空！"),
    ADD_USER_PASSWORD_IS_NULL(-10008, "密码不能为空！"),
    ADD_USER_USERNAME_IS_NULL(-10009, "用户名不能为空！"),
    ADD_USER_PHONE_IS_NULL(-10010, "手机号不能为空！"),
    ADD_USER_STATUS_IS_NULL(-10011, "状态不能为空！"),
    ADD_USER_ENTERPRISE_IS_NULL(-10012, "企业不能为空！"),
    ADD_USER_IS_NULL(-10012, "添加用户对象为空！"),

    AUTH_GROUP_NULL(-10015, "权限组对象为空！"),
    ENTERPRISE_NULL(-10016, "企业id为空！"),
    UPDATEUSER_NULL(-10017, "更新用户为空！"),
    AUTH_GROUP_NAME_NULL(-10018, "权限组名称为空！"),

    EDIT_AUTH_GROUP_ERROR(-10013, "编辑权限组失败！"),


    TOKEN_IS_NULL(-10014, "token为空！"),


    ADD_ENTERPRISE_NULL(-10019, "企业对象不能为空！"),
    ADD_ENTERPRISE_SHORT_NAME_NULL(-10020, "企业简称不能为空！"),
    ENTERPRISE_NAME_EXIST(-10023, "企业名称已存在"),
    ENTERPRISE_id_EXIST(-100234, "企业id已存在"),
    ENTERPRISE_NOEXIST(-10024, "企业不存在!"),
    ENTERPRISE_ID_NOTEXIST(-10025, "企业代号不存在!"),

    PUBLIC_INFO_NOTEXIST(-10026, "公共信息不存在!"),
    AUTH_PASSWORD_FAIL(-10027, "密码认证失败"),


    NOT_LOGIN(-20000, "未登录请先登录！"),

    NOT_PERMISSION(-20001, "您没有该操作的权限，请联系系统管理员！"),

    NOT_BIND_ACCOUNT(-20002, "没有绑定账号！"),

    OPENID_TYPE_CANNOT_NULL(-20003, "绑定账号和账号类型不能为空！"),
    THIS_OPENID_USED(-20004, "该账号已绑定用户！"),
    THIS_WECHAT_BIND_ACCOUNT_TOO_MANY(-20005, "该微信绑定帐号过多!"),

    ENTERPRISE_NOT_OPEN_PROJECT(-20006, "该企业未开启该项目！"),

    USER_CANNOT_LOGIN(-20007, "该帐号被禁止登录！"),

    ACCOUNT_NUMBER_BIND_WECHAT_CANNOT_LOGIN(-20008, "该帐号已绑定微信，禁止登录"),
    _PID_NOT_FIND_IN_HEADER(-20009, "在请求头中找不到pid"),
    THE_USER_ALREADY_BOUND_THIS_TYPE_ACCOUNT(-20010, "该用户已经绑定过该类型帐号了!"),

    ONLY_ADMINISTRATOR_CAN_DO_IT(-20011, "只有超级管理员能进行此操作！"),

    PROJECT_LIFE_SPAN_NOT_EXIST(-20012, "时长不存在！"),

    SEND_AUTH_CODE_ERROR(-20013, "发送验证码失败！"),

    AUTH_CODE_ERROR(-20014, "验证码错误!"),


    /*
    销售模块异常
     */
    // 工程名称
    ENGINEERING_ID_CANNOT_NULL_OR_ZERO(-100001, "工程名称不能为空！"),

    ENGINEERING_CODE_CANNOT_NULL(-100002, "工程代号不能为空!"),

    ENGINEERING_FULL_NAME_CANNOT_NULL(-100003, "工程全称不能为空！"),

    ENGINEERING_SHORT_NAME_CANNOT_NULL(-100004, "工程简称不能为空！"),
    ENGINEERING_NOT_FIND(-100004, "找不到工程！"),


    // 施工单位
    BUILDER_CODE_CANNOT_NULL(-100005, "施工代号不能为空"),

    BUILDER_NAME_CANNOT_NULL(-100006, "施工名称不能为空"),
    BUILDER_NOT_FIND(-100007, "找不到施工单位"),


    // 合同
    ADD_CONTRACRT_ERROR(-100100, "添加合同失败"),
    USER_NOT_IN_ENTERPRISE(-100101, "用户不在该查询的企业中"),
    CONTRACT_SALESMANUID_NULL(-100102, "销售员代号不能为空"),
    CONTRACT_CMTYPE_NULL(-100103, "合同类型不能为空"),
    CONTRACT_PRICESTYLE_NULL(-100104, "价格执行方式不能为空"),
    CONTRACT_CMUID_NULL(-100104, "合同UID不能为空"),

    CONTRACRT_NOT_FOUND(-100105, "找不到主合同！"),

    CONTRACRT_DETAILS_NOT_FOUND(-100106, "找不到子合同"),
    STG_ID_CANNOT_NULL(-100107, "标号不能为空"),
    PROJECT_NAME_NULL(-100108, "项目名不能为空"),
    PROJECT_CODE_NULL(-100109, "项目代号不能为空"),
    PUMP_TYPE_NAME_NULL(-100110, "泵车类别名称不能为空！"),
    PUMP_TYPE_CODE_NULL(-100111, "泵车类别代号不能为空！"),


    ADD_ENTERPRISE_AFTER_SALE_ERROR(-100112, "添加企业售后信息失败！"),

    DING_GET_ACCESS_ERROR(-100113, "连接钉钉服务器失败！"),
    BIND_VALUE_NULL(-100114, "绑定账号为空！"),


    ENTERPRISE_AFTER_SALE_LOG_ADD_ERROR(-100115, "企业售后信息添加失败！"),
    ENTERPRISE_AFTER_SALE_LOG_NOT_FOUND(-100116, "找不到售后信息！"),


    UPLOAD_FILE_ERROR(-100117, "上传文件失败"),
    DOWNLOAD_FILE_ERROR(-100118, "下载文件失败！"),

    PROJECT_HEAD_MAN_CANNOT_NULL(-100119, "项目负责人不能为空"),
    PROJECT_CHECK_MAN_CANNOT_NULL(-100120, "项目验收人不能为空"),
    PROJECT_SAVE_ERROR(-100121, "项目保存失败"),
    PROJECT_NOT_FOUND(-100122, "项目不存在"),
    ONLY_CHECK_MAN_OR_HEAD_MAN_CAN_START_PROJECT(-100123, "只有项目负责人和项目验收人可以开始项目"),
    ONLY_HEAD_MAN_CAN_FINSH_PROJECT(-100124, "只有项目负责人可以完成项目"),
    ONLY_CHECK_MAN_CAN_CHECK_PROJECT(-100125, "只有项目验收人可以验收项目"),

    WORK_RECORD_ADD_ERROR(-100126, "添加待办事项失败！"),
    WORK_RECORD_UP_ERROR(-100127, "更新待办事项失败！"),
    ADD_SYSTEM_LOG_ERROR(-100128, "添加系统日志失败！"),
    ENTERPRISE_TYPE_NOTEXIST(-100129, "企业状态不存在"),
    SETTING_ENTERPRISE_NOT_NULL(-100130, "企业id不存在"),
    SETTING_VALUE_NOT_NULL(-100131, "设置值不可为空"),
    SETTING_CREATEUID_NOT_NULL(-100132, "创建人ID不可为空"),
    IMGAGDFILE_FAIL(-100133, "读取地址失败"),
    TO_TRANSFARTO_IMGFILE_FAIL(-100134, "保存文件失败"),
    NOT_FOUNDNOT_FILE(-100135, "找不到文件"),
    JOURNALISM_NEWS_NULL(-100135, "新闻不存在"),
    JOURNALISM_TITLE_NULL(-100136, "新闻标题为空"),
    JOURNALISM_CONTENT_NULL(-100137, "新闻内容为空"),
    JOURNALISM_IMG_NULL(-100138, "新闻图片为空"),


    add_feedback_error(-100201, "反馈失败"),



    ADD_EPP_ADDRESS_EPP_CODE_IS_NULL(-100202, "工程代号不能为空"),

    ;


    private Integer code;
    private String message;

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    ErrEumn(Integer code, String message) {
        this.message = message;
        this.code = code;
    }


    public static ErrEumn getEumn(Integer code) {
        for (ErrEumn errEumn : values()) {
            if (errEumn.getCode().equals(code)) {
                return errEumn;
            }
        }
        return null;
    }


}
