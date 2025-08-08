package com.gestor.notificaciones_service.messaging;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gestor.notificaciones_service.model.Notificacion;
import com.gestor.notificaciones_service.repository.NotificacionRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import java.util.Map;


@Component
public class EventosListener {
    private final NotificacionRepository repo;
    private final ObjectMapper om = new ObjectMapper();
    public EventosListener(NotificacionRepository repo){ this.repo=repo; }

    @RabbitListener(queues = RabbitConfig.QUEUE)
    public void onEvent(Map<String,Object> evt){
        try{
            String tipo = (String) evt.getOrDefault("type","desconocido");
            // si tus publishers no mandan "type", puedes registrar por routing key -> ver nota abajo
            String payload = om.writeValueAsString(evt);
            repo.save(Notificacion.builder().tipo(tipo).payload(payload).build());
        }catch(Exception ignored){}
    }
}
