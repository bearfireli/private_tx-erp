package com.hntxrj.txerp.dao.impl;

import com.alibaba.fastjson.JSONArray;
import com.hntxrj.txerp.dao.AdminDao;
import com.hntxrj.txerp.entity.PageBean;
import com.hntxrj.txerp.util.CommonUtil;
import com.hntxrj.txerp.util.jdbc.procedure.Param;
import com.hntxrj.txerp.util.jdbc.procedure.Procedure;
import com.hntxrj.txerp.util.jdbc.enums.ParamType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 功能: 后台Dao实现层
 *
 * @Auther 李帅
 * @Data 2017-08-29.上午 9:05
 */
@Component
@Scope("prototype")
public class AdminDaoImpl implements AdminDao {

    @Autowired
    private Procedure procedure;


    /**
     * 加载企业列
     *
     * @param pageBean
     * @return
     */
    @Override
    public JSONArray getCompList(PageBean pageBean, String name, Integer mark, String compid, Integer emptype, Integer useing) {
        List<Param> params = new ArrayList<>();
        params.add(new Param(1, ParamType.INPARAM.getType(), mark));
        params.add(new Param(2, ParamType.INPARAM.getType(), compid));
        params.add(new Param(3, ParamType.INPARAM.getType(), name));
        params.add(new Param(4, ParamType.INPARAM.getType(), pageBean.getPageCurr()));
        params.add(new Param(5, ParamType.INPARAM.getType(), pageBean.getPageSize()));
        params.add(new Param(6, ParamType.INPARAM.getType(), emptype));
        params.add(new Param(7, ParamType.INPARAM.getType(), useing));


        procedure.init("sp_Admin_Query_Comp", params);

        try {
            procedure.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        /* 存储页面总数 */
        if (mark != 2) {
            if (!procedure.getResultArray().isEmpty()) {

                String str = procedure.getResultArray().getJSONArray(1).toString();
                str = str.substring(str.indexOf(":") + 1, str.indexOf("}"));
                System.out.println("总数为:" + str);
                if (str.matches("[0-9]*")) {
                    pageBean.setRecordCount(Integer.parseInt(str));
                }
            }
        }
        return procedure.getResultArray();
    }

    /**
     * 权限组加载
     *
     * @param comid    企业ID
     * @param pageBean 分页相关
     * @return
     */
    @Override
    public JSONArray getPermissionGroupList(Integer mark, String comid, PageBean pageBean) {
        List<Param> list = new ArrayList<Param>();
        list.add(new Param(1, ParamType.INPARAM.getType(), mark));
        list.add(new Param(2, ParamType.INPARAM.getType(), comid));
        list.add(new Param(3, ParamType.INPARAM.getType(), pageBean.getPageCurr()));
        list.add(new Param(4, ParamType.INPARAM.getType(), pageBean.getPageSize()));

        procedure.init("sp_Admin_Query_PermissionGroup", list);

        try {
            procedure.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (mark != 11 && mark != 3) {
            /* 存储页面总数 */
            CommonUtil.getrecordCount(procedure, pageBean, 1);
        }

        return procedure.getResultArray();
    }


    /**
     * 员工增-删-改
     *
     * @param Mark                -操作标识0 插入 1更新 2删除
     * @param parameter_compid_1  公司代号
     * @param parameter_account_2 部门
     * @param parameter_codezo_3  职位
     * @param parameter_opid_4    操作员编号
     * @param parameter_empname_5 操作员名称
     * @param parameter_opid_c_6  创建人代号
     * @param empname_7           操作员名称
     * @param pwd_8               密码
     * @param workclass_9         班次
     * @param state_10            人员状态
     * @param motel_11            移动电话
     * @param email_12            Email
     * @param cardid_13           卡号
     * @param contack_14          联系人
     * @param tel_15              联系电话
     * @param codesx_16           性别
     * @param birthday_17         生日
     * @param securid_18          身份证号
     * @param addr2_19            户籍地址
     * @param postid_20           邮政编码
     * @param addr_21             通讯地址
     * @param rdate_22            到职日期
     * @param ldate_23            离职日期
     * @param codejo_24           职称
     * @param factid_25           工厂代号
     * @param useing_26           是否启用
     * @param memo_27             备注
     * @param LoginName_28        备注
     * @return
     */
    @Override
    public JSONArray insertOrUpdateDelEmployee(Integer Mark, String parameter_compid_1, String parameter_account_2,
                                               String parameter_codezo_3, String parameter_opid_4, String parameter_empname_5,
                                               String parameter_opid_c_6, String empname_7, String pwd_8, String workclass_9,
                                               Integer state_10, String motel_11, String email_12, String cardid_13, String contack_14, String tel_15, String codesx_16,
                                               String birthday_17, String securid_18, String addr2_19, String postid_20, String addr_21,
                                               String rdate_22, String ldate_23, String codejo_24, String factid_25, byte useing_26,
                                               String memo_27, String LoginName_28, String groupCode, Integer emptype, String salecode, String compid_sa) {
        List<Param> params = new ArrayList<Param>();
        params.add(new Param(1, ParamType.INPARAM.getType(), Mark));
//        @Mark 	[int],    //操作标识0 插入 1更新 2删除
        params.add(new Param(2, ParamType.INPARAM.getType(), parameter_compid_1));
//        @parameter_compid_1 	[varchar](4),//公司代号
        params.add(new Param(3, ParamType.INPARAM.getType(), parameter_account_2));
//        @parameter_account_2 	[varchar](20),//部门
        params.add(new Param(4, ParamType.INPARAM.getType(), parameter_codezo_3));
//        @parameter_codezo_3 	[varchar](3),//职位
        params.add(new Param(5, ParamType.INPARAM.getType(), parameter_opid_4));
//        @parameter_opid_4 [varchar](10),//操作员编号
        params.add(new Param(6, ParamType.INPARAM.getType(), parameter_empname_5));
//        @parameter_empname_5 [varchar](10),//操作员名称
        params.add(new Param(7, ParamType.INPARAM.getType(), parameter_opid_c_6));
//        @parameter_opid_c_6[varchar](10),//创建人代号
        params.add(new Param(8, ParamType.INPARAM.getType(), empname_7));
//        @empname_7 [varchar](10),//操作员名称
        params.add(new Param(9, ParamType.INPARAM.getType(), pwd_8));
//        @pwd_8		[varchar](10),//密码
        params.add(new Param(10, ParamType.INPARAM.getType(), workclass_9));
//        @workclass_9 [varchar](10),//班次
        params.add(new Param(11, ParamType.INPARAM.getType(), state_10));
//        @state_10 	[int],//人员状态
        params.add(new Param(12, ParamType.INPARAM.getType(), motel_11));
//        @motel_11 	[varchar](30),//移动电话
        params.add(new Param(13, ParamType.INPARAM.getType(), email_12));
//        @email_12 	[varchar](60),//Email
        params.add(new Param(14, ParamType.INPARAM.getType(), cardid_13));
//        @cardid_13 [varchar](10),//卡号
        params.add(new Param(15, ParamType.INPARAM.getType(), contack_14));
//        @contack_14 	[varchar](10),//联系人.
        params.add(new Param(16, ParamType.INPARAM.getType(), tel_15));
//        @tel_15 	[varchar](30),//联系电话
        params.add(new Param(17, ParamType.INPARAM.getType(), codesx_16));
//        @codesx_16 [varchar](3),//性别
        params.add(new Param(18, ParamType.INPARAM.getType(), birthday_17));
//        @birthday_17 	[datetime],//生日
        params.add(new Param(19, ParamType.INPARAM.getType(), securid_18));
//        @securid_18 	[varchar](20),//身份证号
        params.add(new Param(20, ParamType.INPARAM.getType(), addr2_19));
//        @addr2_19 	[varchar](100),//户籍地址
        params.add(new Param(21, ParamType.INPARAM.getType(), postid_20));
//        @postid_20 	[varchar](10),//邮政编码
        params.add(new Param(22, ParamType.INPARAM.getType(), addr_21));
//        @addr_21[varchar](100),//通讯地址
        params.add(new Param(23, ParamType.INPARAM.getType(), rdate_22));
//        @rdate_22 	[datetime],//到职日期
        params.add(new Param(24, ParamType.INPARAM.getType(), ldate_23));
//        @ldate_23 	[datetime],//离职日期
        params.add(new Param(25, ParamType.INPARAM.getType(), codejo_24));
//        @codejo_24 [varchar](3),//职称
        params.add(new Param(26, ParamType.INPARAM.getType(), factid_25));
//        @factid_25 [varchar](10),//工厂代号
        params.add(new Param(27, ParamType.INPARAM.getType(), useing_26));
//        @useing_26 [BIT],//是否启用
        params.add(new Param(28, ParamType.INPARAM.getType(), memo_27));
//        @memo_27 	[varchar](100),//备注
        params.add(new Param(29, ParamType.INPARAM.getType(), LoginName_28));
//        @LoginName_28 	[varchar](100),//备注
        params.add(new Param(30, ParamType.INPARAM.getType(), ""));
//      @用户组
        params.add(new Param(31, ParamType.INPARAM.getType(), groupCode));
        params.add(new Param(32, ParamType.INPARAM.getType(), emptype));
        params.add(new Param(33, ParamType.INPARAM.getType(), salecode));
        params.add(new Param(34, ParamType.INPARAM.getType(), compid_sa));
        procedure.init("sp_insertUpDel_user_employee_srq", params);
        try {
            procedure.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return procedure.getResultArray();
    }

    /**
     * 企业的增删改
     *
     * @param Mark           //操作标识0 插入 1更新 2删除
     * @param parmeter_updid
     * @param compid_1       企业ID
     * @param longname_2     企业全称
     * @param shortname_3    企业简称
     * @param addr_4         联系地址
     * @param tel_5          联系电话
     * @param fax_6          传真
     * @param contack_7      联络人
     * @param director_8     负责人
     * @param dmotel_9       负责人电话
     * @param facno_10       工厂证号
     * @param postid_11      邮政编码
     * @param ename_12       英文名称
     * @param useing_13      启用否是否启用，1启用 0未启用
     * @param taxrate_14     税率
     * @param Taxnumber_15   税号
     * @param busnumber_16   企业营业执照号
     * @param coderd_17      小数进位方式
     * @param roundbit_18    小数位数
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
                                       String roundbit_18) {
        List<Param> param = new ArrayList<Param>();
        param.add(new Param(1, ParamType.INPARAM.getType(), Mark));  //标记
        param.add(new Param(2, ParamType.INPARAM.getType(), parmeter_updid)); //修改人代号
        param.add(new Param(3, ParamType.INPARAM.getType(), compid_1));  //企业ID
        param.add(new Param(4, ParamType.INPARAM.getType(), longname_2)); //企业全称
        param.add(new Param(5, ParamType.INPARAM.getType(), shortname_3)); //企业简称
        param.add(new Param(6, ParamType.INPARAM.getType(), addr_4));  //地址
        param.add(new Param(7, ParamType.INPARAM.getType(), tel_5));  //联系电话
        param.add(new Param(8, ParamType.INPARAM.getType(), fax_6));  //传真
        param.add(new Param(9, ParamType.INPARAM.getType(), contack_7));  //联络人
        param.add(new Param(10, ParamType.INPARAM.getType(), director_8));  //负责人
        param.add(new Param(11, ParamType.INPARAM.getType(), dmotel_9));   //负责人电话
        param.add(new Param(12, ParamType.INPARAM.getType(), facno_10));   //工厂证号
        param.add(new Param(13, ParamType.INPARAM.getType(), postid_11));  //邮政编码
        param.add(new Param(14, ParamType.INPARAM.getType(), ename_12));   //英文名
        param.add(new Param(15, ParamType.INPARAM.getType(), useing_13));  //启用否
        param.add(new Param(16, ParamType.INPARAM.getType(), taxrate_14));   //税率
        param.add(new Param(17, ParamType.INPARAM.getType(), Taxnumber_15)); //税号
        param.add(new Param(18, ParamType.INPARAM.getType(), busnumber_16));  //企业营业执照号
        param.add(new Param(19, ParamType.INPARAM.getType(), coderd_17));  //小数进位方式
        param.add(new Param(20, ParamType.INPARAM.getType(), roundbit_18));  //小数位数
        param.add(new Param(21, ParamType.INPARAM.getType(), ""));  //出参

        procedure.init("sp_insertUpDel_User_comp", param);
        try {
            procedure.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return procedure.getResultArray();
    }


    /**
     * 增删改菜单
     *
     * @param mark               1 增  2 删 3 改
     * @param id                 菜单主ID
     * @param MenuName           菜单名
     * @param MenuIcon           菜单图标
     * @param MenuGroup          几级菜单 0起始
     * @param MenuFid            菜单父ID
     * @param MenuUrl            菜单对应的url地址
     * @param MenuDoUrl          接口方法
     * @param MenuDoTaxis        操作排序，用于计算权限，从0开始
     * @param MenuIdentification 标识，0为正常，1为new，2为hot
     * @param CreateTime         创建时间
     * @param CreateUser         创建人
     * @param UpdateTime         更新时间
     * @param MenuStatus         菜单状态
     * @return
     */
    @Override
    public JSONArray insertOrUpdateDelMenu(Integer mark, Integer id, String MenuName, String MenuIcon, String MenuGroup,
                                           Integer MenuFid, String MenuUrl, String MenuDoUrl, Integer MenuDoTaxis, Integer MenuIdentification,
                                           String CreateTime, String CreateUser, String UpdateTime, byte MenuStatus) {
        List<Param> params = new ArrayList<Param>();
        //标记 1 增  2 删 3 改
        params.add(new Param(1, ParamType.INPARAM.getType(), mark));
        //菜单主ID
        params.add(new Param(2, ParamType.INPARAM.getType(), id));
        //菜单名
        params.add(new Param(3, ParamType.INPARAM.getType(), MenuName));
        //菜单图标
        params.add(new Param(4, ParamType.INPARAM.getType(), MenuIcon));
        //几级菜单 0起始
        params.add(new Param(5, ParamType.INPARAM.getType(), MenuGroup));
        //菜单父ID
        params.add(new Param(6, ParamType.INPARAM.getType(), MenuFid));
        //菜单对应的url地址
        params.add(new Param(7, ParamType.INPARAM.getType(), MenuUrl));
        //接口方法
        params.add(new Param(8, ParamType.INPARAM.getType(), MenuDoUrl));
        //操作排序，用于计算权限，从0开始
        params.add(new Param(9, ParamType.INPARAM.getType(), MenuDoTaxis));
        //标识，0为正常，1为new，2为hot
        params.add(new Param(10, ParamType.INPARAM.getType(), MenuIdentification));
        //创建时间
        params.add(new Param(11, ParamType.INPARAM.getType(), CreateTime));
        //创建人
        params.add(new Param(12, ParamType.INPARAM.getType(), CreateUser));
        //更新时间
        params.add(new Param(13, ParamType.INPARAM.getType(), UpdateTime));
        //菜单状态
        params.add(new Param(14, ParamType.INPARAM.getType(), MenuStatus));


        procedure.init("sp_Admin_edit_MenuInfo", params);
        try {
            procedure.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return procedure.getResultArray();
    }


    /**
     * 保存权限
     *
     * @param groupClass 用户组ID
     * @param inStr      插入的sql语句
     * @return
     */
    @Override
    public JSONArray savePermiss(Integer groupClass, String inStr) {
        List<Param> params = new ArrayList<Param>();
        params.add(new Param(1, ParamType.INPARAM.getType(), 1));
        params.add(new Param(2, ParamType.INPARAM.getType(), groupClass));
        params.add(new Param(3, ParamType.INPARAM.getType(), null));
        params.add(new Param(4, ParamType.INPARAM.getType(), null));
        params.add(new Param(5, ParamType.INPARAM.getType(), null));
        params.add(new Param(6, ParamType.INPARAM.getType(), inStr));
        procedure.init("sp_Admin_ManagerPermiss", params);
        try {
            procedure.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return procedure.getResultArray();
    }


    /**
     * 模糊查询企业用户
     *
     * @param mark     标记  1 模糊查询 empname
     * @param compid   企业id
     * @param name     用户名
     * @param pageBean 分页
     * @return
     */
    @Override
    public JSONArray searchEmployee(Integer mark, String compid, String name, PageBean pageBean, Integer emptype, String opid_id, Integer useing) {
        List<Param> parms = new ArrayList<Param>();
        parms.add(new Param(1, ParamType.INPARAM.getType(), mark)); //标记
        parms.add(new Param(2, ParamType.INPARAM.getType(), compid));
        parms.add(new Param(3, ParamType.INPARAM.getType(), name));
        parms.add(new Param(4, ParamType.INPARAM.getType(), pageBean.getPageCurr()));
        parms.add(new Param(5, ParamType.INPARAM.getType(), pageBean.getPageSize()));
        parms.add(new Param(6, ParamType.INPARAM.getType(), emptype));
        parms.add(new Param(7, ParamType.INPARAM.getType(), opid_id));
        parms.add(new Param(8, ParamType.INPARAM.getType(), useing));

        procedure.init("sp_Admin_Employee_Query", parms);
        try {
            procedure.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!procedure.getResultArray().isEmpty()) {

            CommonUtil.getrecordCount(procedure, pageBean, 1);
        }
        return procedure.getResultArray();
    }

    /**
     * 加载菜单
     *
     * @return
     */
    @Override
    public JSONArray loadMenuTest() {
        procedure.init("sp_Query_MenuListTest");
        try {
            procedure.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return procedure.getResultArray();
    }


    /**
     * 添加用户的下拉
     *
     * @param mark     1  SM_BuilderInfo  施工单位 2  SM_EPPInfo   工程  3   --   SM_BUSINESSGROUP  业务员   4  --   VM_PERSONALINFO   司机泵工信息表
     * @param pageSize 分页
     * @param currPage 当前页
     * @param compid   所属企业
     * @param opid     用户
     * @return
     */
    @Override
    public JSONArray admin_user_add(Integer mark, Integer pageSize, Integer currPage, String compid, String opid, String fname, Integer emptype) {


        List<Param> params = new ArrayList<>();
        params.add(new Param(1, ParamType.INPARAM.getType(), mark));
        params.add(new Param(2, ParamType.INPARAM.getType(), pageSize));
        params.add(new Param(3, ParamType.INPARAM.getType(), currPage));
        params.add(new Param(4, ParamType.INPARAM.getType(), compid));
        params.add(new Param(5, ParamType.INPARAM.getType(), opid));
        params.add(new Param(6, ParamType.INPARAM.getType(), fname));
        params.add(new Param(7, ParamType.INPARAM.getType(), emptype));

        procedure.init("admin_user_add", params);
        try {
            procedure.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return procedure.getResultArray();
    }


    /**
     * 增删改user_ascriptin 所属表
     *
     * @param mark      0 插入  1 更新  2 删除
     * @param compid    企业
     * @param opid      用户
     * @param edit_id   修改所属表  User_ascription 的id
     * @param saleCode  添加user_ascription 的 code 值
     * @param compid_as 预留字段
     * @return
     */
    @Override
    public JSONArray sp_edit_User_ascription(Integer mark, String compid, String opid, String edit_id, String saleCode, String compid_as) {
        List<Param> params = new ArrayList<>();
        params.add(new Param(1, ParamType.INPARAM.getType(), mark));
        params.add(new Param(2, ParamType.INPARAM.getType(), compid));
        params.add(new Param(3, ParamType.INPARAM.getType(), opid));
        params.add(new Param(4, ParamType.INPARAM.getType(), edit_id));
        params.add(new Param(5, ParamType.INPARAM.getType(), saleCode));
        params.add(new Param(6, ParamType.INPARAM.getType(), compid_as));

        procedure.init("sp_edit_User_ascription", params);
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
     * @param password    密码
     * @param username    用户
     * @param opid_id
     * @param compid_id
     * @param oldpassword
     * @return
     */
    @Override
    public JSONArray spt_edit_password(String password, String username, String opid_id, String compid_id, String oldpassword) {
        List<Param> params = new ArrayList<>();
        params.add(new Param(1, ParamType.INPARAM.getType(), 1));
        params.add(new Param(2, ParamType.INPARAM.getType(), password));
        params.add(new Param(3, ParamType.INPARAM.getType(), username));
        params.add(new Param(4, ParamType.INPARAM.getType(), opid_id));
        params.add(new Param(5, ParamType.INPARAM.getType(), compid_id));
        params.add(new Param(6, ParamType.INPARAM.getType(), oldpassword));

        procedure.init("spt_edit_password", params);
        try {
            procedure.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return procedure.getResultArray();
    }


}
