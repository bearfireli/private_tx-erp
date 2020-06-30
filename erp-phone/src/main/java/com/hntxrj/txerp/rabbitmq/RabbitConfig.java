package com.hntxrj.txerp.rabbitmq;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.core.Queue;

@Configuration
public class RabbitConfig {

    public static final String PHONE_QUEUE = "erpPhoneQueue";
    public static final String DRIVER_QUEUE = "erpDriverQueue";
    public static final String BUILDER_QUEUE = "erpBuilderQueue";

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
