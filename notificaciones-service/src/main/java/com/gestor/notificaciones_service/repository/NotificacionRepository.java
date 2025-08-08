package com.gestor.notificaciones_service.repository;

import com.gestor.notificaciones_service.model.Notificacion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface NotificacionRepository extends JpaRepository<Notificacion, UUID> {
}
