package com.hntxrj.txerp.im;

import com.alibaba.fastjson.JSONObject;
import com.hntxrj.txerp.core.exception.ErpException;
import com.hntxrj.txerp.vo.IMUserVO;

import java.util.List;

/**
 * 账号管理
 */
public interface AccountService {


    /**
     * 单个帐号导入接口（已经导入了100个账号，达到上限；等腾讯云账号升级为专业版之后，可以继续导入）
     *
     * @param imUserVO 消息用户对象
     * @return 请求结果
     */
    com.alibaba.fastjson.JSONObject accountImport(IMUserVO imUserVO) throws ErpException;


    /**
     * 批量帐号导入接口（把所有通信软件的用户导入即时通讯的账号；
     * 目前因为使用的是腾讯云即时通讯IMSDK的体验账号，所以只能导入100个）
     *
     * @return 请求结果
     */
    com.alibaba.fastjson.JSONObject multiAccountImport() throws ErpException;

    /**
     * 帐号删除接口
     *
     * @return 请求结果
     */
    com.alibaba.fastjson.JSONObject accountDelete(List<String> imUserIDs) throws ErpException;


    /**
     * 帐号检查接口
     * 用于检查自有帐号是否已导入即时通信 IM，支持批量帐号检查。
     *
     * @return 请求结果
     */
    JSONObject accountCheck(List<String> imUserIDs) throws ErpException;

    /**
     * 帐号登录态失效接口
     * 本接口适用于将 App 用户帐号的登录态（如 UserSig）失效。
     * 例如，开发者判断一个用户为恶意帐号后，可以调用本接口将该用户当前的登录态失效，
     * 这样用户使用历史 UserSig 登录即时通信 IM 会失败。
     *
     * @return 请求结果
     */
    com.alibaba.fastjson.JSONObject kick(IMUserVO imUserVO) throws ErpException;
}
