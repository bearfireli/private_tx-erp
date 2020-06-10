package com.hntxrj.txerp.conf;

import com.alibaba.fastjson.JSONObject;
import com.hntxrj.txerp.core.exception.ErpException;
import com.hntxrj.txerp.core.exception.ErrEumn;
import com.hntxrj.txerp.core.exception.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Component
@Aspect
@Slf4j
public class AuthCheckAopAspect {

    @Value("${app.cloud.host}")
    private String url;

    private static final String succeedCode = "0";
    private static final String failedCode = "-1";

    private static final String GET_DRIVER_NAME = "/driver/getDriverNames";
    private static final String GET_QUERY_TIME = "/api/querytimeset/getQueryTimeSetList";
    private static final String GET_STOCK = "/api/stock/getStock";
    private static final String GET_STIR_IDS = "/api/stock/getStirIds";


    private static final String[] PUBLIC_API_LIST = new String[]{
            GET_DRIVER_NAME, GET_QUERY_TIME, GET_STOCK, GET_STIR_IDS

    };

    @Around("execution(* com.hntxrj.txerp.api.*.*(..))")
    private Object authCheck(ProceedingJoinPoint joinPoint) throws Throwable {
        //获取每次请求的request和response
        HttpServletRequest request = ((ServletRequestAttributes)
                Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        HttpServletResponse response = ((ServletRequestAttributes)
                RequestContextHolder.getRequestAttributes()).getResponse();

        //打印出请求的地址
        String uri = request.getRequestURI();
        log.info(String.format("%s >>> %s", request.getMethod(), uri));


        // 判断无权限接口
        for (String publicApi : PUBLIC_API_LIST) {
            if (publicApi.equals(uri)) {
                return joinPoint.proceed();
            }
        }

        assert response != null;
        response.setHeader("Content-type", "text/html;charset=UTF-8");
        //获取请求头中的token,pid,compid
        String token = request.getHeader("token");
        String pid = request.getHeader("pid");
        String compid = request.getHeader("compid");

        if (token == null || "".equals(token)) {
            ExceptionUtil.defaultErrorHandler(request, response, new ErpException(ErrEumn.NOT_LOGIN));
        }
        if (pid == null || "".equals(pid)) {
            ExceptionUtil.defaultErrorHandler(request, response, new ErpException(ErrEumn._PID_NOT_FIND_IN_HEADER));
        }
        if (compid == null || "".equals(compid)) {
            ExceptionUtil.defaultErrorHandler(request, response, new ErpException(ErrEumn.ENTERPRISE_ID_NOTEXIST));
        }

        //通过AOP得到要访问的方法名和类名
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method targetMethod = methodSignature.getMethod();
        String className = targetMethod.getDeclaringClass().getName();
        String methodName = targetMethod.getName();
        String functionName;   //app名+controller名+method名，数据库存的方法名的形式。

        //得到的类名是全路径，用.截取其具体的类名。
        String[] strings = className.split("\\.");
        className = strings[strings.length - 1];

        //得到项目名称
        String appName = getProjectName(pid);

        functionName = appName + "_" + className + "_" + methodName;

        //向erpBase项目发送请求，验证用户是否有权限访问此方法
        String permissionCode = isPermission(token, functionName, compid);

        if (succeedCode.equals(permissionCode)) {
            return joinPoint.proceed();
        } else {
            ExceptionUtil.defaultErrorHandler(request, response, new ErpException(ErrEumn.NOT_PERMISSION));
            return null;
        }

    }


    private String getProjectName(String pid) {
        Map<String, String> projectName = new HashMap<>();
        //项目：2->手机erp,  3->cloud项目, 25->司机App,  27->工地App
        projectName.put("2", "erpPhone");
        projectName.put("3", "erpBase");
        projectName.put("25", "erpDriver");
        projectName.put("27", "erpBuilder");

        return projectName.get(pid);
    }

    private String isPermission(String token, String methodName, String compid) {
        String baseUrl;
        baseUrl = url + "/auth/isPermission";
        OkHttpClient client = new OkHttpClient();
        RequestBody body = new FormBody.Builder()
                .add("enterprise", compid)
                .add("token", token)
                .add("uri", methodName)
                .build();
        Request request = new Request.Builder()
                .url(baseUrl)
                .post(body)
                .build();

        try {
            Response response = client.newCall(request).execute();
            ResponseBody responseBody = response.body();
            if (responseBody != null) {
                String result = responseBody.string();
                JSONObject resultJSON = JSONObject.parseObject(result);
                responseBody.close();
                Integer code = (Integer) resultJSON.get("code");
                return String.valueOf(code);
            }
            response.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return failedCode;
    }
}
