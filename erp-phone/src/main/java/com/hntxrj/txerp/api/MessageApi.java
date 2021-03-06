package com.hntxrj.txerp.api;

import com.hntxrj.txerp.server.MessageService;
import com.hntxrj.txerp.vo.RecipientVO;
import com.hntxrj.txerp.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 消息api
 */
@RestController
@RequestMapping("/api/message")
@Slf4j
public class MessageApi {

    private final MessageService messageService;

    @Autowired
    public MessageApi(MessageService messageService) {
        this.messageService = messageService;
    }

    /**
     * 获取消息模块列表
     *
     * @param compid 企业id
     */
    @PostMapping("/getPushTypeList")
    public ResultVO getPushTypeList(String compid) {
        return ResultVO.create(messageService.getPushTypeList(compid));

    }

    /**
     * 获取消息推送人列表
     *
     * @param compid 企业id
     * @param typeId 消息类型代号
     */
    @PostMapping("/getRecipientList")
    public ResultVO getRecipientList(String compid, Integer typeId) {
        return ResultVO.create(messageService.getRecipientList(compid, typeId));
    }


    /**
     * 保存消息推送人
     *
     * @param recipientVOS 推送人对象集合
     */
    @PostMapping("/saveRecipient")
    public ResultVO saveRecipient(@RequestBody List<RecipientVO> recipientVOS) {
        messageService.saveRecipient(recipientVOS);
        return ResultVO.create();
    }

}
