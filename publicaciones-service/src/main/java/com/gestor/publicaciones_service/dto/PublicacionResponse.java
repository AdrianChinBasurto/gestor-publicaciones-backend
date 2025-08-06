package com.gestor.publicaciones_service.dto;

import com.gestor.publicaciones_service.model.EstadoPublicacion;
import com.gestor.publicaciones_service.model.TipoPublicacion;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class PublicacionResponse {
    private UUID id;
    private String titulo;
    private String resumen;
    private List<String> palabrasClave;
    private EstadoPublicacion estado;
    private int versionActual;
    private String autorPrincipalId;
    private List<String> coAutoresIds;
    private TipoPublicacion tipo;
    private String metadatos;
}
