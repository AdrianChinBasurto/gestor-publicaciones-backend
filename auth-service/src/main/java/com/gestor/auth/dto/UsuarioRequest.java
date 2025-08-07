package com.gestor.auth.dto;

import com.gestor.auth.model.Rol;
import lombok.Data;

import java.util.Set;

@Data
public class UsuarioRequest {
    private String nombres;
    private String apellidos;
    private String email;
    private String afiliacion;
    private String orcid;
    private String biografia;
    private String fotoURL;
    private Set<Rol> roles;
    private String password;
}
