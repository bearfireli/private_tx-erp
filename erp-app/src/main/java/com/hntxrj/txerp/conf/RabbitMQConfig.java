package com.hntxrj.txerp.conf;


//@Configuration
public class RabbitMQConfig {

    public static final String QUEUE_NAME = "spring-boot";

//    @Bean
//    public Queue queue() {
//        // 是否持久化
//        boolean durable = true;
//        // 仅创建者可以使用的私有队列，断开后自动删除
//        boolean exclusive = false;
//        // 当所有消费客户端连接断开后，是否自动删除队列
//        boolean autoDelete = false;
//        return new Queue(QUEUE_NAME, durable, exclusive, autoDelete);
//    }
}
