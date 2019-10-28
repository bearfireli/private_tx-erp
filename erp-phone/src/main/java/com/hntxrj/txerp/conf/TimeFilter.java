package com.hntxrj.txerp.conf;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;


@Component
@Slf4j
@WebFilter(urlPatterns = "/*", filterName = "timeFilter")
public class TimeFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response
            , FilterChain chain) throws IOException, ServletException {
        long startTime = new Date().getTime();
        chain.doFilter(request, response);

        HttpServletRequest req = (HttpServletRequest) request;

        log.info("【耗时统计】uri={}, time={}", req.getRequestURI(), new Date().getTime() - startTime);
    }

    @Override
    public void destroy() {

    }
}
