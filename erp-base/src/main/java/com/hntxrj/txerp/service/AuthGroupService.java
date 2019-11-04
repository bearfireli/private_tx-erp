package com.hntxrj.txerp.service;

import com.hntxrj.txerp.core.exception.ErpException;
import com.hntxrj.txerp.entity.base.AuthGroup;
import com.hntxrj.txerp.entity.base.AuthValue;
import com.hntxrj.txerp.vo.AuthGroupDropDownVO;
import com.hntxrj.txerp.vo.AuthGroupVO;
import com.hntxrj.txerp.vo.AuthValueVO;
import com.hntxrj.txerp.vo.PageVO;

import javax.transaction.Transactional;
import java.util.List;

public interface AuthGroupService {

    /**
     * select auth group drop down (group id and group name) for enterprise
     *
     * @param enterprise enterprise id
     * @return auth group drop down
     */
    List<AuthGroupDropDownVO> getAuthGroupDropDown(Integer enterprise) throws ErpException;


    /**
     * select auth group
     *
     * @param page       page number
     * @param agName     auth group name
     * @param agStatus   auth group status
     * @param enterprise enterprise code if enterprise
     *                   is 0 get all enterprise auth group
     * @param pageSize   number of one page
     * @return page vo obj with auth group
     */
    PageVO<AuthGroupVO> selectAuthGroup(
            String token,
            long page, long pageSize,
            String agName,
            Integer agStatus,
            Integer enterprise) throws ErpException;


    AuthGroup editAuthGroup(AuthGroup authGroup) throws ErpException;


    /**
     * 获取企业的启用权限组
     *
     * @param token        用户令牌
     * @param enterpriseId 权限组id
     * @return 当前企业的权限组
     */
    List<AuthGroup> getAuthGroup(String token, Integer enterpriseId) throws ErpException;


    List<AuthValueVO> getAuthValue(Integer groupId);


    /**
     * 保存权限
     *
     * @param funName  有权限的菜单方法名
     * @param groupId 权限组id
     * @param token   用户token
     * @return saved auth values
     */
    //传递过来的是菜单id
    @Transactional
    List<AuthValue> saveAuthValue(List<Integer> menuId, Integer groupId, String token, Integer pid) throws ErpException;

    //传递过来的是方法名
//    @Transactional
//    List<AuthValue> saveAuthValue(List<String> funName, Integer groupId, String token, Integer pid) throws ErpException;



    /**
     * 得到该权限组的全部方法
     * */
    String[] getOpenAuth(Integer groupId);

    /**
     * 得到该权限组的全部方法
     * */
    Integer[] getOpenAuthIds(Integer groupId);


    /**
     * Is there permission?
     *
     * @param token user login token
     * @param enterprise   公司代号
     * @return
     */
    boolean isPermission(String token, Integer enterprise, String methodName) throws ErpException;

    String getfunNameByMid(Integer menuId);



    /**
    * 查询方法名和权限组是否绑定
    * */
    Integer isBound(Integer groupId, String funName);
}
