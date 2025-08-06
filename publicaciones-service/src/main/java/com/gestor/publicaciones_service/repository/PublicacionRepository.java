package com.gestor.publicaciones_service.repository;

import com.gestor.publicaciones_service.model.Publicacion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PublicacionRepository extends JpaRepository<Publicacion, UUID> {
}
