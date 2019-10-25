package com.hntxrj.server;

import com.alibaba.fastjson.JSONArray;
import com.hntxrj.entity.DeMenuInfo;
import com.hntxrj.entity.UserEmployee;
import com.hntxrj.entity.UserPermissionGroup;
import com.hntxrj.vo.MenuVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 功能:  权限服务层
 *
 * @Auther 李帅
 * @Data 2017-09-04.上午 10:39
 */
public interface PermissService {

    /**
     *  加载权限组已选中ID
     * @param groupId  权限组ID
     * @return
     */
    JSONArray loadPermi(String groupId, String compid );



    /* @author lhr */


    /**
     * 通过uri获取菜单
     * @param uri
     * @return
     */
    DeMenuInfo useUriGetMenu(String uri);


    /**
     * 通过token和菜单获取权限
     * @param token
     * @param menufids
     * @return
     */
    List<Map<String, Object>> useTokenAndMenuGetPermission(
            String token, List<Integer> menufids);


    /**
     * 判断权限方法
     * @param token 用户登录身份认证
     * @param uri   地址
     * @param request Request
     * @return
     */
    Boolean auth(String token, String uri, HttpServletRequest request);

    List<Map<String, Object>> getMenu(Integer fid, String token);

    /**
     *  根据Token获取权限
     * @param token token
     * @return {@link List}
     */
    List<Map<String,Object>> getPermissionForToken(String token);


    Boolean adminAuth(String name, String token);


    /**
     * 获取全部菜单列表
     * @return 菜单列表
     */
    List<DeMenuInfo> getMenuList();


    MenuVO updateMenu(MenuVO menuVO);

    void delectMenu(Integer id);


    Page<UserPermissionGroup> getUserPermissionGroup(
            String compId, Integer page);


    /**
     * 停用权限组
     * @param id    权限组id
     */
    void stopGroup(Integer id);


    void useGroup(Integer id);

    void delectGroup(Integer id);

    boolean updateGroupPermissionValue(
            String groupId, String compid, String token, List<MenuVO> menuVOS);


    /**
     * 获取带权限的权限列表
     * @param groupId    权限组id
     * @param compid     企业id
     * @param token      用户token
     * @return
     */
    List<MenuVO> getGroupPermissionValue(
            String groupId, String compid, String token);


    /**
     * 通过compid和token获取用户
     * @param compid    企业id
     * @param token     token
     * @return
     */
    List<UserEmployee> getUserEmployee(Integer compid, String token);


    Page<UserPermissionGroup> findPermissionGroup(Pageable pageable);


}
