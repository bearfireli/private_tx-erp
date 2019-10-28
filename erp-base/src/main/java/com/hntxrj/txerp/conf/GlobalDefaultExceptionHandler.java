package com.hntxrj.txerp.conf;

import com.hntxrj.txerp.core.exception.ErpException;
import com.hntxrj.txerp.core.exception.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 全局异常捕获
 */
@ControllerAdvice
@Slf4j
@ResponseBody
public class GlobalDefaultExceptionHandler {

    @ExceptionHandler(value = ErpException.class)
    public void defaultErrorHandler(HttpServletRequest request,
                                    HttpServletResponse response,
                                    ErpException e)
            throws IOException {
        ExceptionUtil.defaultErrorHandler(request, response, e);
    }
}
