package com.gestor.publicaciones_service.messaging;


import com.gestor.publicaciones_service.config.RabbitMQConfig;
import com.gestor.publicaciones_service.dto.PublicationReadyEvent;
import com.gestor.publicaciones_service.model.Publicacion;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;

import static com.gestor.publicaciones_service.config.RabbitMQConfig.EXCHANGE;

@Component
public class PublicacionEventPublisher {

    private final RabbitTemplate rabbitTemplate;

    public PublicacionEventPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void publicarPublicado(Publicacion pub) {
        Map<String, Object> evt = Map.of(
                "id", pub.getId().toString(),
                "titulo", pub.getTitulo(),
                "autorPrincipalId", pub.getAutorPrincipalId(),
                "tipo", pub.getTipo().name(),            // "ARTICULO" | "LIBRO"
                "palabrasClave", pub.getPalabrasClave(), // List<String>
                "identificador", pub.getIdentificador(), // DOI/ISBN generado
                "resumen", pub.getResumen()
        );

        rabbitTemplate.convertAndSend(
                RabbitMQConfig.EXCHANGE,
                "publication.published",
                evt
        );
    }
}
