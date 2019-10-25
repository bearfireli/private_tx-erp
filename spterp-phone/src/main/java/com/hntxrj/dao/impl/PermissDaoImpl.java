package com.hntxrj.dao.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hntxrj.dao.DeMenuInfoDao;
import com.hntxrj.dao.PermissDao;
import com.hntxrj.dao.UserEmployeeDaoJpa;
import com.hntxrj.dao.UserLoginHistoryDao;
import com.hntxrj.entity.PermiUrl;
import com.hntxrj.util.jdbc.procedure.Param;
import com.hntxrj.util.jdbc.procedure.Procedure;
import com.hntxrj.util.jdbc.enums.ParamType;
import com.hntxrj.util.jdbc.sql.JdbcUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 功能: 权限控制Dao实现层
 *
 * @Auther 李帅
 * @Data 2017-09-03.下午 3:00
 */
@Slf4j
@Component
@Scope("prototype")
public class PermissDaoImpl implements PermissDao {

    private final JdbcUtil jdbcUtil;

    @Autowired
    public PermissDaoImpl(JdbcUtil jdbcUtil) {
        this.jdbcUtil = jdbcUtil;
    }


    @Autowired
    public void setProcedure(Procedure procedure) {
        this.procedure = procedure;
    }

    private Procedure procedure;

    /**
     * 根据token查询权限对应的url
     *
     * @param mark  标记
     * @param token 登录记录值
     * @param sql   sql语句
     * @return
     */
    public JSONArray managerPermiss(Integer mark, String token, String sql) {
        List<Param> list = new ArrayList<>();
        list.add(new Param(1, ParamType.INPARAM.getType(), mark));
        list.add(new Param(2, ParamType.INPARAM.getType(), token));
        list.add(new Param(3, ParamType.INPARAM.getType(), sql));
        log.info("【list】list={}", list);
        procedure.init("sp_Query_Token", list);
        try {
            procedure.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return procedure.getResultArray();
    }


    /**
     * 取出URL
     *
     * @param token
     * @return
     */
    @Override
    public List<String> formathData(String token) {

        // 通过token获取用户组
        JSONArray resultJsonArray = this.managerPermiss(1, token, null);

        List<String> list = new ArrayList<>();
        String str = resultJsonArray.toString();
        if (str.contains("token无效")) {
            System.out.println("无效的token");
        } else if (str.contains("未查询到用户组")) {
            System.out.println("未查询到用户组");
        } else {
            // 查询有结果
            System.out.println("【result】str={}" + str);
            JSONArray jr = resultJsonArray.getJSONArray(0);
            StringBuilder sql = new StringBuilder("select MenuDoUrl from De_MenuInfo where MenuFid is null ");
            for (Object object : jr) {
                if (object instanceof JSONObject) {
                    String menuID = ((JSONObject) object).get("MenuID").toString();

                    String v = this.getMaxBit(Integer.parseInt(menuID));

                    String permissionValue = ((JSONObject) object)
                            .get("PermissionValue").toString();

                    String sc = Integer.toBinaryString(Integer.parseInt(permissionValue));

                    StringBuilder ccc = new StringBuilder();

                    for (int i = 0; i < Integer.parseInt(v) - sc.toCharArray().length; i++) {
                        ccc.append("0");
                    }
                    ccc.append(sc);

                    char[] ch = ccc.toString().toCharArray();
                    StringBuilder whereSql = new StringBuilder(" -1 ");
                    for (int i = 0; i < ch.length; i++) {
                        char c = ch[i];
                        if ("1".equals(String.valueOf(c))) {
                            whereSql.append(", ").append(i);
                        }
                    }
                    sql.append("union   select MenuDoUrl from De_MenuInfo where MenuFid = ").append(menuID).append(" and  MenuDoTaxis in ( ").append(whereSql).append(" ) ");
                    //System.out.println("MenuID : " + menuID + "  PermissionValue : " + Integer.toBinaryString(Integer.parseInt(permissionValue)));
                }
            }
            System.out.println(sql);
            JSONArray dataArray = managerPermiss(2, null, sql.toString());
            System.out.println(dataArray.toString());
            for (Object obj : dataArray.getJSONArray(0)) {
                if (obj instanceof JSONObject) {
                    list.add(((JSONObject) obj).get("MenuDoUrl").toString());
                }
            }
        }
        return list;
    }

    @Override
    public List<Map<String, Object>> formathDataNew(String token) throws SQLException {
        return jdbcUtil.find("select menuurl as url from v_user_permission where token='" + token + "'");
    }


    /**
     * 取出URL  (方法二)
     *
     * @param token
     * @return
     */
    @Override
    public List<PermiUrl> formathDataTwo(String token) {

        JSONArray resultJsonArray = this.managerPermiss(1, token, "");

        List<PermiUrl> list = new ArrayList<>();

        String str = resultJsonArray.toString();

        if (str.contains("token无效")) {
            System.out.println("无效的token");
        } else if (str.contains("未查询到用户组")) {
            System.out.println("未查询到用户组");
        } else {
            JSONArray jr = resultJsonArray.getJSONArray(0);
            StringBuilder sql = new StringBuilder("select MenuUrl as url ,MenuDoUrl as api from De_MenuInfo where MenuFid is null ");
            for (Object object : jr) {
                if (object instanceof JSONObject) {
                    JSONObject jsonObject = JSONObject.parseObject(String.valueOf(object));

                    String menuID = jsonObject.getString("MenuID");

                    String v = this.getMaxBit(Integer.parseInt(menuID));

                    String permissionValue = ((JSONObject) object)
                            .get("PermissionValue").toString();

                    String sc = Integer.toBinaryString(Integer.parseInt(permissionValue));

                    StringBuilder ccc = new StringBuilder();

                    for (int i = 0; i < Integer.parseInt(v) - sc.toCharArray().length; i++) {
                        ccc.append("0");
                    }
                    ccc.append(sc);

                    char[] ch = ccc.toString().toCharArray();
                    StringBuilder whereSql = new StringBuilder(" -1 ");
                    for (int i = 0; i < ch.length; i++) {
                        char c = ch[i];
                        if ("1".equals(String.valueOf(c))) {
                            whereSql.append(", ").append(i);
                        }
                    }
                    sql.append("union select MenuUrl as url ,MenuDoUrl as api from De_MenuInfo where MenuFid = ").append(menuID).append(" and  MenuDoTaxis in ( ").append(whereSql).append(" ) ");
                }
            }
            JSONArray dataArray = managerPermiss(2, null, sql.toString());
            System.out.println(dataArray.toString());
            for (Object obj : dataArray.getJSONArray(0)) {
                if (obj instanceof JSONObject) {
                    list.add(JSONObject.toJavaObject((JSONObject) obj, PermiUrl.class));
                }
            }
        }
        return list;
    }


    /**
     * 加载权限组已选中ID
     *
     * @param groupId 权限组ID
     * @return
     */
    @Override
    public JSONArray loadPermi(String groupId, String compid) {

        JSONArray resultJsonArray = managerPermiss(3, groupId, compid);
        List<String> list = new ArrayList<>();
        String str = resultJsonArray.toString();
        JSONArray jr = resultJsonArray.getJSONArray(0);
        StringBuilder sql = new StringBuilder("select id from De_MenuInfo where MenuFid is null ");
        for (Object object : jr) {
            if (object instanceof JSONObject) {
                String menuID = ((JSONObject) object).get("MenuID").toString();

                String v = this.getMaxBit(Integer.parseInt(menuID));

                String permissionValue = ((JSONObject) object).get("PermissionValue").toString();

                String sc = Integer.toBinaryString(Integer.parseInt(permissionValue));

                StringBuilder ccc = new StringBuilder();
                /* 补位 */
                for (int i = 0; i < Integer.parseInt(v) - sc.toCharArray().length; i++) {
                    ccc.append("0");
                }
                ccc.append(sc);

                char[] ch = ccc.toString().toCharArray();

                StringBuilder whereSql = new StringBuilder(" -1 ");
                for (int i = 0; i < ch.length; i++) {
                    char c = ch[i];
                    if ("1".equals(String.valueOf(c))) {
                        whereSql.append(", ").append(i);
                    }
                }
                sql.append("union   select id from De_MenuInfo where MenuFid = ").append(menuID).append(" and  MenuDoTaxis in ( ").append(whereSql).append(" ) and MenuDoUrl != ''");
                //System.out.println("MenuID : " + menuID + "  PermissionValue : " + Integer.toBinaryString(Integer.parseInt(permissionValue)));
            }
        }
        System.out.println(sql);
        return managerPermiss(2, null, sql.toString());
    }

    /* 取最大位补位 */
    public String getMaxBit(Integer menuId) {
        List<Param> list = new ArrayList<Param>();
        list.add(new Param(1, ParamType.INPARAM.getType(), menuId));
        procedure.init("sp_Query_MenuListTest_MaxBit", list);
        try {
            procedure.commit();
        } catch (Exception e) {
            log.info(e.toString());
        }

        return procedure.getResultArray().getJSONArray(0).getJSONObject(0).get("MaxBit").toString();
    }


    public static void main(String[] args) {
//        System.out.println(new PermissDaoImpl().formathData("ED22A6FEB7926FB5AE0AE6B0B62A36E8"));
    }

}
