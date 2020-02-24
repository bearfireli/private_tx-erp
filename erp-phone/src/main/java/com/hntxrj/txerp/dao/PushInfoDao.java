package com.hntxrj.txerp.dao;



/**
 * 功能:  推送消息接收人Dao接口
 *
 * @Auther 陈世强
 */
public interface PushInfoDao {


    /**
     *   保存推送消息接收人
     * @return
     */

    void saveRecipient(String name, String compId, int typeId);
}
