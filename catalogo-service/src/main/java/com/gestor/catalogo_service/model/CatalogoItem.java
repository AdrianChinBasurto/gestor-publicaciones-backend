package com.gestor.catalogo_service.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CatalogoItem {

    @Id
    @GeneratedValue
    private UUID id;

    private UUID publicacionId;
    private String titulo;
    private String autorPrincipalId;
    private String tipo;             // ARTICULO | LIBRO
    private String palabras;         // "k1,k2,k3"
    private String identificador;    // DOI/ISBN
    @Column(length=2000)
    private String resumen;

    private Instant fechaPublicacion;

    @ElementCollection
    private List<String> etiquetas;

}
