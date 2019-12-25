package com.hntxrj.txerp.conf;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.CodeSignature;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Aspect
@Slf4j
public class RequestLogAspect {
    @Around("execution(* com.hntxrj.txerp.controller.*.*.*(..))")
    private Object printRequestLog(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("\n======================请求日志=====================\n" +
                        "class={}\n" +
                        "method={}\n" +
                        "params={}\n" +
                 "========================end=======================\n",
                joinPoint.getTarget().getClass().getName(), joinPoint.getSignature().getName(),
                getNameAndValue(joinPoint));
        return joinPoint.proceed();
    }

    @Around("execution(* com.hntxrj.txerp.api.*.*(..))")
    private Object printRequestLog1(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("\n======================请求日志=====================\n" +
                        "class={}\n" +
                        "method={}\n" +
                        "params={}\n" +
                        "========================end=======================\n",
                joinPoint.getTarget().getClass().getName(), joinPoint.getSignature().getName(),
                getNameAndValue(joinPoint));
        return joinPoint.proceed();
    }
    /**
     * 获取参数Map集合
     */
    Map<String, Object> getNameAndValue(ProceedingJoinPoint joinPoint) {
        Map<String, Object> param = new HashMap<>();

        Object[] paramValues = joinPoint.getArgs();
        String[] paramNames = ((CodeSignature)joinPoint.getSignature()).getParameterNames();

        for (int i = 0; i < paramNames.length; i++) {
            param.put(paramNames[i], paramValues[i]);
        }

        return param;
    }


}
