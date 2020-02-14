package com.hntxrj.txerp.im;

import com.alibaba.fastjson.JSONObject;
import com.hntxrj.txerp.core.exception.ErpException;
import com.hntxrj.txerp.vo.FriendsVO;
import org.json.JSONArray;

import java.util.List;

public interface FriendService {

    /**
     * 添加好友
     * 添加好友，支持批量添加好友。
     *
     * @param userID  当前用户
     * @param friends 添加的好友
     * @return
     */
    String friendAdd(String userID, List<FriendsVO> friends) throws ErpException;

    /**
     * 导入好友
     * 支持批量导入单向好友。
     * 往同一个用户导入好友时建议采用批量导入的方式，避免并发写导致的写冲突。
     *
     * @param userID 当前用户
     * @param eid    企业代号
     * @return 接口返回参数
     */
    JSONArray friendImport(String userID, Integer eid);


    /**
     * 删除好友
     * 删除好友，支持单向删除好友和双向删除好友。
     * @param userID 当前用户
     * @param friends  选择删除的好友ID
     * @param deleteType 删除模式 Delete_Type_Single 单向删除/Delete_Type_Both 双向删除
     * @return 接口返回参数
     */
    String friendDelete(String userID, List<FriendsVO> friends, String deleteType) throws ErpException;
}
