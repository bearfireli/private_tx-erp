package com.hntxrj.txerp.mapper;

import com.hntxrj.txerp.entity.base.*;
import com.hntxrj.txerp.vo.AuthGroupVO;
import com.hntxrj.txerp.vo.AuthValueVO;
import com.hntxrj.txerp.vo.UserListVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {

    List<User> details(Integer userId);

    List<AuthGroupVO> findbyId(Integer userId);

    UserAccount type(Integer userId);


    List<UserAuth> selectUserList(Integer enterpriseId, String userName, String phone, String email);

    void addUserStatus(Integer userId);

    void deleteUserStatus(Integer userId);

    List<UserListVO> getUserList(Integer enterpriseId, String userName, String phone, String email);

    List<String> getOpenAuth(Integer groupId);

    List<AuthValueVO> getAuthValue(Integer groupId);


    Integer getAuthGroupByUserAndCompid(Integer uid, Integer enterprise);

    Integer judgementAuth(Integer authGroupID, String methodName);

    Integer getMenuIdByFunName(String funName);


    List<AuthValue> getAuthValueByGroupId(Integer groupId,Integer pid);

    List<AuthValueOld> getAuthValueOld(Integer groupId, Integer pid);

    List<User> userAll(Integer eid);
}
