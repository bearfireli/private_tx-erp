package com.hntxrj.txerp.rabbitmq;

import com.hntxrj.txerp.core.exception.ErpException;
import com.hntxrj.txerp.core.exception.ErrEumn;
import com.hntxrj.txerp.mapper.MsgMapper;
import com.hntxrj.txerp.vo.MessagePushVO;
import com.hntxrj.txerp.vo.RecipientVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RabbitMQReceiver {
    private static final Logger logger = LoggerFactory.getLogger(RabbitMQReceiver.class);

    private final AmqpTemplate rabbitTemplate;
    private final MsgMapper msgMapper;
    private final JPushUtil jPushUtil;

    @Autowired
    public RabbitMQReceiver(AmqpTemplate rabbitTemplate, MsgMapper msgMapper, JPushUtil jPushUtil) {
        this.rabbitTemplate = rabbitTemplate;
        this.msgMapper = msgMapper;
        this.jPushUtil = jPushUtil;
    }

    @RabbitListener(queues = RabbitConfig.PHONE_QUEUE)
    @RabbitHandler
    public void erpPhoneReceive(MessagePushVO message) throws ErpException {
        logger.info("erpPhone消息消费者:{}", message);
        //获取需要推送的推送人
        List<RecipientVO> recipientList = getRecipientList(message);
        //调用极光推送api推送消息
        jPushUtil.messagePush(message, recipientList);
    }

    @RabbitListener(queues = RabbitConfig.DRIVER_QUEUE)
    @RabbitHandler
    public void erpDriverReceive(MessagePushVO message) {
        logger.info("erpDriver消息消费者:{}", message);
    }

    @RabbitListener(queues = RabbitConfig.BUILDER_QUEUE)
    @RabbitHandler
    public void erpBuilderReceive(MessagePushVO message) {
        logger.info("erpBuilder消息消费者:{}", message);
    }

    private List<RecipientVO> getRecipientList(MessagePushVO messagePushVO) throws ErpException {
        if (messagePushVO == null) {
            throw new ErpException(ErrEumn.MESSAGE_PUSH_IS_NULL);
        }
        return msgMapper.getRecipientList(messagePushVO.getCompid(), messagePushVO.getTypeId());
    }
}
