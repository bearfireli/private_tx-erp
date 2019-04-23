package com.hntxrj.txerp.conf;

import com.hntxrj.txerp.core.exception.ErpException;
import com.hntxrj.txerp.core.exception.ErrEumn;
import com.hntxrj.txerp.core.exception.ExceptionUtil;
import com.hntxrj.txerp.service.AuthGroupService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Slf4j
@WebFilter(filterName = "authFilter", urlPatterns = "/**")
public class AuthFilter implements Filter {

    private final AuthGroupService authService;

    private static final String LOGIN_API = "/user/login";
    private static final String TOKEN_USE = "/user/tokenUse";
    private static final String THIRD_LOGIN = "/user/thirdLogin";
    private static final String USER_ENTERPRISE = "/user/getUserEnterprise";
    private static final String ERROR = "/error";
    private static final String FILEDOWNLOAD = "/afterSale/file";
    private static final String FAVICON = "/favicon.ico";


    private static final String DOCUMENT_URI = "/swagger";
    private static final String WEBJARS = "/webjars";
    private static final String V2 = "/v2";
    private static final String JOURNALISM_IMAGES="/journalism/images";


    private static final String[] PUBLIC_API_LIST = new String[]{
            LOGIN_API, TOKEN_USE, THIRD_LOGIN, ERROR, USER_ENTERPRISE, FILEDOWNLOAD,FAVICON
    };

    private static final String[] PUBLIC_PATH = new String[]{
            DOCUMENT_URI, WEBJARS, V2,JOURNALISM_IMAGES
    };

    @Autowired
    public AuthFilter(AuthGroupService authGroupService) {
        this.authService = authGroupService;
    }


    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        if ("OPTIONS".equals(request.getMethod())) {
            chain.doFilter(req, resp);
            return;
        }
        HttpServletResponse response = (HttpServletResponse) resp;
        String uri = request.getRequestURI();


        // 判断无权限接口
        for (String publicApi : PUBLIC_API_LIST) {
            if (publicApi.equals(uri)) {
                chain.doFilter(req, resp);
                return;

            }
        }

        for (String publicPath : PUBLIC_PATH) {
            if (uri.indexOf(publicPath) >= 0) {
                chain.doFilter(req, resp);
                return;

            }
        }
        log.info(String.format("%s >>> %s", request.getMethod(), uri));


        String token = request.getParameter("token");
        if (token == null || "".equals(token)) {
            token = request.getHeader("token");
        }



        response.setHeader("Content-type", "text/html;charset=UTF-8");

        if (request.getHeader("pid") == null || "".equals(request.getHeader("pid"))) {
            ExceptionUtil.defaultErrorHandler(request, response, new ErpException(ErrEumn._PID_NOT_FIND_IN_HEADER));

        }

        if (token == null || "".equals(token)) {
            ExceptionUtil.defaultErrorHandler(request, response, new ErpException(ErrEumn.NOT_LOGIN));
        }
        int enterprise = request.getIntHeader("enterprise");

        if (enterprise <= 0) {
            ExceptionUtil.defaultErrorHandler(request, response, new ErpException(ErrEumn.ENTERPRISE_ID_NOTEXIST));
        }
        log.info("【验证身份】token={}, uri={}, enterprise={}", token, uri, enterprise);
        try {
            if (authService.isPermission(token, enterprise, uri)) {
                chain.doFilter(req, resp);
                return;
            }
            ExceptionUtil.defaultErrorHandler(request, response, new ErpException(ErrEumn.NOT_PERMISSION));
        } catch (ErpException e) {
            ExceptionUtil.defaultErrorHandler(request, response, e);
        }
    }
}