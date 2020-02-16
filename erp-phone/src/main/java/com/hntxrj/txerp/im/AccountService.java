package com.hntxrj.txerp.im;

import com.hntxrj.txerp.core.exception.ErpException;
import com.hntxrj.txerp.vo.IMUserVO;

import java.util.List;

/**
 * 账号管理
 */
public interface AccountService {


    /**
     * 单个帐号导入接口
     * @return 接口返回参数
     */
    Object accountImport(IMUserVO imUserVO) throws ErpException;


    /**
     * 批量帐号导入接口
     * @return 接口返回参数
     */
    Object multiAccountImport() throws ErpException;

    /**
     * 帐号删除接口
     * @return 接口返回参数
     */
    Object accountDelete(List<IMUserVO> imUserVOS) throws ErpException;


    /**
     * 帐号检查接口
     * 用于检查自有帐号是否已导入即时通信 IM，支持批量帐号检查。
     * @return 接口返回参数
     */
    Object accountCheck(List<IMUserVO> imUserVOS) throws ErpException;

    /**
     * 帐号登录态失效接口
     * 本接口适用于将 App 用户帐号的登录态（如 UserSig）失效。
     * 例如，开发者判断一个用户为恶意帐号后，可以调用本接口将该用户当前的登录态失效，这样用户使用历史 UserSig 登录即时通信 IM 会失败。
     * @return 接口返回参数
     */
    Object kick(IMUserVO imUserVO) throws ErpException;
}
