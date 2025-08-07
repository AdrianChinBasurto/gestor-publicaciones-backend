package com.gestor.publicaciones_service.messaging;


import com.gestor.publicaciones_service.dto.PublicationReadyEvent;
import com.gestor.publicaciones_service.model.Publicacion;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import static com.gestor.publicaciones_service.config.RabbitMQConfig.EXCHANGE;

@Component
public class PublicacionEventPublisher {

    private final RabbitTemplate rabbitTemplate;

    public PublicacionEventPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void publicarEvento(String routingKey,Publicacion publicacion) {
        var mensaje = String.format("ID: %%s - Estado %s - Titulo: %s",
                publicacion.getId(),
                publicacion.getEstado(),
                publicacion.getTitulo());

        rabbitTemplate.convertAndSend(EXCHANGE, routingKey, mensaje);
    }

    public void publicarReadyForCatalog (Publicacion pub) {
        PublicationReadyEvent event = new PublicationReadyEvent();
        event.setId(pub.getId());
        event.setTitulo(pub.getTitulo());
        event.setIdentificador(pub.getIdentificador());
        event.setTipo(pub.getTipo());
        event.setVersion(pub.getVersionActual());

        rabbitTemplate.convertAndSend("publicaciones.exchange","publication.readyForCatalog", event);
    }

}
