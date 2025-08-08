package com.gestor.auth.config;

import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
public class RabbitConfig {
    public static final String EXCHANGE = "publicaciones.exchange"; // mismo exchange que ya usas
    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(EXCHANGE);
    }

    @Component
    public class AuditorPublisher {
        private final RabbitTemplate rabbit;
        public AuditorPublisher(RabbitTemplate rabbit) { this.rabbit = rabbit; }
        public void publicar(String routingKey, Object payload) {
            rabbit.convertAndSend(RabbitConfig.EXCHANGE, routingKey, payload);
        }
    }

}
