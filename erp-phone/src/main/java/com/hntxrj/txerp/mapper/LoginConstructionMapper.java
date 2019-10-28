package com.hntxrj.txerp.mapper;

import com.hntxrj.txerp.vo.BuildAccountsVO;
import com.hntxrj.txerp.vo.InvitationVO;
import com.hntxrj.txerp.vo.LoginClibationVO;

public interface LoginConstructionMapper {
    //校对账户密码
    LoginClibationVO getAccountPassword(String userName, String passWord);

    BuildAccountsVO findBybuildId(String userName);

    InvitationVO findByid(String buildInvitationCode);

    Integer save(String userName, String passWord);

}
