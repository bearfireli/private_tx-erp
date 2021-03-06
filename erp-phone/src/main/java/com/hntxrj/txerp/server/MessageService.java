package com.hntxrj.txerp.server;


import com.hntxrj.txerp.vo.PushTypeVO;
import com.hntxrj.txerp.vo.RecipientVO;

import java.util.List;

public interface MessageService {
    /**
     * 获取消息推送类型列表
     */
    List<PushTypeVO> getPushTypeList(String compid);

    /**
     * 获取消息签收人列表
     *
     * @param compid 企业id
     * @param typeId 消息类型代号
     */
    List<RecipientVO> getRecipientList(String compid, Integer typeId);

    /**
     * 保存消息推送人
     *
     * @param recipientVOS 推送人对象集合
     */
    void saveRecipient(List<RecipientVO> recipientVOS);

}
