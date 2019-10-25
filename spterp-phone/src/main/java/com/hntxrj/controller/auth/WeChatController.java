package com.hntxrj.controller.auth;import com.alibaba.fastjson.JSONObject;
import com.hntxrj.server.UserEmployeeService;
import com.hntxrj.util.HttpUtil;
import com.hntxrj.vo.JsonVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 功能及介绍：微信授权认证
 * <p>
 * ========================
 * Created with IntelliJ IDEA.
 * User： 李 帅
 * Date：2018/7/24
 * Time：下午3:33
 * ========================
 *
 * @author 李帅
 */
@RestController
@EnableAsync
@Slf4j
public class WeChatController {

    private final UserEmployeeService userEmployeeService;


    final String APP_ID = "wx752baf18030ed7ea";
    final String APP_SECRET = "435ee098a643ee8c3b5d2fa4d734ed3b";
    final String TEST_APP_ID = "wxc385edc0d9afdbeb";
    final String TEST_APP_SECRET = "10e5bd2cdf5499e1c9ac136c8b1977f0";

    @Autowired
    public WeChatController(UserEmployeeService userEmployeeService) {
        this.userEmployeeService = userEmployeeService;
    }

    /**
     * 获取微信用户的 openId
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "getUserWechatOpenId.do")
    public void getUserWechatOpenId(HttpServletRequest request, HttpServletResponse response, String code, String state,
                                    String type,int version) {
        HttpUtil httpUtil = new HttpUtil("https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + APP_ID +
                "&secret=" + APP_SECRET + "&code=" + code + "&grant_type=authorization_code");
        String resultStr = httpUtil.get();
        resultStr = JSONObject.parseObject(resultStr).getString("openid");
        try {
            response.setContentType("UTF-8");
            response.setContentType("text/html; charset=UTF-8");
            log.info("【getUserWechatOpenId】 type={}", type);
            String url = "0".equals(type) || "".equals(type) || type == null ?
                    "/bindwechat2.html?data=" + resultStr :
                    "/login.html?data=" + resultStr;
            // TODO： 修改微信登录
         /*   url = "https://erp.hntxrj.com" + url;*/
            if(version == 1){
                url = "https://erp.hntxrj.com" + url;
            }
            if(version == 2){
                url = "https://erp.hntxrj.com/v2"+url;
            }
            if(version == 3){
                url = "https://erp.hntxrj.com/bindwechat2.html"+url;

            }
            System.out.println(url);
            response.sendRedirect(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 取消绑定
     * <p>
     * 拿到操作员代号，在数据库中查找对应的用户，清空 weixin_code 字段
     *
     * @param token 用户的token
     */
    @RequestMapping(value = "cancelWechatBind")
    public JsonVo cancelWechatBind(String token) {
        JsonVo jsonVo = new JsonVo();
        if (token == null || token.equals("")) {
            jsonVo.setCode(1);
            jsonVo.setMsg("用户Token不能为空");
        } else {
            jsonVo.setCode(0);
            jsonVo.setMsg("ok");
            jsonVo.setData(userEmployeeService.cancelWechatBind(token));
        }
        return jsonVo;
    }
}
