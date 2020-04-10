package com.hntxrj.txerp.conf;

import com.hntxrj.txerp.core.exception.ErpException;
import com.hntxrj.txerp.core.exception.ErrEumn;
import com.hntxrj.txerp.core.exception.ExceptionUtil;
import com.hntxrj.txerp.service.AuthGroupService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


@Component
@Aspect
@Slf4j
public class ControllerAspect {

    private final AuthGroupService authService;


    private static final String LOGIN_API = "/user/login";
    private static final String LOGIN_REST_API = "/api/user/login";
    private static final String TOKEN_USE = "/user/tokenUse";
    private static final String TOKEN_CHECK = "/user/tokenCheck";
    private static final String THIRD_LOGIN = "/user/thirdLogin";
    private static final String USER_ENTERPRISE = "/user/getUserEnterprise";
    private static final String ERROR = "/error";
    private static final String FILE_DOWNLOAD = "/afterSale/file";
    private static final String FAVICON = "/favicon.ico";
    private static final String JOURNALISM_LIST = "/journalism/journalismlist";
    private static final String JOURNALISM_SELECT_LIST = "/journalism/selectJournalism";
    private static final String JOURNALISM_BY_ID = "/journalism/getJournalism";
    private static final String GET_AUTH_VALUE = "/auth/getAuthValue";
    private static final String USER_SET_USER_FAVORITE = "/user/setUserFavorite";
    private static final String USER_GET_USER_FAVORITE = "/user/getUserFavorite";
    private static final String UPLOAD_PICTURE = "/enterprise/uploadPicture";
    private static final String SAVE_COLLECTION_CODE = "/enterprise/saveCollectionCode";
    private static final String USER_EXPIRE_TIME = "/v1/project/getExpireTime";
    private static final String SELECT_USER_ALL = "/user/selectAllUser";
    private static final String SELECT_ALL_USER = "/user/userAll";
    private static final String TOKEN_GET_USER = "/user/tokenGetUser";


    private static final String DOCUMENT_URI = "/swagger";
    private static final String WEBJARS = "/webjars";
    private static final String V2 = "/v2";
    private static final String JOURNALISM_IMAGES = "/journalism/images";
    private static final String USER_IMAGES = "/user/header.png";
    private static final String ENTERPRISE_IMAGES = "/enterprise/getFeedboackPicture";
    private static final String ENTERPRISE_IMAGE = "/enterprise/getimage";
    private static final String FEED_BOCK_IMAGES = "/feedback/getFeedboackPicture";
    private static final String FEED_BOCK_UPLOAD_PICTURE = "/feedback/uploadPicture";
    private static final String FEED_BOCK_ADD_FEEDBACK = "/feedback/addFeedback";
    private static final String USER_USERNAME = "/user/getUser";
    private static final String USER_SET_HEADER = "/user/setHeader";
    private static final String ENTERPRISE_GET_ENTERPRISE = "/enterprise/getEnterprise";
    private static final String USER_STATISTIC = "/statistic";
    private static final String FUNCTION_MENU_LIST = "/functionmenulist";
    private static final String USER_UPDATE_PASSWORD = "/user/updatePassword";

    private static final String[] PUBLIC_API_LIST = new String[]{
            LOGIN_API, TOKEN_USE, TOKEN_CHECK, THIRD_LOGIN, ERROR, USER_ENTERPRISE, FILE_DOWNLOAD, LOGIN_REST_API,
            FAVICON, JOURNALISM_LIST, JOURNALISM_BY_ID, JOURNALISM_SELECT_LIST, GET_AUTH_VALUE, USER_SET_USER_FAVORITE,
            USER_GET_USER_FAVORITE, UPLOAD_PICTURE, SAVE_COLLECTION_CODE, USER_EXPIRE_TIME, SELECT_USER_ALL,
            SELECT_ALL_USER,TOKEN_GET_USER
    };

    private static final String[] PUBLIC_PATH = new String[]{
            DOCUMENT_URI, WEBJARS, V2, JOURNALISM_IMAGES, USER_IMAGES, FEED_BOCK_IMAGES, ENTERPRISE_IMAGES,
            ENTERPRISE_IMAGE, USER_USERNAME, ENTERPRISE_GET_ENTERPRISE, USER_SET_HEADER, FEED_BOCK_UPLOAD_PICTURE,
            FEED_BOCK_ADD_FEEDBACK, USER_STATISTIC, FUNCTION_MENU_LIST,USER_UPDATE_PASSWORD
    };


    @Autowired
    public ControllerAspect(AuthGroupService authService) {
        this.authService = authService;
    }

    @Around("execution(* com.hntxrj.txerp.controller.*.*(..))")
    private Object mappingAround(ProceedingJoinPoint joinPoint) throws Throwable {
        //获取每次请求的request和response
        HttpServletRequest request = ((ServletRequestAttributes)
                Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        HttpServletResponse response = ((ServletRequestAttributes)
                RequestContextHolder.getRequestAttributes()).getResponse();

        if ("OPTIONS".equals(request.getMethod())) {
            return joinPoint.proceed();
        }

        //打印出请求的地址
        String uri = request.getRequestURI();
        log.info(String.format("%s >>> %s", request.getMethod(), uri));


        // 判断无权限接口
        for (String publicApi : PUBLIC_API_LIST) {
            if (publicApi.equals(uri)) {
                return joinPoint.proceed();
            }
        }

        for (String publicPath : PUBLIC_PATH) {
            if (uri.contains(publicPath)) {
                return joinPoint.proceed();
            }
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


        //获取参数中的token和compid以及pid
        String token = request.getParameter("token");
        int enterprise = request.getIntHeader("enterprise");
        String pid = request.getHeader("pid");


        assert response != null;
        response.setHeader("Content-type", "text/html;charset=UTF-8");
        if (pid == null || "".equals(pid)) {
            ExceptionUtil.defaultErrorHandler(request, response, new ErpException(ErrEumn._PID_NOT_FIND_IN_HEADER));
        }

        if (token == null || "".equals(token)) {
            token = request.getHeader("token");
            if (token == null || "".equals(token)) {
                ExceptionUtil.defaultErrorHandler(request, response, new ErpException(ErrEumn.NOT_LOGIN));
            }

        }

        if (enterprise <= 0) {
            ExceptionUtil.defaultErrorHandler(request, response, new ErpException(ErrEumn.ENTERPRISE_ID_NOTEXIST));
        }


        //得到项目名称
        String appName = getProjectName(pid);

        functionName = appName + "_" + className + "_" + methodName;


        //调用权限验证的方法，判断此用户是否有访问此方法的权限
        try {
            if (authService.isPermission(token, enterprise, functionName)) {
                return joinPoint.proceed();
            } else {
                ExceptionUtil.defaultErrorHandler(request, response, new ErpException(ErrEumn.NOT_PERMISSION));
            }
        } catch (ErpException e) {
            ExceptionUtil.defaultErrorHandler(request, response, e);
        }
        return null;
    }

    //得到发送请求的的项目的app名称
    private String getProjectName(String pid) {
        Map<String, String> projectName = new HashMap<>();
        projectName.put("2", "erpPhone");
        projectName.put("3", "erpBase");
        projectName.put("25", "erpDriver");

        return projectName.get(pid);

    }

}
