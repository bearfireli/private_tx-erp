package com.hntxrj.txerp.conf;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.ValidationException;

import com.hntxrj.txerp.core.exception.ErpException;
import com.hntxrj.txerp.core.exception.ExceptionUtil;
import com.hntxrj.txerp.vo.JsonVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 全局异常捕获
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    public GlobalExceptionHandler() {
    }

    @ExceptionHandler({ErpException.class})
    public void defaultErrorHandler(HttpServletRequest request, HttpServletResponse response, ErpException e)
            throws IOException {
        ExceptionUtil.defaultErrorHandler(request, response, e);
    }

    /**
     * 400 - Bad Request
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public JsonVo handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {
        log.error("缺少请求参数", e);
        JsonVo vo = new JsonVo();
        vo.setCode(400);
        vo.setMsg("缺少请求参数");
        return vo;
    }

    /**
     * 400 - Bad Request
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public JsonVo handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        log.error("参数解析失败", e);
        JsonVo vo = new JsonVo();
        vo.setCode(400);
        vo.setMsg("参数解析失败");
        return vo;
    }

    /**
     * 400 - Bad Request
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public JsonVo handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("参数验证失败", e);
        BindingResult result = e.getBindingResult();
        FieldError error = result.getFieldError();
        String field = error.getField();
        String code = error.getDefaultMessage();
        String message = String.format("%s:%s", field, code);
        JsonVo vo = new JsonVo();
        vo.setCode(400);
        vo.setMsg("参数验证失败");
        e.printStackTrace();
        return vo;
    }

    /**
     * 400 - Bad Request
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ValidationException.class)
    public JsonVo handleValidationException(ValidationException e) {
        log.error("参数验证失败", e);
        JsonVo vo = new JsonVo();
        vo.setCode(400);
        vo.setMsg("参数验证失败");
        e.printStackTrace();
        return vo;
    }

    /**
     * 405 - Method Not Allowed
     */
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
    public JsonVo handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        log.error("不支持当前请求方法1", e);
//        return new JSONArray().fromObject("request_method_not_supported");
        JsonVo vo = new JsonVo();
        vo.setCode(405);
        vo.setMsg("不支持当前请求方法");
        e.printStackTrace();
        return vo;
    }

    /**
     * 415 - Unsupported Media Type
     */
    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    @ExceptionHandler(value = HttpMediaTypeNotSupportedException.class)
    public JsonVo handleHttpMediaTypeNotSupportedException(Exception e) {
        log.error("不支持当前媒体类型", e);
        JsonVo vo = new JsonVo();
        vo.setCode(415);
        vo.setMsg("不支持当前媒体类型");
        e.printStackTrace();
        return vo;
    }

    /**
     * 500 - Internal Server Error
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = Exception.class)
    public JsonVo handleException(Exception e) {
        log.error("通用异常", e);
        JsonVo vo = new JsonVo();
        e.printStackTrace();
        vo.setCode(500);
        vo.setMsg("通用异常");
        return vo;
    }

    /**
     * 操作数据库出现异常:名称重复，外键关联
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = DataIntegrityViolationException.class)
    public JsonVo handleException(DataIntegrityViolationException e) {
        log.error("操作数据库出现异常:", e);
        JsonVo vo = new JsonVo();
        vo.setCode(500);
        e.printStackTrace();
        vo.setMsg("操作数据库出现异常：字段重复、有外键关联等");
        return vo;
    }

    /**
     * 操作为null的对象引发的异常
     */
    @ExceptionHandler(value = NullPointerException.class)
    public JsonVo handleException(NullPointerException e) {
        log.info("空指针异常", e);
        JsonVo vo = new JsonVo();
        vo.setCode(500);
        //vo.setMsg("操作为的对象为null引发的异常");
        e.printStackTrace();
        vo.setMsg("没有更多数据了");
        return vo;
    }
}
