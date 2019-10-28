package com.hntxrj.txerp.util;

import org.apache.commons.lang.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * 功能:   取出用户的 opid 与 compid
 *
 * @Auther 李帅
 * @Data 2017-08-22.上午 9:50
 */
public class CookieUtils {

    /**
     *  获取用户信息
     * @param request
     * @return
     */
    public Map<String,String> getUserInfo(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();

        Map<String,String> map = new HashMap<String,String>();

        if ( cookies != null ){
            for (Cookie  ck:cookies){
                if (ck.getName().equals("compid") ){
                    map.put("compid",ck.getValue());
                }else if(ck.getName().equals("opid") ){
                    map.put("opid",ck.getValue());
                }
            }
        }
        return map;
    }

    /**
     *  添加Cookie
     * @param response
     * @param name  cookie的名字
     * @param value  cookie的值
     * @param maxAge   cookie的时效
     */
    public static void addCookie(HttpServletResponse response, String name,
                                 String value, int maxAge) {
        int defaultMaxAeg = 60 * 60 * 24 * 7; //一周
        try {
            Cookie cookie = new Cookie(name, value);
            if (maxAge > 0) {
                cookie.setMaxAge( maxAge == -1 ? defaultMaxAeg : maxAge);
            }
            cookie.setPath("/");
            response.addCookie(cookie);
        } catch (Exception ex) {
            //LoggerUtils.error(CookieUtil.class, "创建Cookies发生异常！", ex);
        }
    }
    /**
     * 清空Cookie操作 clearCookie
     *
     * @param request
     * @param response
     * @return boolean
     */
    public static boolean clearCookie(HttpServletRequest request,
                                      HttpServletResponse response, String name) {
        boolean bool = false;
        Cookie[] cookies = request.getCookies();
        if(null == cookies || cookies.length == 0) {
            return bool;
        }
        try {
            for (int i = 0; i < cookies.length; i++) {
                Cookie cookie = new Cookie(name, null);
                cookie.setMaxAge(0);
                cookie.setPath("/");// 根据你创建cookie的路径进行填写
                response.addCookie(cookie);
                bool = true;
            }
        } catch (Exception ex) {
            //LoggerUtils.error(CookieUtil.class, "清空Cookies发生异常！", ex);
        }
        return bool;
    }
    /**
     * 清空Cookie操作 clearCookie
     *
     * @param request
     * @param response
     * @return boolean
     */
    public static boolean clearCookie(HttpServletRequest request,
                                      HttpServletResponse response, String name, String domain) {
        boolean bool = false;
        Cookie[] cookies = request.getCookies();
        if(null == cookies || cookies.length == 0) {
            return bool;
        }
        try {
            for (int i = 0; i < cookies.length; i++) {
                Cookie cookie = new Cookie(name, null);
                cookie.setMaxAge(0);
                cookie.setPath("/");// 根据你创建cookie的路径进行填写
                cookie.setDomain(domain);
                response.addCookie(cookie);
                bool = true;
            }
        } catch (Exception ex) {
            //LoggerUtils.error(CookieUtil.class, "清空Cookies发生异常！", ex);
        }
        return bool;
    }


    /**
     * 获取指定cookies的值 findCookieByName
     *
     * @param request
     * @param name
     * @return String
     */
    public static String findCookieByName(HttpServletRequest request,
                                          String name) {
        //作废 未用到 2018-05-31 13:27:42
//        Cookie[] cookies = request.getCookies();
//        if(null == cookies || cookies.length == 0) {
//            return null;
//        }
//        String string = null;
//        try {
//            for (int i = 0; i < cookies.length; i++) {
//                Cookie cookie = cookies[i];
//                String cname = cookie.getName();
//                if (!StringUtils.isBlank(cname) && cname.equals(name)) {
//                    string = cookie.getValue();
//                }
//            }
//        } catch (Exception ex) {
//            //LoggerUtils.error(CookieUtil.class, "获取Cookies发生异常！", ex);
//        }
        return request.getParameter(name);
    }
}
