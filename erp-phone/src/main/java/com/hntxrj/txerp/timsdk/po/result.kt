package com.hntxrj.txerp.timsdk.po

import com.alibaba.fastjson.JSONArray
import lombok.Data

/**
 * 账号在线状态
 * queryResult 内容
 * {
 *     "To_Account": "id2",
 *     "State": "Online"
 * },
 *
 */
class UserStateResult {
    var actionStatus: String? = null
    var errorInfo: String? = null
    var errorCode: Int? = null
    var queryResult: JSONArray? = null
    override fun toString(): String {
        return "{actionStatus:${actionStatus}, errorInfo:${errorInfo}, errorCode:${errorCode}, queryResult:${queryResult}}"
    }
}
