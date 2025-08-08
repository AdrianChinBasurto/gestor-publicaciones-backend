package com.gestor.auth.event;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import static com.gestor.auth.config.RabbitConfig.EXCHANGE;

@Component
public class AuditorPublisher {
    private  final RabbitTemplate rabbitTemplate;

    public AuditorPublisher(final RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void publicar(String routingKey, String payload){
        rabbitTemplate.convertAndSend(EXCHANGE,routingKey, payload);
    }
}
