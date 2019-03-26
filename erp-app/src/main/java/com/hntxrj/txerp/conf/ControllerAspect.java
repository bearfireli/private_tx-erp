package com.hntxrj.txerp.conf;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * controller aop
 *
 * @author haoranliu
 */
@Component
@Aspect
@Slf4j
public class ControllerAspect {

    @Around("execution(* com.hntxrj.txerp.controller.*.*(..))")
    private Object mappingAround(ProceedingJoinPoint joinPoint) throws Throwable {
        return joinPoint.proceed();
    }

    private void statisticsUriClick() {
        // TODO: 点击统计
    }

}
