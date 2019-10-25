package com.hntxrj.dao.impl;

import com.alibaba.fastjson.JSONArray;
import com.hntxrj.dao.UserEmployeeDao;
import com.hntxrj.entity.UserEmployee;
import com.hntxrj.util.LogUtil;
import com.hntxrj.util.jdbc.procedure.Param;
import com.hntxrj.util.jdbc.procedure.Procedure;
import com.hntxrj.util.jdbc.enums.ParamType;
import com.hntxrj.util.jdbc.sql.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 功能:  用户Dao层方法
 *
 * @Auther 李帅
 * @Data 2017-08-11.下午 2:39
 */
@Component
@Scope("prototype")
public class UserEmployeeDaoImpl implements UserEmployeeDao {
    @Autowired
    public UserEmployeeDaoImpl(SqlBuilder sqlBuilder, Procedure procedure) {
        this.sqlBuilder = sqlBuilder;
        this.procedure = procedure;
    }


    private final SqlBuilder sqlBuilder;

    private final Procedure procedure;


    /**
     * 用户登录
     *
     * @param username 用户名
     * @param userpwd  密码
     * @return jsoN
     */
    @Override
    public JSONArray userLogin(String username, String userpwd, Integer type) {

        List<Param> params = new ArrayList<>();
        params.add(new Param(1, ParamType.INPARAM.getType(), type));
        params.add(new Param(2, ParamType.INPARAM.getType(), username));
        params.add(new Param(3, ParamType.INPARAM.getType(), userpwd));

        //type==2  为管理员登录
        procedure.init("sp_check_User_employee", params);

        try {
            procedure.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        JSONArray result = procedure.getResultArray();

        LogUtil.procedureInfo("sp_check_User_employee", params, result);

        return result;
    }

    /**
     * @param opid   操作员ID
     * @param ip     IP地址
     * @param token  token
     * @param driver 设备
     * @return
     */
    @Override
    public JSONArray insertUserHistory(String opid, String ip, String token,
                                       String driver, String compid) {
        List<Param> params = new ArrayList<>();
        params.add(new Param(1, ParamType.INPARAM.getType(), opid));
        params.add(new Param(2, ParamType.INPARAM.getType(), ip));
        params.add(new Param(3, ParamType.INPARAM.getType(), token));
        params.add(new Param(4, ParamType.INPARAM.getType(), driver));
        params.add(new Param(5, ParamType.INPARAM.getType(), compid));

        procedure.init("sp_User_Inser_History", params);

        try {
            procedure.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return procedure.getResultArray();
    }

    /**
     * 检查token是否存在
     *
     * @param token 唯一值
     * @return
     */
    @Override
    public boolean checkToken(String token) {
        List<Param> params = new ArrayList<>();
        params.add(new Param(1, ParamType.INPARAM.getType(), 1));
        params.add(new Param(2, ParamType.INPARAM.getType(), token));


        procedure.init("sp_User_Query_History", params);

        try {
            procedure.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        int num = Integer.parseInt(procedure.getResultArray()
                .getJSONArray(0).getJSONObject(0)
                .get("num").toString());

        return num > 0;
    }


    /**
     * 加载菜单信息
     *
     * @param grade 几级菜单
     * @param id    父ID
     * @return
     */
    @Override
    public JSONArray loadMenuInfo(Integer grade, Integer id) {
        List<Param> params = new ArrayList<>();
        params.add(new Param(1, ParamType.INPARAM.getType(), grade));
        params.add(new Param(2, ParamType.INPARAM.getType(), id));


        procedure.init("sp_Query_MenuInfo", params);

        try {
            procedure.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return procedure.getResultArray();
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
        List<Param> params = new ArrayList<Param>();
        params.add(new Param(1, ParamType.INPARAM.getType(), mark));
        params.add(new Param(2, ParamType.INPARAM.getType(), groupName));
        params.add(new Param(3, ParamType.INPARAM.getType(), groupClass));
        params.add(new Param(4, ParamType.INPARAM.getType(), groupStatus));
        params.add(new Param(5, ParamType.INPARAM.getType(), createUser));
        params.add(new Param(6, ParamType.INPARAM.getType(), compid_id));


        procedure.init("sp_InserUpdateDel_User_PermissionGroup", params);

        try {
            procedure.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return procedure.getResultArray();
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
        List<Param> params = new ArrayList<Param>();
        params.add(new Param(1, ParamType.INPARAM.getType(), compid));
        params.add(new Param(2, ParamType.INPARAM.getType(), loginname));
        params.add(new Param(3, ParamType.INPARAM.getType(), pwd));
        params.add(new Param(4, ParamType.INPARAM.getType(), newPwd));
        procedure.init("sp_User_Updata_Pwd", params);
        try {
            procedure.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return procedure.getResultArray();
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
        List<Param> params = new ArrayList<Param>();
        params.add(new Param(1, ParamType.INPARAM.getType(), mark));
        params.add(new Param(2, ParamType.INPARAM.getType(), compid));
        params.add(new Param(3, ParamType.INPARAM.getType(), opid));

        params.add(new Param(4, ParamType.INPARAM.getType(), idphoto));
        procedure.init("edit_employee_idphoto", params);
        try {
            procedure.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return procedure.getResultArray();
    }

    @Override
    public Page<UserEmployee> findUser(String compId, String name, Limit limit) {

        SqlBuilder sql = sqlBuilder.table(
                new Table("User_employee", UserEmployee.class));

        sql.where(new Where("emptype", " is not ", "null"));
        if (compId != null) {
            sql.where(new Where("compid", compId));
        }
        if (name != null) {
            sql.where(new Where("name", "like", name));
        }
        //去掉pcerp数据
        sql.where(new Where("len(opid)", ">", "4"));
        if (limit != null) {
            sql.limit(limit);
        }

        return sql.select().page();
    }

    @Override
    public UserEmployee findUser(String compidm, String loginName) {
        loginName = "'" + loginName + "'";
        SqlBuilder sql = sqlBuilder.table("User_employee", UserEmployee.class)
                .where("compid", compidm)
                .where("loginname", loginName)
                .select();
        return (UserEmployee) sql.getResult().get(0);
    }

    @Override
    public UserEmployee findUserByOpId(String compid, String opid) {
        opid = "'" + opid + "'";
        SqlBuilder sql = sqlBuilder.table("User_employee", UserEmployee.class)
                .where("compid", compid)
                .where("opid", opid)
                .select();

        return (UserEmployee) sql.getResult().get(0);
    }

    /**
     * 取消微信绑定
     *
     * @param token 用户token
     * @return {@link JSONArray}
     */
    @Override
    public JSONArray cancelWechatBind(String token) {
        List<Param> params = new ArrayList<Param>();
        params.add(new Param(1, ParamType.INPARAM.getType(), 1));
        params.add(new Param(2, ParamType.INPARAM.getType(), token));
        procedure.init("wechat_bind", params);
        try {
            procedure.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return procedure.getResultArray();
    }


}
