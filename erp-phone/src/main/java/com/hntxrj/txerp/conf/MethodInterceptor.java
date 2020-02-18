package com.hntxrj.txerp.conf;

import com.alibaba.fastjson.JSONObject;

import com.arronlong.httpclientutil.HttpClientUtil;
import com.arronlong.httpclientutil.common.HttpConfig;
import com.arronlong.httpclientutil.common.HttpHeader;
import com.arronlong.httpclientutil.exception.HttpProcessException;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.Header;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * 功能: 拦截器处理
 *
 * @Auther 李帅
 * @Data 2017-08-22.下午 10:14
 */
@Component
@Slf4j
public class MethodInterceptor implements HandlerInterceptor {

    @Value("${app.cloud.auth}")
    private String authUrl;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) {
        String uri = request.getRequestURI();
        //获取并验证Token
        String tk = request.getParameter("token");
        // 从请求头获取token
        if (tk == null) {
            tk = request.getHeader("token");
        }
        if (uri.indexOf(".html") > 0 || uri.indexOf(".htm") > 0 ||
                uri.indexOf(".js") > 0 || uri.indexOf(".css") > 0 ||
                uri.indexOf(".png") > 0 || uri.indexOf(".jpg") > 0 ||
                uri.indexOf(".jpeg") > 0 || uri.indexOf(".gif") > 0 ||
                uri.indexOf(".woff") > 0 || uri.indexOf(".ttf") > 0) {
            return true;
        }
        log.info("【验证权限】uri={}, token={}", uri, tk);
        if (uri.equals("/comp/addComp")||uri.equals("/comp/deleteComp")||uri.equals("/comp/updateComp")) {
            // 该接口为tx-erp提供添加企业,修改企业，删除企业服务,通过key验证，不需要在此验证
            return true;
        }
        if (checkTokenIsNormal(tk)) {
            return true;
        } else {
            log.warn("权限验证失败！");
            response.setHeader("REDIRECT", "please check token!");
            return false;
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object o, ModelAndView modelAndView) {


    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object o, Exception e) {

    }


    /**
     * 校验Token是否正常
     *
     * @param token Token
     * @return 返回是否通过
     */
    private boolean checkTokenIsNormal(String token) {
        Map<String, Object> map = new HashMap<>();
        map.put("token", token);
        Header[] headers = HttpHeader.custom()
                .other("version", "1")
                .build();
        //插件式配置请求参数（网址、请求参数、编码、client）
        HttpConfig config = HttpConfig.custom()
                .headers(headers)
                .url(authUrl)
                .map(map)
                .encoding("utf-8")
                .inenc("utf-8");
        //使用方式：
        boolean pass = false;
        try {
            String result = HttpClientUtil.post(config);
            JSONObject jsonObject = JSONObject.parseObject(result);

            int code = jsonObject.getIntValue("code");
            log.info("【code】c={}", code);
            pass = code == 0;
            if (!pass) {
                log.warn("【token验证】token 验证失败,jsonObject={}", jsonObject);
            }
        } catch (HttpProcessException e) {
            log.warn("【Token验证】请求权限服务失败");

            e.printStackTrace();
        }
        return pass;
    }
}
