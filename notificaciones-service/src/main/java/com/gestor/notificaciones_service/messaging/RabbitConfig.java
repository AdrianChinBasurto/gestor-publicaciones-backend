package com.gestor.notificaciones_service.messaging;

import org.springframework.amqp.core.*;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    public static final String EXCHANGE = "publicaciones.exchange";
    public static final String QUEUE = "notifications.activity";

    @Bean
    TopicExchange exchange(){ return new TopicExchange(EXCHANGE, true,false); }
    @Bean
    Queue queue(){ return new Queue(QUEUE, true); }

    @Bean Binding bindPub(Queue q, TopicExchange ex){ return BindingBuilder.bind(q).to(ex).with("publication.*"); }
    @Bean Binding bindAuth(Queue q, TopicExchange ex){ return BindingBuilder.bind(q).to(ex).with("auth.*");}
}
