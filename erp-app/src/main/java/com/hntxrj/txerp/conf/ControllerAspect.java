package com.hntxrj.txerp.conf;

import com.hntxrj.txerp.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
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

    private final RedisUtil redisUtil;

    @Autowired
    public ControllerAspect(RedisUtil redisUtil) {
        this.redisUtil = redisUtil;
    }

    @Around("execution(* com.hntxrj.txerp.controller.*.*(..))")
    private Object mappingAround(ProceedingJoinPoint joinPoint) throws Throwable {
        return joinPoint.proceed();
    }

}
