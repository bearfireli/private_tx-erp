package com.hntxrj.txerp.im;

import org.json.JSONObject;

public interface FriendService {

    /**
     * 添加好友
     * 添加好友，支持批量添加好友。
     */
    Object friendAdd();

    /**
     * 导入好友
     * 支持批量导入单向好友。
     * 往同一个用户导入好友时建议采用批量导入的方式，避免并发写导致的写冲突。
     * @return 接口返回参数
     */
    Object friendImport();



    /**
     * 删除好友
     * 删除好友，支持单向删除好友和双向删除好友。
     * @return 接口返回参数
     */
    JSONObject friendDelete();
}
