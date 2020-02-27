package com.hntxrj.txerp.server;


import com.alibaba.fastjson.JSONArray;
import com.hntxrj.txerp.entity.UserEmployee;
import com.hntxrj.txerp.util.jdbc.sql.Limit;
import com.hntxrj.txerp.util.jdbc.sql.Page;

import java.util.List;

/**
 * 功能:  用户服务层接口
 *
 * @Auther 李帅
 * @Data 2017-08-11.下午 2:45
 */
public interface UserEmployeeService {

    /**
     * 用户登录
     *
     * @param username 用户名
     * @param userpwd  密码
     * @return
     */
    JSONArray userLogin(String username, String userpwd, Integer type);


    /**
     * 登录记录
     *
     * @param opid   操作员ID
     * @param ip     IP地址
     * @param token  token
     * @param driver 设备
     * @return
     */
    JSONArray insertUserHistory(String opid, String ip, String token, String driver, String compid);


    /**
     * 检查token是否存在
     *
     * @param token 唯一值
     * @return
     */
    boolean checkToken(String token);


    /**
     * 加载菜单信息
     *
     * @param grade 几级菜单
     * @param id    父ID
     * @return
     */
    JSONArray loadMenuInfo(Integer grade, Integer id);


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
    JSONArray inserUpdateDel_User_PermissionGroup(Integer mark, String groupName, String groupClass, String groupStatus, String createUser, String compid_id);

    /**
     * 修改密码
     *
     * @param compid    企业ID
     * @param loginname 登录名
     * @param pwd       密码
     * @param newPwd    新密码
     * @return
     */
    JSONArray editPwd(String compid, String loginname, String pwd, String newPwd);


    //------------------------------

    /**
     * 修改用户的图片
     *
     * @param idphoto 图片
     * @param compid  企业
     * @param opid    用户
     * @return json
     */

    JSONArray edit_employee_idphoto(String idphoto, String compid, String opid, Integer mark);


    /**
     * 查询用户
     *
     * @param compId 企业id
     * @param name   用户名
     * @param limit  分页
     * @return 查询结果
     */
    Page<UserEmployee> findUser(String compId, String name, Limit limit);

    UserEmployee findUser(String compidm, String loginName);

    UserEmployee findUserByOpId(String compidm, String opId);


    void saveUser(UserEmployee userEmployee);


    /**
     * 取消微信绑定
     *
     * @param token 用户token
     * @return {@link JSONArray}
     */
    JSONArray cancelWechatBind(String token);

    /**
     * 通过企业ID获取用户列表
     *
     * @param 企业ID compid
     * @return  List<UserEmployee>  用户列表
     */
    List<UserEmployee> getUserListByCom(String compid);
}
