package com.hntxrj.txerp.util

import com.hntxrj.txerp.core.exception.ErpException
import com.hntxrj.txerp.core.exception.ErrEumn
import javax.servlet.http.HttpServletRequest


@Throws(ErpException::class)
fun getToken(request: HttpServletRequest): String {
    if (!request.getHeader("token").isNullOrEmpty()) {
        return request.getHeader("token")
    }
    if (!request.getHeader("accessToken").isNullOrBlank()) {
        return request.getHeader("accessToken")
    }
    if (!request.getHeader("access-token").isNullOrBlank()) {
        return request.getHeader("access-token")
    }
    throw ErpException(ErrEumn.TOKEN_IS_NULL)
}

@Throws(ErpException::class)
fun getCompid(request: HttpServletRequest): String? {
    if (!request.getHeader("compid").isNullOrEmpty()) {
        return request.getHeader("compid")
    }
    if (!request.getHeader("Compid").isNullOrEmpty()) {
        return request.getHeader("Compid")
    }
    if (!request.getParameter("compid").isNullOrEmpty()) {
        return request.getParameter("compid")
    }
    if (!request.getParameter("Compid").isNullOrEmpty()) {
        return request.getParameter("Compid")
    }

    return null
}


@Throws(ErpException::class)
fun getOpId(request: HttpServletRequest): String? {
    if (!request.getParameter("opid").isNullOrEmpty()) {
        return request.getParameter("opid")
    }
    return null
}