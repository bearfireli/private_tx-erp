package com.hntxrj.controller.base;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hntxrj.entity.Data;
import com.hntxrj.entity.PageBean;
import com.hntxrj.entity.Permission;
import com.hntxrj.server.AdminService;
import com.hntxrj.server.PermissService;
import com.hntxrj.server.UserEmployeeService;
import com.hntxrj.util.jdbc.GetMsg;
import com.hntxrj.vo.JsonVo;
import com.hntxrj.vo.JsonVoAndPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * 功能: 后台控制器
 *
 * @Auther 李帅
 * @Data 2017-08-29.上午 9:03
 * TODO: 合并两套管理后台的企业添加
 */
@RestController
@RequestMapping("/admin")
@Scope("prototype")
@EnableAsync
public class AdminController {

    private final AdminService adminService;

    private final PermissService permissService;

    private final UserEmployeeService userEmployeeService;

    @Autowired
    public AdminController(AdminService adminService, PermissService permissService, UserEmployeeService userEmployeeService) {
        this.adminService = adminService;
        this.permissService = permissService;
        this.userEmployeeService = userEmployeeService;
    }


    /**
     * 后台加载企业列表
     *
     * @param mark
     * @param compida
     * @param currPage
     * @param name
     * @param emptype
     * @return
     */
    @RequestMapping("/loadComp.action")
    public JsonVo loadComp(Integer mark,
                           String compida,
                           @RequestParam(defaultValue = "0") Integer currPage,
                           String name,
                           Integer emptype,
                           @RequestParam(defaultValue = "1") Integer useing) {
        JsonVoAndPage vo = new JsonVoAndPage();

        if (mark == 1) {
            //
            vo.setMsg("ok");
            vo.setCode(0);

            PageBean pageBean = new PageBean(10, currPage);
            //name  不能为空
            name = name == null || name.equals("") ? null : name;
            //调用dao
            vo.setData(adminService.getCompList(pageBean, name, mark, compida, emptype, useing));

            vo.setPageBean(pageBean);
        } else if (mark >= 2) {
            vo.setMsg("ok");
            vo.setCode(0);
            PageBean pageBean = new PageBean(10, currPage);
            vo.setData(adminService.getCompList(pageBean, name, mark, compida, emptype, useing));
        } else if (mark < 1) {
            vo.setMsg("error , mark error");
            vo.setCode(0);
        } else if (compida == null || compida.equals("")) {
            vo.setMsg("error, compid is null ");
            vo.setCode(1);
        }


        return vo;
    }

    /**
     * 加载权限组/用户组 详情
     *
     * @param mark         1 权限组 2 用户组
     * @param currPage     当前页码
     * @param admin_compid 企业ID
     * @return
     */
    @RequestMapping("/loadPermi.action")
    public JsonVo loadComp(Integer mark, @RequestParam(defaultValue = "0") Integer currPage, String admin_compid, String compida) {
        JsonVoAndPage vo = new JsonVoAndPage();

        if (admin_compid == null || admin_compid.equals("")) {
            vo.setCode(1);
            vo.setMsg("error,admin_compid is null");
        } else if (mark < 1 && mark > 2) {
            vo.setCode(1);
            vo.setMsg("error,mark is error");
        } else {
            vo.setMsg("ok");
            vo.setCode(0);
            PageBean pageBean = new PageBean(10, currPage);


            admin_compid = mark == 11 ? compida : admin_compid;

            if (mark == 4) {
                vo.setData(adminService.getPermissionGroupList(1, compida, pageBean));
            } else {
                vo.setData(adminService.getPermissionGroupList(mark, admin_compid, pageBean));
            }
            if (mark != 11 && mark != 3) { //详情不带分页  3 权限组不带分页
                vo.setPageBean(pageBean);
            }

        }
        return vo;
    }


    /**
     * 企业的增删改
     *
     * @param Mark           --操作标识0 插入 1更新 2删除
     * @param parmeter_updid 操作员id
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
    @RequestMapping("/editComp.action")
    public JsonVo insertUpdateDelComp(Integer Mark,
                                      String parmeter_updid,
                                      String compid_1,
                                      String longname_2,
                                      String shortname_3,
                                      @RequestParam(defaultValue = "") String addr_4,
                                      @RequestParam(defaultValue = "") String tel_5,
                                      @RequestParam(defaultValue = "") String fax_6,
                                      @RequestParam(defaultValue = "") String contack_7,
                                      @RequestParam String director_8,
                                      @RequestParam(defaultValue = "") String dmotel_9,
                                      @RequestParam(defaultValue = "") String facno_10,
                                      @RequestParam(defaultValue = "") String postid_11,
                                      @RequestParam(defaultValue = "") String ename_12,
                                      @RequestParam(defaultValue = "1") Integer useing_13,
                                      @RequestParam(defaultValue = "0.00") Double taxrate_14,
                                      @RequestParam(defaultValue = "") String Taxnumber_15,
                                      @RequestParam(defaultValue = "") String busnumber_16,
                                      @RequestParam(defaultValue = "") String coderd_17,
                                      @RequestParam(defaultValue = "") String roundbit_18) {

        JsonVo vo = new JsonVo();
        vo.setCode(1);
        if (parmeter_updid == null || parmeter_updid.equals("")) {
            vo.setMsg("error,parmeter_updid  is null ");
        } else if (Mark < 0 || Mark > 2) {
            vo.setMsg("error,mark 无效");
        } else if (compid_1 == null || compid_1.equals("")) {
            vo.setMsg("error,compid  is null ");
        } else {
            vo.setMsg("ok");
            vo.setCode(0);
            JSONArray array = adminService.insertOrUpdateDel(Mark, parmeter_updid, compid_1, longname_2, shortname_3, addr_4, tel_5, fax_6, contack_7, director_8, dmotel_9, facno_10, postid_11, ename_12, useing_13, taxrate_14, Taxnumber_15, busnumber_16, coderd_17, roundbit_18);
            //获取返回信息 为array 为null 时 直接报异常 ，由通用异常抓住
            String string = array.getJSONArray(0).toString();
            //获取提示信息
            JSONObject jsonObject = (JSONObject) array.getJSONArray(0).get(0);
            //提示信息
            String result = jsonObject.getString("result");
            //不包含失败就是成功
            if (!string.contains("失败")) {
                vo.setCode(0);
                vo.setMsg(result);
                vo.setData(array);
            } else {
                //失败返回code  =1
                vo.setCode(1);
                vo.setMsg(result);
                vo.setData(array);
            }

        }
        return vo;
    }

    /**
     * 增/删/改 权限组
     *
     * @param mark        标记 1 添加 2 修改 3 删除
     * @param groupName   权限组名
     * @param groupClass  权限UUid
     * @param groupStatus 权限组状态
     * @param createUser  创建人
     * @return
     */
    @RequestMapping("/editPermiGroup.action")
    public JsonVo inserUpdateDel_User_PermissionGroup(Integer mark, String groupName, String groupStatus, String groupClass, String createUser, String admin_compid, String compid_id) {
        JsonVo jsonVo = new JsonVo();
        if (createUser == null || createUser.equals("")) {
            jsonVo.setCode(1);
            jsonVo.setMsg("error,createUser is null");
        } else if (mark < 1 || mark > 3) {
            jsonVo.setCode(1);
            jsonVo.setMsg("error,mark error");
        } else {
            jsonVo.setCode(0);
            jsonVo.setMsg("ok");
            if (mark == 1) {
                groupClass = UUID.randomUUID().toString();
            }
            jsonVo.setData(userEmployeeService.inserUpdateDel_User_PermissionGroup(mark, groupName, groupClass, groupStatus, createUser, compid_id));
        }
        return jsonVo;
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
    @RequestMapping("/editEmployee.action")      //sp_insertUpDel_user_employee_srq
    public JsonVo insertOrUpdateDelEmployee(Integer Mark, String parameter_compid_1, String parameter_account_2,
                                            String parameter_codezo_3, String parameter_opid_4, String parameter_empname_5,
                                            String parameter_opid_c_6, String empname_7, String pwd_8, String workclass_9,
                                            Integer state_10, String motel_11, String email_12, String cardid_13, String contack_14, String tel_15, String codesx_16,
                                            String birthday_17, String securid_18, String addr2_19, String postid_20, String addr_21,
                                            String rdate_22, String ldate_23, String codejo_24, String factid_25, byte useing_26,
                                            String memo_27, String LoginName_28, String groupCode,

                                            Integer emptype,   //人员类型
                                            String salecode,  //操作的code
                                            String compid_sa, // 操作salecode 的 所属企业

                                            String saleCode,
                                            String compid_a,
                                            String opid_a,
                                            String emptype_id) {   //修改所属关系的saleCode 的值

        if (StringUtils.isEmpty(salecode)) {
            salecode = null;
        }
        JsonVo vo = new JsonVo();
        if (parameter_opid_c_6 == null || parameter_opid_c_6.equals("")) {
            vo.setCode(1);
            vo.setMsg("error,当前登录用户操作员代号不能为空");
        } else {

            JSONArray array = adminService.insertOrUpdateDelEmployee(Mark, parameter_compid_1, parameter_account_2, parameter_codezo_3,
                    parameter_opid_4, parameter_empname_5, parameter_opid_c_6, empname_7, pwd_8, workclass_9, state_10, motel_11,
                    email_12, cardid_13, contack_14, tel_15, codesx_16, birthday_17, securid_18, addr2_19, postid_20, addr_21,
                    rdate_22, ldate_23, codejo_24, factid_25, useing_26, memo_27, LoginName_28, groupCode,
                    emptype, salecode, compid_sa
            );

            //获取返回信息 为array 为null 时 直接报异常 ，由通用异常抓住
            String string = array.getJSONArray(0).toString();
            //获取提示信息
            JSONObject jsonObject = (JSONObject) array.getJSONArray(0).get(0);
            //提示信息
            String result = jsonObject.getString("result");
            //不包含失败就是成功
            if (!string.contains("失败")) {
                vo.setCode(0);
                vo.setMsg(result);
                vo.setData(array);
                if (Mark == 1 && StringUtils.isEmpty(saleCode)) {
                    //修改个人信息成功后修改,修改人员的归属关系

                    JSONArray jsonArray = adminService.sp_edit_User_ascription(1, compid_a, opid_a, null, saleCode, emptype_id);
                    JSONObject jsonObject1 = (JSONObject) jsonArray.getJSONArray(0).get(0);
                    //获取修改人员的归属关系的result
                    String result1 = jsonObject1.getString("result");
                    //包含失败,则返回提示
                    if (result1.contains("失败")) {
                        vo.setCode(1);
                        vo.setMsg(result1);
                        vo.setData(jsonArray);
                        return vo;
                    }
                    //把修改人员的归属关系的信息返回
                    vo.setMsg(result1);
                    vo.setData(jsonArray); //--------
                }


            } else {
                //失败返回code  =1
                vo.setCode(1);
                vo.setMsg(result);
                vo.setData(array);
            }
        }
        return vo;
    }

    /**
     * 增删改User_ascription 所属表
     *
     * @param mark       0 插入  1 更新  2 删除
     * @param compid_a   企业
     * @param opid_a     用户id
     * @param edit_id    要修改的id
     * @param saleCode   添加时的salecode
     * @param emptype_id 预留
     * @return json
     */
    @RequestMapping(value = "/speditUserscription.action")
    public JsonVo sp_edit_User_ascription(Integer mark,
                                          String compid_a,
                                          String opid_a,
                                          String edit_id,
                                          String saleCode,
                                          String emptype_id) {
        JsonVo jsonVo = new JsonVo();
        JSONArray jsonArray = adminService.sp_edit_User_ascription(mark, compid_a, opid_a, edit_id, saleCode, emptype_id);
        //判断jsonArray 不能为空
        if (jsonArray != null) {
            JSONObject jsonObject = (JSONObject) jsonArray.getJSONArray(0).get(0);
            String result = jsonObject.getString("result");
            if (result.contains("失败")) {
                jsonVo.setCode(1);
                jsonVo.setMsg(result);
                return jsonVo;
            }
            jsonVo.setCode(0);
            jsonVo.setMsg(result);
            jsonVo.setData(jsonArray);
            return jsonVo;

        } else {
            //为空时保存失败,返回code = 1
            jsonVo.setCode(1);
            jsonVo.setMsg("没有更多数据");
            return jsonVo;
        }
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
    @RequestMapping("/addMenu.action")
    public JsonVo insertOrUpdateDelMenu(Integer mark, Integer id, String MenuName, String MenuIcon, String MenuGroup,
                                        Integer MenuFid, String MenuUrl, String MenuDoUrl, Integer MenuDoTaxis, Integer MenuIdentification,
                                        String CreateTime, String CreateUser, String UpdateTime, byte MenuStatus) {
        JsonVo vo = new JsonVo();
        vo.setCode(0);
        vo.setMsg("ok");
        vo.setData(adminService.insertOrUpdateDelMenu(mark, id, MenuName, MenuIcon, MenuGroup, MenuFid,
                MenuUrl, MenuDoUrl, MenuDoTaxis, MenuIdentification, CreateTime, CreateUser, UpdateTime, MenuStatus));


        return vo;
    }


    /**
     * 保存权限组权限
     *
     * @param permission mark = 0    compid 企业  groupClass 权限组id  添加不传     list 权限  groupName 权限组名称     createUser 用户
     * @return
     */
    @RequestMapping("/savePermiss.action")
    public JsonVo savePermiss(@RequestBody Permission permission, HttpServletRequest request, HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET,POST");

        JsonVo vo = new JsonVo();
//        Permission permission = map.get("data");
        Integer mark = permission.getMark();
        //mark = 0 添加
        if (mark == 0) {
            //保存权限
            //添加权限组
            //到用dao 获取数据
            String s = UUID.randomUUID().toString();
            JSONArray array = userEmployeeService.inserUpdateDel_User_PermissionGroup(4, permission.getGroupName(), s, null, permission.getCreateUser(), permission.getCompid());
            if (array == null) {
                vo.setCode(1);
                vo.setMsg("权限组名称保存失败");
                return vo;
            }
            JSONObject jsonObject = (JSONObject) array.getJSONArray(0).get(0);
            String result = jsonObject.getString("result");
            //失败返回code =  1
            if (result.contains("失败")) {
                vo.setCode(1);
                vo.setMsg(result);
                return vo;
            }
            //成功 返回权限组主键id

            //设置groupcalss
            permission.setGroupClass(Integer.parseInt(result));
        }
        if (StringUtils.isEmpty(permission.getGroupName())) {
            vo.setCode(1);
            vo.setMsg("权限组名称不能为空");
            return vo;
        }

        //添加权限
        String str = "";
        if (permission.groupClass != 0 && !permission.compid.equals("")) {
            for (Data p : permission.list) {
                char[] ch = p.value.toCharArray();
                String resultString = "";
                for (int i = ch.length - 1; i >= 0; i--) {
                    resultString += ch[i];
                }
                str += ",(" + permission.groupClass + "," + p.id + "," + Integer.parseInt(resultString, 2) + ",'" + permission.compid + "')";
            }
            if (permission.list.size() > 0) {
                str = str.substring(1, str.length());
            }
            str = str == null || str.equals("") ? null : str;
            GetMsg.getJsonMsg(vo, adminService.savePermiss(permission.groupClass, str));
        } else {
            vo.setCode(1);
            vo.setMsg("未接收到数据");
        }


        return vo;
    }

    /**
     * 后台用户查询企业用户   sp_Admin_Employee_Query
     *
     * @param mark       标记  1 模糊查询 empname
     * @param compid_id  企业id
     * @param name       用户名
     * @param currPage   当前页码
     * @param emptype_id 用户类型
     * @param opid_id    查详情的用户的opid_id
     * @return
     */
    @RequestMapping("/searchEmployee.action")
    public JsonVoAndPage searchEmployee(Integer mark,
                                        String compid_id,
                                        String name,
                                        @RequestParam(defaultValue = "0") Integer currPage,
                                        Integer emptype_id,
                                        String opid_id,
                                        @RequestParam(defaultValue = "1") Integer useing) { // 0禁用  1 启用
        JsonVoAndPage jsonVoAndPage = new JsonVoAndPage();

        if (compid_id == null || compid_id.equals("")) {
            jsonVoAndPage.setCode(1);
            jsonVoAndPage.setMsg("error,compid is null");
        } else {
            jsonVoAndPage.setCode(0);
            jsonVoAndPage.setMsg("ok");
            PageBean pageBean = new PageBean(10, currPage);
            JSONArray jsonArray = adminService.searchEmployee(mark, compid_id, name == null || name.equals("") ? null : name, pageBean, emptype_id, opid_id, useing);
            jsonVoAndPage.setData(jsonArray);
            jsonVoAndPage.setPageBean(pageBean);
        }
        return jsonVoAndPage;
    }

    /**
     * 菜单加载
     *
     * @return
     */
    @RequestMapping("/loadMenu.action")
    public JsonVo loadMenu() {
        JsonVo vo = new JsonVo();
        vo.setCode(0);
        vo.setMsg("ok");
        vo.setData(adminService.loadMenuTest());
        return vo;
    }

    /**
     * 加载权限组已有的权限
     *
     * @param id 权限组ID
     * @return
     */
    @RequestMapping("/loadPermiSelected.action")
    public JsonVo loadPermi(String id, String compid) {
        JsonVo vo = new JsonVo();
        if (id == null || id.equals("")) {
            vo.setCode(1);
            vo.setMsg("error,id error");
        } else {
            vo.setCode(0);
            vo.setMsg("ok");
            vo.setData(permissService.loadPermi(id, compid));
        }
        return vo;
    }


    /**
     * 添加用户的下拉    存储过程   admin_user_add
     *
     * @param mark     mark     --- 1  加载所有企业  -----  2加载用户类型 	 -- ---  3 业务员   -- 4      司机泵工信息表   -- --5 SM_EPPInfo   工程 	-- 6     施工单位
     * @param PageSize 分页
     * @param currPage 当前页
     * @param compida  所属企业
     * @param opid     用户
     * @return json
     */
    @RequestMapping(value = "/adminuseradd.action")
    public JsonVo admin_user_add(Integer mark,
                                 @RequestParam(defaultValue = "10") Integer PageSize,
                                 @RequestParam(defaultValue = "0") Integer currPage,
                                 String compida,
                                 String opid,
                                 String fname,
                                 Integer emptype) {

        if (StringUtils.isEmpty(fname)) {
            fname = null;
        }
        JsonVoAndPage jsonVoAndPage = new JsonVoAndPage();

        //调用dao 获取数据
        JSONArray jsonArray = adminService.admin_user_add(mark, PageSize, currPage, compida, opid, fname, emptype);
        //jsonArry  为空时返回
        if (jsonArray == null) {
            jsonVoAndPage.setCode(0);
            jsonVoAndPage.setData(jsonArray);
            jsonVoAndPage.setMsg("ok");
            jsonVoAndPage.setPageBean(new PageBean(PageSize, currPage));
            return jsonVoAndPage;
        }
        //jsonarry   不为空时去recordCount
        JSONObject jsonObject = (JSONObject) jsonArray.getJSONArray(1).get(0);
        //从jsonOBject  去总页数
        String recordCount = jsonObject.getString("recordCount");
        jsonVoAndPage.setCode(0);
        jsonVoAndPage.setData(jsonArray);
        jsonVoAndPage.setMsg("ok");


        //创建分页对象
        PageBean pageBean = new PageBean(PageSize, currPage);
        //recordCount 为null 时不填加到pagebean
        if (recordCount != null) {
            pageBean.setRecordCount(Integer.parseInt(recordCount));
        }

        jsonVoAndPage.setPageBean(pageBean);
        return jsonVoAndPage;
    }


    /**
     * 修改密码
     *
     * @param oldpassword
     * @param password
     * @param admin_opid
     * @param username
     * @param admin_compid
     * @return
     */
    @RequestMapping(value = "/spteditpassword.action")
    public JsonVo spt_edit_password(String oldpassword, String password, String admin_opid, String username, String admin_compid) {
        JsonVo jsonVo = new JsonVo();
        JSONArray jsonArray = adminService.spt_edit_password(password, username, admin_opid, admin_compid, oldpassword);
        //判断jsonArray 不能为空
        if (jsonArray != null) {
            JSONObject jsonObject = (JSONObject) jsonArray.getJSONArray(0).get(0);
            String result = jsonObject.getString("result");
            if (result.contains("失败")) {
                jsonVo.setCode(1);
                jsonVo.setMsg(result);
                return jsonVo;
            }
            jsonVo.setCode(0);
            jsonVo.setMsg(result);
            jsonVo.setData(jsonArray);
            return jsonVo;

        } else {
            //为空时保存失败,返回code = 1
            jsonVo.setCode(1);
            jsonVo.setMsg("没有更多数据");
            return jsonVo;
        }

    }
}