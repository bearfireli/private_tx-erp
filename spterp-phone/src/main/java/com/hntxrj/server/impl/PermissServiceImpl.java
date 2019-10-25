package com.hntxrj.server.impl;

import com.alibaba.fastjson.JSONArray;
import com.hntxrj.dao.*;
import com.hntxrj.dao.UserEmployeeDaoJpa;
import com.hntxrj.entity.DeMenuInfo;
import com.hntxrj.entity.UserEmployee;
import com.hntxrj.entity.UserPermissionGroup;
import com.hntxrj.entity.UserPermissionGroupDetails;
import com.hntxrj.server.PermissService;
import com.hntxrj.util.jdbc.sql.JdbcUtil;
import com.hntxrj.util.jdbc.sql.Param;
import com.hntxrj.vo.MenuVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.util.*;

/**
 * 功能:
 *
 * @Auther 李帅
 * @Data 2017-09-04.上午 10:41
 */
@Service
@Scope("prototype")
public class PermissServiceImpl implements PermissService{


    private final DeMenuInfoDao deMenuInfoDao;

    private final UserEmployeeDaoJpa userEmployeeDaoJpa;

    private final UserLoginHistoryDao userLoginHistoryDao;

    private final PermissDao permissDao;

    private final UserPermissionGroupDetailsDao permissionGroupDetailsDao;

    private final UserPermissionGroupDao userPermissionGroupDao;


    private final JdbcUtil jdbcUtil;

    @Autowired
    public PermissServiceImpl(DeMenuInfoDao deMenuInfoDao, UserEmployeeDaoJpa userEmployeeDaoJpa, UserLoginHistoryDao userLoginHistoryDao, PermissDao permissDao, UserPermissionGroupDetailsDao permissionGroupDetailsDao, UserPermissionGroupDao userPermissionGroupDao, JdbcUtil jdbcUtil) {
        this.deMenuInfoDao = deMenuInfoDao;
        this.userEmployeeDaoJpa = userEmployeeDaoJpa;
        this.userLoginHistoryDao = userLoginHistoryDao;
        this.permissDao = permissDao;
        this.permissionGroupDetailsDao = permissionGroupDetailsDao;
        this.userPermissionGroupDao = userPermissionGroupDao;
        this.jdbcUtil = jdbcUtil;
    }

    /**
     * 加载权限组已选中ID
     * @param groupId  权限组ID
     * @return
     */
    @Override
    public JSONArray loadPermi(String groupId, String compid) {
        return permissDao.loadPermi(groupId,compid);
    }

    @Override
    public DeMenuInfo useUriGetMenu(String uri) {

        return deMenuInfoDao.findByMenuUrl(uri);
    }

    @Override
    public List<Map<String, Object>> useTokenAndMenuGetPermission(String token, List<Integer> menufids) {

        String sql = "select * from v_user_permission where Token=? and menufid in ({number})";

        StringBuilder number = new StringBuilder();
        for(int i=0;i<menufids.size(); i++){
            number.append("?, ");
        }

        sql = sql.replace("{number}", number.substring(0, number.length()-2));


        List<Param> params = new ArrayList<>();

        params.add(new Param(1, Param.STRING, token));

        int index = 2;
        for (Integer menufid : menufids){
            params.add(new Param(index, Param.INT, menufid));
            index++;
        }

        List<Map<String, Object>> result = jdbcUtil.findAll(sql,params);
        System.out.println(result);
        return result;
    }

    @Override
    public Boolean auth(String token, String uri, HttpServletRequest request) {
        boolean b = false;
        String sql = "SELECT * from v_user_permission where token=? and menudourl like ? ";
        List<Param> params = Arrays.asList(new Param(1, Param.STRING, token),
                new Param(2, Param.STRING, "%" + uri + "%"));
        List<Map<String,Object>> mapList = jdbcUtil.findAll(sql,params);
        //未获取到相似的 Api 接口地址    false 没权限
        if ( mapList.size() > 0 ){
            //遍历数据
            for ( int j = 0; j < mapList.size(); j++ ){
                if ( mapList.get(j) instanceof Map ){
                    Map<String,Object> l = (Map<String,Object>) mapList.get(j);
                    String u = l.get("menudourl").toString();
                    if ( u.contains("?") ){
                        int i = u.indexOf("=");
                        String paramName = u.substring(u.indexOf("?")+1,i);
                        String paramValue = u.substring(i + 1);
                        String urlParamValue = request.getParameter(paramName);
                        boolean bl = urlParamValue == null ? false :
                                (paramValue!= null && paramValue.equals(urlParamValue)) ? true : false;
                        if ( bl ){
                            b = true;
                            break;
                        }
                    }else {
                        b = true;
                        break;
                    }
                }
            }
        }
        return b;
    }

    /**
     *  根据token获取用户权限
     * @param token token
     * @return {@link JSONArray}
     */
    @Override
    public List<Map<String,Object>> getPermissionForToken(String token) {
        String sql = "SELECT * from v_user_permission where token=? ";
        List<Param> params = Arrays.asList(new Param(1, Param.STRING, token));
        return jdbcUtil.findAll(sql, params);
    }


    @Override
    public List<Map<String, Object>> getMenu(Integer fid, String token) {
        String sql = "SELECT * from v_user_permission where token=? and menufid=?";
        List<Param> params = Arrays.asList(new Param(1, Param.STRING, token),
                new Param(2, Param.STRING, token));
        return jdbcUtil.findAll(sql, params);
    }

    @Override
    public Boolean adminAuth(String name, String token) {
        return null;
    }

    @Override
    public List<DeMenuInfo> getMenuList() {
        return deMenuInfoDao.findAll();
    }

    @Override
    public MenuVO updateMenu(MenuVO menuVO) {

        DeMenuInfo deMenuInfo = new DeMenuInfo();

        BeanUtils.copyProperties(menuVO, deMenuInfo);

        deMenuInfo = deMenuInfoDao.save(deMenuInfo);

        BeanUtils.copyProperties(deMenuInfo, menuVO);

        return menuVO;
    }

    @Override
    public void delectMenu(Integer id) {
        deMenuInfoDao.deleteById(id);
    }

    @Override
    public Page<UserPermissionGroup> getUserPermissionGroup(
            String compId,
            Integer page) {
        if (page == null){
            page = 1;
        }
        if (compId == null || "".equals(compId)){
            return userPermissionGroupDao.findAll(PageRequest.of(page - 1, 10));
        }
        return userPermissionGroupDao.findByCompid(compId,
                PageRequest.of(page - 1, 10));
    }

    @Override
    public void stopGroup(Integer id) {
        jdbcUtil.update(
                "update User_PermissionGroup set groupStatus=?, updateTime=? where id=?",
                Arrays.asList(new Param(1, Param.STRING, "1"),
                        new Param(2, Param.DATE,
                                new Date(new java.util.Date().getTime())),
                        new Param(3, Param.INT, id)));
    }

    @Override
    public void useGroup(Integer id) {
        jdbcUtil.update(
                "update User_PermissionGroup set groupStatus=?, updateTime=? where id=?",
                Arrays.asList(new Param(1, Param.STRING, "0"),
                        new Param(2, Param.DATE,
                                new Date(new java.util.Date().getTime())),
                        new Param(3, Param.INT, id)));
    }

    @Override
    public void delectGroup(Integer id) {
        userPermissionGroupDao.deleteById(id);
    }


    @Override
    public boolean updateGroupPermissionValue(
            String groupId, String compid, String token, List<MenuVO> menuVOS) {


        List<UserPermissionGroupDetails> updateGroupDetails = new ArrayList<>();

        List<UserPermissionGroupDetails> oldUserPermissionGroupDetails
                = permissionGroupDetailsDao.findByGroupClass(groupId);


        // 获取menuvos中的所有checked为true的节点
        for (MenuVO menuVO : menuVOS){
            int exist = 0;
            for (UserPermissionGroupDetails oldUserPermissionGroupDetail
                    : oldUserPermissionGroupDetails){
                // 对比id进行更新
                if (oldUserPermissionGroupDetail.getMenuId().equals(menuVO.getId())){
                    exist = 1;
                    if (menuVO.getChecked()){
                        // 页面权限选中
                        if (oldUserPermissionGroupDetail.getPermissionValue() == 0){
                            // 原本的权限值为0,只有当权限值改变时才会进行更新
                            oldUserPermissionGroupDetail.setPermissionValue(1);
                            updateGroupDetails.add(oldUserPermissionGroupDetail);
                        }
                    }else {
                        // 页面权限为选中
                        if (oldUserPermissionGroupDetail.getPermissionValue() == 1){
                            // 原本的权限值为0,只有当权限值改变时才会进行更新
                            oldUserPermissionGroupDetail.setPermissionValue(0);
                            updateGroupDetails.add(oldUserPermissionGroupDetail);
                        }
                    }
                }
            }


            if (exist == 0){
                // 当上述代码为匹配到当前库中存在的权限值时，在此添加一个新的权限值记录
                UserPermissionGroupDetails userPermissionGroupDetails
                        = new UserPermissionGroupDetails();

                if (menuVO.getChecked()){
                    userPermissionGroupDetails.setPermissionValue(1);
                }else {
                    userPermissionGroupDetails.setPermissionValue(0);
                }
                userPermissionGroupDetails.setCompid(compid);
                userPermissionGroupDetails.setMenuId(menuVO.getId());
                userPermissionGroupDetails.setGroupClass(groupId);

                updateGroupDetails.add(userPermissionGroupDetails);
            }

        }
        try {
            permissionGroupDetailsDao.saveAll(updateGroupDetails);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }


    }

    @Override
    public List<MenuVO> getGroupPermissionValue(
            String groupId, String compid, String token) {

        // todo: 验证用户是否有权限修改该权限组


        // todo: 不知道为什么库里存的企业代号前面的0没了这里进行了一次整数转换。。。
        // 获取该权限组的权限值
        List<UserPermissionGroupDetails> userPermissionGroupDetails
                = permissionGroupDetailsDao.findByGroupClass(groupId);

        // 获取菜单详情
        List<DeMenuInfo> deMenuInfos = deMenuInfoDao.findAll();
        List<MenuVO> menuVOS = new ArrayList<>();

        // 转换菜单详情位menuvo对象

        for (DeMenuInfo deMenuInfo : deMenuInfos){
            MenuVO menuVO = new MenuVO();
            BeanUtils.copyProperties(deMenuInfo, menuVO);
            menuVOS.add(menuVO);
        }


        for (UserPermissionGroupDetails userPermissionGroupDetail
                : userPermissionGroupDetails){
            for (MenuVO menuVO :menuVOS){
                if (menuVO.getId().equals(userPermissionGroupDetail.getMenuId())
                        && userPermissionGroupDetail.getPermissionValue() == 1){
                    menuVO.setChecked(true);
                }
            }
        }

        return menuVOS;
    }

    @Override
    public List<UserEmployee> getUserEmployee(Integer compid, String token) {
        return null;
    }

    @Override
    public Page<UserPermissionGroup> findPermissionGroup(Pageable pageable) {
        // 查询非删除状态的权限组
        return userPermissionGroupDao.findAll(pageable);
    }
}
