package com.hntxrj.txerp.im;

/**
 * 消息
 */
public interface MsgService {

    /**
     * 单发单聊消息
     * @return 接口返回参数
     */
    Object sendMsg();


    /**
     * 批量发单聊消息
     * @return 接口返回参数
     */
    Object batchSendMsg();



}
