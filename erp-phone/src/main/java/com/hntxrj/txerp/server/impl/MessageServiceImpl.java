package com.hntxrj.txerp.server.impl;

import com.hntxrj.txerp.mapper.MessageMapper;
import com.hntxrj.txerp.server.MessageService;
import com.hntxrj.txerp.vo.PushTypeVO;
import com.hntxrj.txerp.vo.RecipientVO;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class MessageServiceImpl implements MessageService {

    private final MessageMapper messageMapper;

    public MessageServiceImpl(MessageMapper messageMapper) {
        this.messageMapper = messageMapper;
    }


    @Override
    public List<PushTypeVO> getPushTypeList(String compid) {

        List<PushTypeVO> pushTypeList = messageMapper.getPushTypeList(compid);
        for (PushTypeVO pushTypeVO : pushTypeList) {
            List<RecipientVO> recipientList = messageMapper.getRecipientList(compid, pushTypeVO.getTypeId());
            pushTypeVO.setRecipientVOList(recipientList);
        }
        return pushTypeList;
    }

    @Override
    public List<RecipientVO> getRecipientList(String compid, Integer typeId) {
        return messageMapper.getRecipientList(compid, typeId);
    }

    @Override
    public void saveRecipient(List<RecipientVO> recipientVOS) {

        String compid = recipientVOS.get(0).getCompid();
        Integer typeId = recipientVOS.get(0).getTypeId();

        //先逻辑删除，再统一添加推送人
        messageMapper.deleteRecipient(compid, typeId);
        for (RecipientVO recipientVO : recipientVOS) {
            if (recipientVO.getUid() != null) {
                RecipientVO user = messageMapper.getRecipientById(recipientVO.getUid());
                if (user == null) {
                    messageMapper.saveRecipient(recipientVO.getUid(), recipientVO.getCompid(), recipientVO.getTypeId(),
                            recipientVO.getUserName(), recipientVO.getPhone());
                } else {
                    messageMapper.updateRecipient(recipientVO.getUid(), recipientVO.getCompid(), recipientVO.getTypeId(),
                            recipientVO.getUserName(), recipientVO.getPhone());
                }
            }

        }

    }


}
