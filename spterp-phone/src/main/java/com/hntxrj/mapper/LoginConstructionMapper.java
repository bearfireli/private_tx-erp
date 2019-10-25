package com.hntxrj.mapper;

import com.hntxrj.entity.BdBuildAccounts;
import com.hntxrj.vo.BuildAccountsVO;
import com.hntxrj.vo.InvitationVO;
import com.hntxrj.vo.LoginClibationVO;
import com.hntxrj.vo.bdBindVO;

import java.util.List;

public interface LoginConstructionMapper {
    //校对账户密码
    LoginClibationVO getAccountPassword(String userName,String passWord);

    BuildAccountsVO findBybuildId(String userName);

    InvitationVO findByid(String buildInvitationCode);

    Integer save(String userName, String passWord);

}
