package com.hntxrj.txerp.mapper;

import com.github.pagehelper.Page;
import com.hntxrj.txerp.entity.base.User;
import com.hntxrj.txerp.entity.base.UserAccount;
import com.hntxrj.txerp.entity.base.UserAuth;
import com.hntxrj.txerp.vo.AuthGroupVO;
import com.hntxrj.txerp.vo.UserListVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {

    List<User> details(Integer userId);

    List<AuthGroupVO> findbyId(Integer userId);

    UserAccount type(Integer userId);


    List<UserAuth> selectUserList(Integer enterpriseId, String userName, String phone, String email);
    List<UserListVO>  getUserList(Integer enterpriseId, String userName, String phone, String email);
}
