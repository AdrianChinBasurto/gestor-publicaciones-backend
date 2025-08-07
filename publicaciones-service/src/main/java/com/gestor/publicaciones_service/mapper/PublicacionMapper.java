package com.gestor.publicaciones_service.mapper;

import com.gestor.publicaciones_service.dto.PublicacionRequest;
import com.gestor.publicaciones_service.dto.PublicacionResponse;
import com.gestor.publicaciones_service.model.Publicacion;

public class PublicacionMapper {

    public static Publicacion toEntity(PublicacionRequest dto){
        Publicacion p = new Publicacion();
        p.setTitulo(dto.getTitulo());
        p.setResumen(dto.getResumen());
        p.setPalabrasClave(dto.getPalabrasClave());
        p.setEstado(dto.getEstado());
        p.setVersionActual(dto.getVersionActual());
        p.setAutorPrincipalId(dto.getAutorPrincipalId());
        p.setCoAutoresIds(dto.getCoAutoresIds());
        p.setTipo(dto.getTipo());
        p.setMetadatos(dto.getMetadatos());
        return p;
    }

    public static PublicacionResponse toResponse(Publicacion entity){
        PublicacionResponse dto = new PublicacionResponse();
        dto.setId(entity.getId());
        dto.setTitulo(entity.getTitulo());
        dto.setResumen(entity.getResumen());
        dto.setPalabrasClave(entity.getPalabrasClave());
        dto.setEstado(entity.getEstado());
        dto.setVersionActual(entity.getVersionActual());
        dto.setAutorPrincipalId(entity.getAutorPrincipalId());
        dto.setCoAutoresIds(entity.getCoAutoresIds());
        dto.setTipo(entity.getTipo());
        dto.setMetadatos(entity.getMetadatos());
        dto.setIdentificador(entity.getIdentificador());
        return dto;

    }

}
