package com.gestor.auth.service;


import com.gestor.auth.dto.UsuarioRequest;
import com.gestor.auth.dto.UsuarioResponse;
import com.gestor.auth.model.Rol;
import com.gestor.auth.model.Usuario;
import com.gestor.auth.repository.UsuarioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashSet;

@Service
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public UsuarioResponse registrar(UsuarioRequest request) {
        //Es un email unico
        if(usuarioRepository.existsByEmail(request.getEmail())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El email ya existe");
        }

        //Rol se elige por defecto si esta vacio
        var roles = request.getRoles() == null || request.getRoles().isEmpty()
                ? new HashSet<Rol>() {{ add(Rol.AUTOR); }}
                : new HashSet<>(request.getRoles());

        //Mapeados de DTO a entidad
        Usuario user = Usuario.builder()
                .nombres(request.getNombres())
                .apellidos(request.getApellidos())
                .email(request.getEmail())
                .afiliacion(request.getAfiliacion())
                .orcid(request.getOrcid())
                .biografia(request.getBiografia())
                .fotoUrl(request.getFotoURL())
                .roles(roles)
                .password(passwordEncoder.encode(request.getPassword()))
                .build();

        Usuario guardado = usuarioRepository.save(user);

        // Entidad -> Response
        return UsuarioResponse.builder()
                .id(guardado.getId())
                .nombres(guardado.getNombres())
                .apellidos(guardado.getApellidos())
                .email(guardado.getEmail())
                .afiliacion(guardado.getAfiliacion())
                .orcid(guardado.getOrcid())
                .biografia(guardado.getBiografia())
                .fotoUrl(guardado.getFotoUrl())
                .roles(guardado.getRoles())
                .build();

    }

}
