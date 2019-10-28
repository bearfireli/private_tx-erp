package com.hntxrj.txerp.conf;

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
        try {
            if (!req.getMethod().equals("OPTIONS")) {
                if (req.getHeader("enterprise") == null ||
                        "".equals(req.getHeader("enterprise")) ||
                        "undefined".equals(req.getHeader("enterprise"))
                ) {
                    pvStatisticsService.augmentPV(req.getRequestURI(), 0);

                } else {
                    pvStatisticsService.augmentPV(req.getRequestURI(), Integer.valueOf(req.getHeader("enterprise")));

                }
            }
        } finally {
            // 即使统计报错 继续执行请求
            chain.doFilter(request, response);
        }


    }

    @Override
    public void destroy() {

    }


}
