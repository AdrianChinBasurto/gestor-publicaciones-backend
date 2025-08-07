package com.gestor.publicaciones_service.dto;

import com.gestor.publicaciones_service.model.TipoPublicacion;
import lombok.Data;

import java.util.UUID;

@Data
public class PublicationReadyEvent {
    private UUID id;
    private String titulo;
    private String identificador;
    private TipoPublicacion tipo;
    private int version;
}
