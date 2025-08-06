package com.gestor.publicaciones_service.controller;

import com.gestor.publicaciones_service.dto.PublicacionRequest;
import com.gestor.publicaciones_service.dto.PublicacionResponse;
import com.gestor.publicaciones_service.mapper.PublicacionMapper;
import com.gestor.publicaciones_service.model.EstadoPublicacion;
import com.gestor.publicaciones_service.model.Publicacion;
import com.gestor.publicaciones_service.service.PublicacionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/publicaciones")
@CrossOrigin(origins = "*")
public class PublicacionController {

    public final PublicacionService publicacionService;

    public PublicacionController(PublicacionService publicacionService) {
        this.publicacionService = publicacionService;
    }

    @GetMapping
    public List<PublicacionResponse> obtenerTodas(){
        return publicacionService.listarTodas().stream()
                .map(PublicacionMapper::toResponse)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PublicacionResponse> obtenerPorId(@PathVariable UUID id){
        return publicacionService.obtenerPorId(id)
                .map(PublicacionMapper::toResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<PublicacionResponse> crear(@RequestBody PublicacionRequest request){
        Publicacion entity = PublicacionMapper.toEntity(request);
        Publicacion guardada = publicacionService.guardar(entity);
        return ResponseEntity.ok(PublicacionMapper.toResponse(guardada));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable UUID id){
        publicacionService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    //Cambios de estado
    @PatchMapping("/{id}/estado")
    public ResponseEntity<PublicacionResponse> cambiarEstado(
            @PathVariable UUID id,
            @RequestParam EstadoPublicacion estado){

        Publicacion actualizada = publicacionService.cambiarEstado(id, estado);
        return ResponseEntity.ok(PublicacionMapper.toResponse(actualizada));
    }

}
