package com.gestor.notificaciones_service.controller;

import com.gestor.notificaciones_service.model.Notificacion;
import com.gestor.notificaciones_service.repository.NotificacionRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/notificaciones")
@CrossOrigin(origins = "*")
public class NotificacionController {
    private final NotificacionRepository repo;
    public NotificacionController(NotificacionRepository repo){ this.repo=repo; }

    @GetMapping
    public List<Notificacion> listar(){ return repo.findAll(); }

    @PostMapping("/{id}/leer")
    public Notificacion marcarLeida(@PathVariable UUID id){
        var n = repo.findById(id).orElseThrow();
        n.setLeida(true);
        return repo.save(n);
    }
}
