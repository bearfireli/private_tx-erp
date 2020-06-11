package com.hntxrj.txerp.util

import com.hntxrj.txerp.core.exception.ErpException
import com.hntxrj.txerp.core.exception.ErrEumn
import com.sun.xml.txw2.TxwException
import javax.servlet.http.HttpServletRequest


fun getCompid(request: HttpServletRequest): String? {
    if ("web_0.1" == request.getHeader("tx_client")) {
        // TODO: 后续接入权限需要根据token获取
        return "01"
    }
    if (request.getParameter("compid") != "") {
        return request.getParameter("compid")
    }
    throw ErpException(ErrEumn.NOT_FOUND_COMPID)
}