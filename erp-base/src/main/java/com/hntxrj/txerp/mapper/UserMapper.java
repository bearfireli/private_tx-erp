package com.hntxrj.txerp.mapper;

import com.hntxrj.txerp.entity.base.*;
import com.hntxrj.txerp.vo.AuthGroupVO;
import com.hntxrj.txerp.vo.AuthValueVO;
import com.hntxrj.txerp.vo.UserListVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {

    /*查询用户信息*/
    List<User> details(Integer userId);

    /*查询用户权限组*/
    List<AuthGroupVO> findById(Integer userId);

    UserAccount type(Integer userId);

    /*查询用户权限集合*/
    List<UserAuth> selectUserList(Integer enterpriseId, String userName, String phone, String email);

    /*添加用户超级管理里员权限*/
    void addUserStatus(Integer userId);

    /*删除用户超级管理员权限*/
    void deleteUserStatus(Integer userId);

    /*获取用户列表*/
    List<UserListVO> getUserList(Integer enterpriseId, String userName, String phone, String email);

    /*根据权限组id获取所有权限方法的集合*/
    List<String> getOpenAuth(Integer groupId);

    /*根据权限组id获取所有权限*/
    List<AuthValueVO> getAuthValue(Integer groupId);


    /*获取权限组id*/
    Integer getAuthGroupByUserAndCompid(Integer uid, Integer enterprise);

    /**
     * 判断该权限组是否具有某个方法的权限
     * */
    Integer judgementAuth(Integer authGroupID, String methodName);

    /*分局方法名称获取方法菜单id*/
    Integer getMenuIdByFunName(String funName);


    /*新版本获取权限组所有方法*/
    List<AuthValue> getAuthValueByGroupId(Integer groupId,Integer pid);

    /*老版本获取权限组所有方法*/
    List<AuthValueOld> getAuthValueOld(Integer groupId, Integer pid);

    /**
     * 查询所有企业所有用户
     * */
    List<User> selectAllUser();

    /*根据eid 查询企业用户*/
    List<User> userAll(Integer eid);
}
