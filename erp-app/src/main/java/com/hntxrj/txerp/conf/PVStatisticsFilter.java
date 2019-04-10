package com.hntxrj.txerp.conf;

import com.alibaba.fastjson.JSONObject;
import com.hntxrj.txerp.core.util.RedisUtil;
import com.hntxrj.txerp.service.PVStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component
@WebFilter(filterName = "PVStatisticsFilter", urlPatterns = "/**")
public class PVStatisticsFilter implements Filter {

    private final PVStatisticsService pvStatisticsService;

    @Autowired
    public PVStatisticsFilter(PVStatisticsService pvStatisticsService) {
        this.pvStatisticsService = pvStatisticsService;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        if (!req.getMethod().equals("OPTIONS")) {
            pvStatisticsService.augmentPV(req.getRequestURI(), req.getIntHeader("enterprise"));
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }


}
