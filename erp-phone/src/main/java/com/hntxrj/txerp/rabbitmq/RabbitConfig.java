package com.hntxrj.txerp.rabbitmq;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.core.Queue;

@Configuration
public class RabbitConfig {

    public static String PHONE_QUEUE;
    @Value("${queues.erp-phone-queue}")
    public void setPhoneQueue(String queueName) {
        PHONE_QUEUE = queueName;
    }

    public static String DRIVER_QUEUE;
    @Value("${queues.erp-driver-queue}")
    public void setDriverQueue(String queueName) {
        DRIVER_QUEUE = queueName;
    }

    public static String BUILDER_QUEUE;
    @Value("${queues.erp-builder-queue}")
    public void setBuilderQueue(String queueName) {
        BUILDER_QUEUE = queueName;
    }

    @Bean
    public Queue erpPhoneQueue() {
        return new Queue(PHONE_QUEUE);
    }

    @Bean
    public Queue erpDriverQueue() {
        return new Queue(DRIVER_QUEUE);
    }

    @Bean
    public Queue erpBuilderQueue() {
        return new Queue(BUILDER_QUEUE);
    }
}
