package com.hntxrj.txerp.mapper;

import com.hntxrj.txerp.vo.BuildAccountsVO;
import com.hntxrj.txerp.vo.InvitationVO;
import com.hntxrj.txerp.vo.LoginClibationVO;

public interface LoginConstructionMapper {
   /**
    * 用户登录校验
    * */
    LoginClibationVO getAccountPassword(String userName, String passWord);

    /**
     *查询施工方用户
     * */
    BuildAccountsVO findByBuildId(String userName);

    /**
     * 查询邀请码使用状态
     * */
    InvitationVO findById(String buildInvitationCode);

    /**
     * 添加用户
     * */
    Integer save(String userName, String passWord,String buildName);

    /**
     * 查询用户信息
     * */
    BuildAccountsVO findUser(String buildId);

    /**
     * 修改密码
     * */
    void updatePassword(String buildId, String newPassword);
}
