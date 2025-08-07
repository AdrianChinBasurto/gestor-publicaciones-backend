package com.gestor.publicaciones_service.config;

import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String EXCHANGE = "publicaciones.exchange";

    @Bean
    public TopicExchange publicacionesExchange() {
        return new TopicExchange(EXCHANGE);
    }

}
