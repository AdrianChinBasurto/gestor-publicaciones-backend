package com.gestor.catalogo_service.messaging;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.*;

@Configuration
public class RabbitConfig {
    public static final String EXCHANGE = "publicaciones.exchange";
    public static final String QUEUE = "catalog.publications";
    public static final String ROUTING = "publication.published";

    @Bean TopicExchange exchange(){ return new TopicExchange(EXCHANGE); }
    @Bean Queue queue(){ return new Queue(QUEUE, true); }
    @Bean Binding binding(Queue q, TopicExchange ex){ return BindingBuilder.bind(q).to(ex).with(ROUTING); }
}
