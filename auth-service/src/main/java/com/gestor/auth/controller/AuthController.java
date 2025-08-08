package com.gestor.auth.controller;


import com.gestor.auth.dto.LoginRequest;
import com.gestor.auth.dto.TokenResponse;
import com.gestor.auth.event.AuditorPublisher;
import com.gestor.auth.model.Usuario;
import com.gestor.auth.service.TokenService;
import com.gestor.auth.service.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class AuthController {
    private final UsuarioService usuarioService;
    private final TokenService tokenService;
    private final AuditorPublisher auditorPublisher;

    public AuthController(UsuarioService usuarioService, TokenService tokenService, AuditorPublisher auditorPublisher) {
        this.usuarioService = usuarioService;
        this.tokenService = tokenService;
        this.auditorPublisher = auditorPublisher;
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody LoginRequest req) {
        var user = usuarioService.buscarPorEmail(req.getEmail())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Credenciales no correctas"));

        if (!usuarioService.passwordMatches(req.getPassword(), user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Credenciales Invalidas");
        }

        String access = tokenService.generarAccessToken(user);
        String refresh = tokenService.generarRefreshToken(user);

        //pUBLICAR EL EVENTOLOGIN
        auditorPublisher.publicar("auth-login", user.getEmail());

        return ResponseEntity.ok(TokenResponse.builder()
                .accessToken(access)
                .refreshToken(refresh)
                .tokenType("Bearer")
                .expiresIn(900)
                .build()
        );

    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestHeader(value = "Authorization", required = false) String auth) {
        // (Opcional) parsear subject del token si viene; para el deber basta con evento
        auditorPublisher.publicar("auth.logout", "logout");
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/introspect")
    public Map<String, Object> introspect(@RequestParam String token) {
        return tokenService.validarAccessToken(token)
                .map(u -> Map.of(
                        "active", true,
                        "sub", u.getId().toString(),
                        "email", u.getEmail(),
                        "roles", u.getRoles()
                ))
                .orElseGet(() -> Map.of("active", false));
    }

    @PostMapping("/refresh")
    public ResponseEntity<TokenResponse> resfresh(@RequestParam String token) {
        Usuario usuario = tokenService.validarRefreshToken(token)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Token invalido"));

        String access = tokenService.generarAccessToken(usuario);
        String nuevoRefresh = tokenService.generarRefreshToken(usuario);

        return ResponseEntity.ok(TokenResponse.builder()
                .accessToken(access)
                .refreshToken(nuevoRefresh)
                .tokenType("Bearer")
                .expiresIn(900)
                .build());
    }
}
