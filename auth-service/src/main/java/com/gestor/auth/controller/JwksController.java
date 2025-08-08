package com.gestor.auth.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class JwksController {
    private final Map<String, Object> jwks;

    public JwksController(Map<String, Object> jwks) {
        this.jwks = jwks;
    }

    @GetMapping("/.well-known/jwks.json")
    public Map<String, Object> jwks() {
        return jwks;
    }

}
