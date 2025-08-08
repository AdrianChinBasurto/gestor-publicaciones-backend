package com.gestor.notificaciones_service.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor @AllArgsConstructor @Builder
public class Notificacion {
    @Id
    @GeneratedValue
    private UUID id;
    private String tipo;             // p.ej. "publication.published", "auth.login"
    @Column(length=4000) private String payload; // JSON string
    private Instant fecha = Instant.now();
    private boolean leida = false;
}
