package com.hntxrj.txerp.server.impl;

import com.alibaba.fastjson.JSONArray;
import com.hntxrj.txerp.dao.UserEmployeeDao;
import com.hntxrj.txerp.entity.UserEmployee;
import com.hntxrj.txerp.server.UserEmployeeService;
import com.hntxrj.txerp.util.jdbc.sql.Limit;
import com.hntxrj.txerp.util.jdbc.sql.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 功能:  用户服务层接口实现层
 *
 * @Auther 李帅
 * @Data 2017-08-11.下午 2:46
 */
@Service
@Scope("prototype")
public class UserEmployeeServiceImpl implements UserEmployeeService {

    private final UserEmployeeDao userEmployeeDao;

    @Autowired
    public UserEmployeeServiceImpl(UserEmployeeDao userEmployeeDao) {
        this.userEmployeeDao = userEmployeeDao;
    }

    /**
     * 用户登录
     *
     * @param username 用户名
     * @param userpwd  密码
     * @return
     */
    @Override
    public JSONArray userLogin(String username, String userpwd, Integer type) {
        return userEmployeeDao.userLogin(username, userpwd, type);
    }

    /**
     * 登录记录
     *
     * @param opid   操作员ID
     * @param ip     IP地址
     * @param token  token
     * @param driver 设备
     * @return
     */
    @Override
    public JSONArray insertUserHistory(String opid, String ip, String token, String driver, String compid) {
        return userEmployeeDao.insertUserHistory(opid, ip, token, driver, compid);
    }

    /**
     * 检查token是否存在
     *
     * @param token 唯一值
     * @return
     */
    @Override
    public boolean checkToken(String token) {
        return userEmployeeDao.checkToken(token);
    }


    /**
     * 加载权限信息
     *
     * @param grade 几级菜单
     * @param id    父ID
     * @return
     */
    @Override
    public JSONArray loadMenuInfo(Integer grade, Integer id) {
        return userEmployeeDao.loadMenuInfo(grade, id);
    }

    /**
     * 增/删/改 权限组
     *
     * @param mark        标记 1 添加 2 修改 3 删除
     * @param groupName   权限组名
     * @param groupClass  权限组uuid
     * @param groupStatus 权限组状态
     * @param createUser  创建人
     * @return
     */
    @Override
    public JSONArray inserUpdateDel_User_PermissionGroup(Integer mark, String groupName, String groupClass, String groupStatus, String createUser, String compid_id) {
        return userEmployeeDao.inserUpdateDel_User_PermissionGroup(mark, groupName, groupClass, groupStatus, createUser, compid_id);
    }

    /**
     * 修改密码
     *
     * @param compid    企业ID
     * @param loginname 登录名
     * @param pwd       密码
     * @param newPwd    新密码
     * @return
     */
    @Override
    public JSONArray editPwd(String compid, String loginname, String pwd, String newPwd) {
        return userEmployeeDao.editPwd(compid, loginname, pwd, newPwd);
    }


    /**
     * 修改用户的图片
     *
     * @param idphoto 图片
     * @param compid  企业
     * @param opid    用户
     * @return json
     */
    @Override
    public JSONArray edit_employee_idphoto(String idphoto, String compid, String opid, Integer mark) {
        return userEmployeeDao.edit_employee_idphoto(idphoto, compid, opid, mark);
    }

    @Override
    public Page<UserEmployee> findUser(String compId, String name, Limit limit) {
        return userEmployeeDao.findUser(compId, name, limit);
    }

    @Override
    public UserEmployee findUser(String compid, String loginName) {
        return userEmployeeDao.findUser(compid, loginName);
    }

    @Override
    public UserEmployee findUserByOpId(String compid, String opId) {
        return userEmployeeDao.findUserByOpId(compid, opId);
    }

    @Override
    public void saveUser(UserEmployee userEmployee) {

    }


    /**
     * 取消微信绑定
     *
     * @param token 用户token
     * @return
     */
    @Override
    public JSONArray cancelWechatBind(String token) {
        return userEmployeeDao.cancelWechatBind(token);
    }


    /**
     * 通过企业ID获取用户列表
     *
     * @param 企业ID compid
     * @return  List<UserEmployee>  用户列表
     */
    @Override
    public List<UserEmployee> getUserListByCom(String comid) {
        return userEmployeeDao.getUserListByCom(comid);
    }


}
