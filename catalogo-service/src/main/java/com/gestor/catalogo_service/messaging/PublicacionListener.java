package com.gestor.catalogo_service.messaging;



import com.fasterxml.jackson.databind.ObjectMapper;

import com.gestor.catalogo_service.model.CatalogoItem;
import com.gestor.catalogo_service.service.CatalogoService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Component
public class PublicacionListener {

    private final CatalogoService svc;
    private final ObjectMapper mapper = new ObjectMapper();

    public PublicacionListener(CatalogoService svc){ this.svc = svc; }

    @RabbitListener(queues = RabbitConfig.QUEUE)
    public void onPublished(String json){
        try {
            Map<String,Object> evt = mapper.readValue(json, Map.class);

            CatalogoItem item = CatalogoItem.builder()
                    .publicacionId(UUID.fromString((String)evt.get("id")))
                    .titulo((String) evt.get("titulo"))
                    .autorPrincipalId((String) evt.get("autorPrincipalId"))
                    .tipo((String) evt.get("tipo"))
                    .palabras(String.join(",", (List<String>) evt.getOrDefault("palabrasClave", List.of())))
                    .identificador((String) evt.get("identificador"))
                    .resumen((String) evt.get("resumen"))
                    .fechaPublicacion(Instant.now())
                    .build();

            svc.guardar(item);
        } catch (Exception e) {
            // loggear si quieres
        }
    }

}
