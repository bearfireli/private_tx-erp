package com.hntxrj.server.impl;

import com.alibaba.fastjson.JSONArray;
import com.hntxrj.dao.AdminDao;
import com.hntxrj.entity.PageBean;
import com.hntxrj.server.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 * 功能: 后台服务层实现
 *
 * @Auther 李帅
 * @Data 2017-08-29.上午 9:04
 */
@Service
@Scope("prototype")
public class AdminServiceImpl implements AdminService {

    private final AdminDao adminDao;

    @Autowired
    public AdminServiceImpl(AdminDao adminDao) {
        this.adminDao = adminDao;
    }


    /**
     * 加载企业列
     * @param pageBean
     * @return
     */
    @Override
    public JSONArray getCompList(PageBean pageBean, String name, Integer mark, String compid, Integer emptype, Integer useing) {
        return adminDao.getCompList(pageBean,name,mark,compid,emptype,useing);
    }


    /**
     *  权限组加载
     * @param comid 企业ID
     * @param pageBean  分页相关
     * @return
     */
    @Override
    public JSONArray getPermissionGroupList(Integer mark, String comid, PageBean pageBean){
        return adminDao.getPermissionGroupList(mark,comid,pageBean);
    }

    /**
     *   员工的 增-删-改
     * @param Mark -操作标识0 插入 1更新 2删除
     * @param parameter_compid_1 公司代号
     * @param parameter_account_2  部门
     * @param parameter_codezo_3    职位
     * @param parameter_opid_4  操作员编号
     * @param parameter_empname_5   操作员名称
     * @param parameter_opid_c_6    创建人代号
     * @param empname_7 操作员名称
     * @param pwd_8 密码
     * @param workclass_9   班次
     * @param state_10  人员状态
     * @param motel_11  移动电话
     * @param email_12  Email
     * @param cardid_13 卡号
     * @param contack_14    联系人
     * @param tel_15    联系电话
     * @param codesx_16 性别
     * @param birthday_17   生日
     * @param securid_18    身份证号
     * @param addr2_19  户籍地址
     * @param postid_20 邮政编码
     * @param addr_21   通讯地址
     * @param rdate_22  到职日期
     * @param ldate_23  离职日期
     * @param codejo_24 职称
     * @param factid_25 工厂代号
     * @param useing_26 是否启用
     * @param memo_27   备注
     * @param LoginName_28  备注
     * @param emptype
     *@param salecode
     * @param compid_sa @return
     */
    @Override
    public JSONArray insertOrUpdateDelEmployee(Integer Mark, String parameter_compid_1, String parameter_account_2,
                                               String parameter_codezo_3, String parameter_opid_4, String parameter_empname_5,
                                               String parameter_opid_c_6, String empname_7, String pwd_8, String workclass_9,
                                               Integer state_10, String motel_11, String email_12, String cardid_13, String contack_14, String tel_15, String codesx_16,
                                               String birthday_17, String securid_18, String addr2_19, String postid_20, String addr_21,
                                               String rdate_22, String ldate_23, String codejo_24, String factid_25, byte useing_26,
                                               String memo_27, String LoginName_28, String groupCode, Integer emptype, String salecode, String compid_sa){
        return adminDao.insertOrUpdateDelEmployee(Mark, parameter_compid_1, parameter_account_2, parameter_codezo_3, parameter_opid_4, parameter_empname_5, parameter_opid_c_6, empname_7, pwd_8, workclass_9, state_10, motel_11, email_12, cardid_13, contack_14, tel_15, codesx_16, birthday_17, securid_18,
                addr2_19, postid_20, addr_21, rdate_22, ldate_23, codejo_24, factid_25, useing_26, memo_27, LoginName_28,groupCode,emptype,salecode,compid_sa);
    }

    /**
     *    企业的增删改
     * @param Mark  --操作标识0 插入 1更新 2删除
     * @param parmeter_updid
     * @param compid_1  企业ID
     * @param longname_2   企业全称
     * @param shortname_3  企业简称
     * @param addr_4   联系地址
     * @param tel_5    联系电话
     * @param fax_6    传真
     * @param contack_7   联络人
     * @param director_8   负责人
     * @param dmotel_9   负责人电话
     * @param facno_10   工厂证号
     * @param postid_11    邮政编码
     * @param ename_12    英文名称
     * @param useing_13   启用否是否启用，1启用 0未启用
     * @param taxrate_14   税率
     * @param Taxnumber_15   税号
     * @param busnumber_16  企业营业执照号
     * @param coderd_17   小数进位方式
     * @param roundbit_18   小数位数
     * @return
     */
    @Override
    public JSONArray insertOrUpdateDel(Integer Mark,
                                       String parmeter_updid,
                                       String compid_1,
                                       String longname_2,
                                       String shortname_3,
                                       String addr_4,
                                       String tel_5,
                                       String fax_6,
                                       String contack_7,
                                       String director_8,
                                       String dmotel_9,
                                       String facno_10,
                                       String postid_11,
                                       String ename_12,
                                       Integer useing_13,
                                       Double taxrate_14,
                                       String Taxnumber_15,
                                       String busnumber_16,
                                       String coderd_17,
                                       String roundbit_18){
        return  adminDao.insertOrUpdateDel(Mark, parmeter_updid, compid_1, longname_2, shortname_3, addr_4, tel_5, fax_6, contack_7, director_8, dmotel_9, facno_10, postid_11, ename_12, useing_13, taxrate_14, Taxnumber_15, busnumber_16, coderd_17, roundbit_18);
    }


    /**
     *  增删改菜单
     * @param mark  1 增  2 删 3 改
     * @param id  菜单主ID
     * @param MenuName  菜单名
     * @param MenuIcon  菜单图标
     * @param MenuGroup 几级菜单 0起始
     * @param MenuFid       菜单父ID
     * @param MenuUrl       菜单对应的url地址
     * @param MenuDoUrl     接口方法
     * @param MenuDoTaxis   操作排序，用于计算权限，从0开始
     * @param MenuIdentification    标识，0为正常，1为new，2为hot
     * @param CreateTime    创建时间
     * @param CreateUser    创建人
     * @param UpdateTime    更新时间
     * @param MenuStatus    菜单状态
     * @return
     */
    @Override
    public JSONArray insertOrUpdateDelMenu(Integer mark, Integer id, String MenuName, String MenuIcon, String MenuGroup,
                                           Integer MenuFid, String MenuUrl, String MenuDoUrl, Integer MenuDoTaxis, Integer MenuIdentification,
                                           String CreateTime, String CreateUser, String UpdateTime, byte MenuStatus){
        return adminDao.insertOrUpdateDelMenu(mark, id, MenuName, MenuIcon, MenuGroup, MenuFid, MenuUrl, MenuDoUrl, MenuDoTaxis, MenuIdentification, CreateTime, CreateUser, UpdateTime, MenuStatus);
    }

    /**
     *  保存权限
     * @param groupClass 用户组ID
     * @param inStr  插入的sql语句
     * @return
     */
    @Override
    public JSONArray savePermiss(Integer groupClass, String inStr){
        return adminDao.savePermiss(groupClass,inStr);
    }


    /**
     *   模糊查询企业用户
     * @param mark 标记  1 模糊查询 empname
     * @param compid  企业id
     * @param name  用户名
     * @param pageBean  分页
     * @return
     */
    @Override
    public JSONArray searchEmployee(Integer mark, String compid, String name, PageBean pageBean, Integer emptype, String opid_id, Integer useing){
        return adminDao.searchEmployee(mark, compid, name, pageBean ,emptype,opid_id,useing);
    }


    /**
     *  加载菜单
     * @return
     */
    @Override
    public JSONArray loadMenuTest(){
        return adminDao.loadMenuTest();
    }



    /**
     * 添加用户的下拉
     * @param mark 1  SM_BuilderInfo  施工单位 2  SM_EPPInfo   工程  3   --   SM_BUSINESSGROUP  业务员   4  --   VM_PERSONALINFO   司机泵工信息表
     * @param pageSize  分页
     * @param currPage 当前页
     * @param compid 所属企业
     * @param opid  用户
     * @return
     */
    @Override
    public JSONArray admin_user_add(Integer mark, Integer pageSize, Integer currPage, String compid, String opid,String fname,Integer emptype) {
        return adminDao.admin_user_add(  mark,   pageSize,   currPage,   compid,   opid,fname,emptype);
    }



    /**
     * 增删改user_ascriptin 所属表
     * @param mark 0 插入  1 更新  2 删除
     * @param compid 企业
     * @param opid 用户
     * @param edit_id 修改所属表  User_ascription 的id
     * @param saleCode 添加user_ascription 的 code 值
     * @param compid_as 预留字段
     * @return
     */
    @Override
    public JSONArray sp_edit_User_ascription(Integer mark, String compid, String opid, String edit_id, String saleCode, String compid_as) {
        return adminDao.sp_edit_User_ascription(mark,compid,opid,edit_id,saleCode,compid_as);
    }


    /**
     * 修改密码
     * @param password 密码
     * @param username 用户名
     * @param opid_id  用户id
     * @param compid_id
     * @param oldpassword
     * @return
     */
    @Override
    public JSONArray spt_edit_password(String password, String username, String opid_id, String compid_id, String oldpassword) {
        return adminDao.spt_edit_password(  password,   username,   opid_id,   compid_id,   oldpassword) ;
    }
}
