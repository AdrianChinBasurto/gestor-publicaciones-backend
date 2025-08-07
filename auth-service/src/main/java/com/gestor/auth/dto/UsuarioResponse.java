package com.gestor.auth.dto;


import com.gestor.auth.model.Rol;
import lombok.Builder;
import lombok.Data;

import java.util.Set;
import java.util.UUID;

@Data
@Builder
public class UsuarioResponse {

    private UUID id;
    private String nombres;
    private String apellidos;
    private String email;
    private String afiliacion;
    private String orcid;
    private String biografia;
    private String fotoUrl;
    private Set<Rol> roles;
}
