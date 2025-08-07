package com.gestor.publicaciones_service.model;


import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import java.util.List;
import java.util.UUID;

@Entity
@Data
public class Publicacion {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    private String titulo;
    private String resumen;

    @ElementCollection
    private List<String> palabrasClave;

    @Enumerated(EnumType.STRING)
    private EstadoPublicacion estado;

    private int versionActual;

    private String autorPrincipalId;

    @ElementCollection
    private List<String> coAutoresIds;

    @Enumerated(EnumType.STRING)
    private TipoPublicacion tipo;

    @Column(columnDefinition = "TEXT")
    private String metadatos; //Json como un STRING

    private String identificador;


}
