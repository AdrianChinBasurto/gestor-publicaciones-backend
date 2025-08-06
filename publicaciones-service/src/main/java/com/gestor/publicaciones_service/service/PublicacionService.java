package com.gestor.publicaciones_service.service;

import com.gestor.publicaciones_service.model.EstadoPublicacion;
import com.gestor.publicaciones_service.model.Publicacion;
import com.gestor.publicaciones_service.repository.PublicacionRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PublicacionService {

    private final PublicacionRepository publicacionRepository;

    public PublicacionService(PublicacionRepository publicacionRepository) {
        this.publicacionRepository = publicacionRepository;
    }

    public List<Publicacion> listarTodas(){
        return publicacionRepository.findAll();
    }

    public Optional<Publicacion> obtenerPorId(UUID id){
        return publicacionRepository.findById(id);
    }

    public Publicacion guardar(Publicacion publicacion){
        return publicacionRepository.save(publicacion);
    }

    public void eliminar(UUID id){

        Publicacion pub = publicacionRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Publicación no encontrada"));

        publicacionRepository.delete(pub);
    }

    //Cambiar de estado
    public Publicacion cambiarEstado(UUID id, EstadoPublicacion nuevoEstado){
        Publicacion pub = publicacionRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Publicacion no encontrada"));

        validarTransicion(pub.getEstado(), nuevoEstado);

        pub.setEstado(nuevoEstado);

        //Incrementar version si aplica
        if(nuevoEstado == EstadoPublicacion.CAMBIOS_SOLICITADOS){
            pub.setVersionActual(pub.getVersionActual()+1);
        }

        return publicacionRepository.save(pub);
    }

    private void validarTransicion(EstadoPublicacion actual, EstadoPublicacion nuevo){
        switch (actual){
            case BORRADOR:
                if (nuevo != EstadoPublicacion.EN_REVISION) throw transicionInvalida(actual, nuevo);
                break;
            case EN_REVISION:
                if (nuevo != EstadoPublicacion.CAMBIOS_SOLICITADOS && nuevo != EstadoPublicacion.APROBADO)
                    throw transicionInvalida(actual, nuevo);
                break;
            case CAMBIOS_SOLICITADOS:
                if (nuevo != EstadoPublicacion.EN_REVISION) throw transicionInvalida(actual, nuevo);
                break;
            case APROBADO:
                if (nuevo != EstadoPublicacion.PUBLICADO) throw transicionInvalida(actual, nuevo);
                break;
            case PUBLICADO:
                if (nuevo != EstadoPublicacion.RETIRADO) throw transicionInvalida(actual, nuevo);
            case RETIRADO:
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No se puede cambiar de estado");
        }
    }

    private ResponseStatusException transicionInvalida(EstadoPublicacion actual, EstadoPublicacion nuevo){
        return new ResponseStatusException(HttpStatus.BAD_REQUEST, "El estado no puede ser introducido" + actual + "->" + nuevo);
    }
}
