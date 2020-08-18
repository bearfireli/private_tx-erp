package com.hntxrj.txerp.rabbitmq;

import com.hntxrj.txerp.vo.MessagePushVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class RabbitMQSender {
    private final AmqpTemplate rabbitTemplate;
    private static final Logger logger = LoggerFactory.getLogger(RabbitMQReceiver.class);

    @Autowired
    public RabbitMQSender(AmqpTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void erpPhoneMessagePush(MessagePushVO messagePushVO) {
        rabbitTemplate.convertAndSend(RabbitConfig.PHONE_QUEUE, messagePushVO);
    }

    public void erpDriverMessagePush(MessagePushVO messagePushVO) {
        rabbitTemplate.convertAndSend(RabbitConfig.DRIVER_QUEUE, messagePushVO);
    }

    public void erpBuilderMessagePush(MessagePushVO messagePushVO) {
        rabbitTemplate.convertAndSend(RabbitConfig.BUILDER_QUEUE, messagePushVO);
    }
}
