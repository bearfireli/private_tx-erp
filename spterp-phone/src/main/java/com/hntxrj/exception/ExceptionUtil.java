package com.hntxrj.exception;

import com.alibaba.fastjson.JSON;
import com.hntxrj.vo.ResultVO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class ExceptionUtil {
    public static void defaultErrorHandler(HttpServletRequest request,
                                           HttpServletResponse response,
                                           ErpException e)
            throws IOException {
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(e.getErrEumn().getCode());
        resultVO.setMsg(e.getErrEumn().getMessage());
        response.addHeader("Content-type", "text/html;charset=UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        PrintWriter out = response.getWriter();
        out.append(JSON.toJSONString(resultVO));
        out.flush();
        out.close();
    }
}
