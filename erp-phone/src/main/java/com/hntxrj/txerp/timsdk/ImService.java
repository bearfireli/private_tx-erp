package com.hntxrj.txerp.timsdk;

import com.hntxrj.txerp.timsdk.po.UserStateResult;

/**
 * 消息服务
 */
public interface ImService {


    /**
     * 导入账号
     *
     * @param identifier 必填 用户名，长度不超过32字节
     * @param nick       用户昵称
     * @param faceUrl    用户头像 URL
     * @param type       帐号类型，开发者默认无需填写，值0表示普通帐号，1表示机器人帐号
     * @return 结果
     */
    Boolean accountImport(String identifier, String nick, String faceUrl, Integer type);


    /**
     * 查询账号在线状态
     *
     * @param accounts 要查询的账号id
     * @return 账号在线状态
     */
    UserStateResult queryState(String[] accounts);


    /**
     * 管理员向其它帐号发消息
     */
    void sendMsg();

}
