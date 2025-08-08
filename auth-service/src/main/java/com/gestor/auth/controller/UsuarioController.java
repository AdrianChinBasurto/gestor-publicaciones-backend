package com.gestor.auth.controller;


import com.gestor.auth.dto.UsuarioRequest;
import com.gestor.auth.dto.UsuarioResponse;
import com.gestor.auth.service.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.token.TokenService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping("/register")
    public ResponseEntity<UsuarioResponse> register(@RequestBody UsuarioRequest request) {
        UsuarioResponse res = usuarioService.registrar(request);
        return ResponseEntity.ok(res);
    }
}
