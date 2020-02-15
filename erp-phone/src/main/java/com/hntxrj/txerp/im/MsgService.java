package com.hntxrj.txerp.im;

import com.hntxrj.txerp.core.exception.ErpException;
import com.hntxrj.txerp.vo.SendmsgVO;
import org.json.JSONObject;

/**
 * 消息
 */
public interface MsgService {

    /**
     * 单发单聊消息
     * @return 接口返回参数
     * @param sendmsgVO 消息类
     */
    String sendMsg(SendmsgVO sendmsgVO) throws ErpException;


    /**
     * 批量发单聊消息
     * @return 接口返回参数
     *@param sendmsgVO 消息类
     */
    String  batchSendMsg(SendmsgVO sendmsgVO) throws ErpException;



}
