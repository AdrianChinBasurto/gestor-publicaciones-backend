package com.gestor.auth.controller;


import com.gestor.auth.dto.UsuarioRequest;
import com.gestor.auth.dto.UsuarioResponse;
import com.gestor.auth.service.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    private final UsuarioService usuarioService;

    public AuthController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping("/register")
    public ResponseEntity<UsuarioResponse> register(@RequestBody UsuarioRequest request) {
        UsuarioResponse res = usuarioService.registrar(request);
        return ResponseEntity.ok(res);
    }
}
