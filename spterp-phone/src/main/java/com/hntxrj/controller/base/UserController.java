package com.hntxrj.controller.base;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hntxrj.dao.PermissDao;
import com.hntxrj.entity.PermiUrl;
import com.hntxrj.server.PermissService;
import com.hntxrj.server.UserEmployeeService;
import com.hntxrj.util.*;
import com.hntxrj.util.jdbc.GetMsg;
import com.hntxrj.vo.JsonVo;
import lombok.extern.slf4j.Slf4j;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 功能:  用户 控制器
 *
 * @author 李帅
 * @Data 2017-08-11.下午 2:49
 */
@Slf4j
@RestController
@Scope("prototype")
@EnableAsync
public class UserController {

    private final App app;

    private final WriteLog writeLog;

    private final UserEmployeeService userEmployeeService;

    private final PermissDao permissDao;

    private final PermissService permissService;

    @Autowired
    public UserController(App app, UserEmployeeService userEmployeeService, PermissDao permissDao, WriteLog writeLog,
                          PermissService permissService) {
        this.app = app;
        this.userEmployeeService = userEmployeeService;
        this.permissDao = permissDao;
        this.writeLog = writeLog;
        this.permissService = permissService;
    }


    /**
     * 前台用户登录
     *
     * @param username 用户名
     * @param userpwd  密码
     * @return
     */
    @PostMapping("/login.do")
    public JsonVo userLogin(String username, String userpwd, String wx, HttpServletRequest request,
                                       HttpServletResponse response) throws SQLException {
        JsonVo vo = new JsonVo();
        //获取登录用户的ip
        String ip = GetRealIp.getIpFromRequest(request);
        //获取登录的版本信息
        String userAgent = request.getHeader("User-Agent");
        //密码和用户名不能为空
        if (username != null && userpwd != null) {
            //查询用户
            JSONArray jsonArray = userEmployeeService.userLogin(username, userpwd, 1);
            System.out.println(jsonArray);

            JSONArray jr = jsonArray.getJSONArray(1);
            String msg = jr.getJSONObject(0).getString("msg");
            //判断提信息是否登录成功
            if (msg.contains("登录成功")) {
                vo.setCode(0);
                vo.setMsg(msg);
                JSONArray jrUser = jsonArray.getJSONArray(0);
                JSONObject userObject = jrUser.getJSONObject(0);

                //设置登录信息
                String compid = userObject.getString("compid");
                String opid = userObject.getString("OpId");
                String shortname = userObject.getString("shortname");
                String empname = userObject.getString("empname");
                String openid = userObject.getString("openid");
                String ua = request.getHeader("user-agent")
                        .toLowerCase();
                if (ua.indexOf("micromessenger") > 0) {
                    //微信浏览器环境中
                    if (openid != null && !"".equals(openid) && wx != null) {
                        if (!wx.equals(openid)) {
                            //已绑定微信
                            vo.setCode(1);
                            vo.setMsg("该账号与绑定微信不一致。");
                            vo.setData("该账号与绑定微信不一致。");
                            return vo;
                        }
                    }
                } else {
                    //有 openid用户不允许在浏览器使用本系统
                    if (!openid.equals("")) {
                        vo.setCode(1);
                        vo.setMsg("该账号已绑定微信请在公众号中使用本系统！");
                        vo.setData("该账号已绑定微信请在公众号中使用本系统！");
                        return vo;
                    }
                }
                //用户头像地址
                String idphoto = "";
                try {
                    idphoto = userObject.getString("idphoto");
                } catch (Exception e) {
                    idphoto = "";
                }
                //token 的信息
                UUID uuid = UUID.randomUUID();
                String token = MD5Tools.MD5(uuid.toString());
                //设置登录信息logr日志
                userEmployeeService.insertUserHistory(opid, ip, token, userAgent, compid);
                //添加信息
                Map<String, Object> map = new HashMap<>();
                //企业名称
                map.put("shortname", shortname);
                //企业编号
                map.put("compid", compid);
                //用户
                map.put("opid", opid);
                //用户名称
                map.put("empname", empname);
                //登录token
                map.put("token", token);
                //  没有图片时  idphoto 是null
                map.put("idphoto", idphoto);
                //微信用户openid
                map.put("openid", openid);
                //map 反倒list 返回到页面
                List<Map<String, Object>> list = permissDao.formathDataNew(token);
                //api
                map.put("api", list);

                vo.setData(map);
                System.out.println("VO >>>> " + vo);

                return vo;
            } else {
                vo.setCode(1);
                vo.setMsg(msg);
                vo.setData(msg);
            }

        } else {
            vo.setCode(1);
            vo.setMsg("用户名或密码不能为空");
        }

        return vo;
    }


    /**
     * 用户退出
     */
    @RequestMapping("/loginout")
    public String userLoginout(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        //企业
        CookieUtils.clearCookie(request, response, "compid");
        //用户
        CookieUtils.clearCookie(request, response, "opid");
        CookieUtils.clearCookie(request, response, "opid_c");
        //y用户名称
        CookieUtils.clearCookie(request, response, "empname");
        //该用户的权限组
        CookieUtils.clearCookie(request, response, "groupCode");
        return "ok";
    }


    /**
     * 加载菜单信息
     *
     * @param grade 几级菜单
     * @param id    父ID
     * @return
     */
    @RequestMapping("/loadMenuInfo")
    public JsonVo loadMenuInfo(Integer grade, @RequestParam(value = "id", defaultValue = "-1") Integer id) {
        JsonVo vo = new JsonVo();
        //几级菜单  不能为null
        if (grade != null) {
            vo.setMsg("ok");
            vo.setCode(0);
            //调用dao
            vo.setData(userEmployeeService.loadMenuInfo(grade, id == -1 ? null : id));
        } else {
            //否则返回到页面
            vo.setCode(1);
            vo.setMsg("error, grade error");
        }
        return vo;
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
    @RequestMapping("/editPwd")
    public JsonVo editPwd(String compid, String loginname, String pwd, String newPwd) {
        JsonVo vo = new JsonVo();
        if (compid == null || "".equals(compid)) {
            vo.setCode(1);
            vo.setMsg("error,compid is null");
        } else if (loginname == null || "".equals(loginname)) {
            vo.setCode(1);
            vo.setMsg("error,loginname is null");
        } else if (pwd == null || "".equals(pwd)) {
            vo.setCode(1);
            vo.setMsg("error,pwd is null");
        } else if (newPwd == null || "".equals(newPwd)) {
            vo.setCode(1);
            vo.setMsg("error,newPwd is null");
        } else {
            GetMsg.getJsonMsg(vo, userEmployeeService.editPwd(compid, loginname, pwd, newPwd));
        }
        return vo;
    }


    /**
     * 生成验证码
     *
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping("/auth")
    public void auth(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        response.setHeader("Pragma", "No-cache");
//        response.setHeader("Cache-Control", "no-cache");
//        response.setDateHeader("Expires", 0);
//        response.setContentType("image/jpeg");

        //生成随机字串
        String verifyCode = VerifyCodeUtils.generateVerifyCode(4);
        //存入会话session
        HttpSession session = request.getSession(true);
        session.setAttribute("rand", verifyCode.toLowerCase());
        //生成图片
        int w = 110, h = 50;
        VerifyCodeUtils.outputImage(w, h, response.getOutputStream(), verifyCode);
    }

    /**
     * 后台用户登录
     *
     * @param username 用户名
     * @param userpwd  密码
     * @param code     验证码
     * @param session
     * @param request
     * @return
     */
    @RequestMapping("/admin/superAdmin.do")
    public JsonVo adminLogin(String username, String userpwd, String code, HttpSession session, HttpServletRequest request) {
        JsonVo vo = new JsonVo();
        //String ip = request.getRemoteAddr().equals("0:0:0:0:0:0:0:1")?"127.0.0.1":request.getRemoteAddr();
        String ip = GetRealIp.getIpFromRequest(request);

        String userAgent = request.getHeader("User-Agent");

        System.out.println(ip);
        System.out.println(userAgent);

        Object co = session.getAttribute("rand");
        String cd = co == null ? "" : co.toString();
        log.info("cd:" + cd + "-----------" + "code:" + code);
        if (cd.equals(code)) {  //cd.equals(code)
            vo.setCode(1);
            vo.setMsg("登录失败,验证码有误.");
            return vo;
        } else {

            if (username != null && userpwd != null) {
                JSONArray jsonArray = userEmployeeService.userLogin(username, userpwd, 2);
                JSONArray jr = ((JSONArray) jsonArray.get(1));
                String msg = jr.getJSONObject(0).getString("msg");
                System.out.println(msg);
                if (msg.contains("登录成功")) {
                    vo.setCode(0);
                    vo.setMsg("登录成功");
                    JSONArray jrUser = (JSONArray) jsonArray.get(0);
                    JSONObject jsonObject = (JSONObject) jrUser.get(0);
                    String compid = jsonObject.getString("compid");
                    String opid = jsonObject.getString("OpId");
                    String empname = jsonObject.getString("empname");

                    //用户类型   1 系统  2 企业
                    String emptype = jsonObject.getString("emptype");
                    UUID uuid = UUID.randomUUID();
                    String token = MD5Tools.MD5(uuid.toString());
                    userEmployeeService.insertUserHistory(opid, ip, token, userAgent, compid);
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("compid", compid);
                    map.put("opid", opid);
                    map.put("empname", empname);
                    //用户类型
                    map.put("emptype", emptype);
                    map.put("token", token);
                    List<PermiUrl> list = permissDao.formathDataTwo(token);
                    map.put("api", list);
                    vo.setData(map);
                    return vo;
                } else {
                    vo.setCode(1);
                    vo.setMsg(msg);
                    vo.setData(msg);
                }

            } else {
                vo.setCode(1);
                vo.setMsg("用户名或密码不能为空");
            }


            return vo;
        }
    }


    //  ------------------------------------------------------------------------


    /**
     * 修改用户的图片
     *
     * @param base   图片
     * @param compid 企业
     * @param opid   用户
     * @return json
     */
    @RequestMapping(value = "/edit/editemployeeidphoto.do")
    public JsonVo edit_employee_idphoto(String base, String compid, String opid, Integer mark, HttpServletRequest request) {

        JsonVo jsonVo = new JsonVo();


        Map<String, String> spterp = app.getSpterp();
        String uploadpath = spterp.get("uploadpath");

        try {
            //base64  不能为空
            if (base.isEmpty()) {
                jsonVo.setCode(1);
                jsonVo.setMsg("上传文件的为空");
                return jsonVo;
            }

//            String base64 = "{\"src\":\""+ base +"\"}";
            String base64 = base;


            /* 创建文件 */

            File file = new File(uploadpath);
            //如果文件夹不存在则创建
            if (!file.exists() && !file.isFile()) {
                file.mkdirs();
            }
            InputStream stream = StringStream.getStream(base64);
            SimpleDateFormat sim = new SimpleDateFormat("yyyyMMddHHmmssSSS");
            String format = sim.format(new Date());
            String uuid = format + UUID.randomUUID().toString().replace("-", "");
            //文件名称
            String destPath = uuid;
//            String geturl = app.getSpterp().get("geturl");
            String s = request.getRequestURL().toString();
            //创建输入流
            BufferedInputStream bis = new BufferedInputStream(stream);
            try {
                //写数据到文件
                BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(uploadpath + "/" + destPath));
                //通过数组分段写入
                byte[] bt = new byte[1024 * 10 * 5];
                int len = 0;
                while ((len = bis.read(bt)) != -1) {
                    bos.write(bt, 0, len);
                }
                //刷新数组
                bos.flush();
                //关闭流
                bis.close();
                bos.close();
            } catch (Exception e) {
                //直接返回错误提示
                e.printStackTrace();
                jsonVo.setCode(1);
                jsonVo.setMsg("上传失败");
                return jsonVo;
            }
            //调用dao 保存图片到数据库
            JSONArray jsonArray = userEmployeeService.edit_employee_idphoto(destPath, compid, opid, mark);
            //获取返回提示
            if (jsonArray != null && jsonArray.size() > 0) {
                //通过提示信息判断是佛成功
                JSONObject jsonObject = (JSONObject) jsonArray.getJSONArray(0).get(0);
                String result = jsonObject.getString("result");
                //"成功"
                if (result.contains("成功")) {
                    jsonVo.setCode(0);
                    jsonVo.setMsg(result);
                    return jsonVo;
                } else {
                    jsonVo.setCode(1);
                    jsonVo.setMsg(result);
                    return jsonVo;
                }

            }
            jsonVo.setCode(1);
            jsonVo.setMsg("保存错误");
            return jsonVo;
        } catch (Exception e) {
            e.printStackTrace();
            writeLog.init("修改用户头像", "1", e.toString(), opid);
            try {
                writeLog.write();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            jsonVo.setCode(1);
            jsonVo.setMsg("程序出错");
            return jsonVo;
        }
    }

    @RequestMapping("/articleImage/{fName}/a.do")
    public void FileController(HttpServletRequest request, HttpServletResponse response, @PathVariable String fName) throws IOException {
        //分解为空直接返回
        if (fName == null || fName.equals("")) {
            response.getWriter().print("文件名不能为空");
            return;
        }
        //获取存放路径
        String uploadpath = app.getSpterp().get("uploadpath").toString();
        uploadpath = uploadpath + "/" + fName;
        File file = new File(uploadpath);
        //读取文件
        if (file.exists() && file.isFile()) {
            //常见输入流
            FileInputStream fis = new FileInputStream(file);
            //流转成String   response 到也页面
            String string = StringStream.getString(fis);
            //response到页面
            response.getWriter().print(string);
            return;
        }
    }


    /**
     * 根据token获取用户权限
     *
     * @param token token
     * @return {@link JsonVo}
     */
    @RequestMapping(value = "getPermissionForToken.do")
    public JsonVo getPermissionForToken(String token) {
        JsonVo jsonVo = new JsonVo();
        jsonVo.setCode(0);
        jsonVo.setMsg("ok");
        jsonVo.setData(permissService.getPermissionForToken(token));
        return jsonVo;
    }


}
