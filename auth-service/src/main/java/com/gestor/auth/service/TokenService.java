package com.gestor.auth.service;


import com.gestor.auth.model.Usuario;
import com.gestor.auth.repository.UsuarioRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.stereotype.Service;

import java.security.KeyPair;
import java.security.interfaces.RSAPublicKey;
import java.time.Instant;
import java.util.Date;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TokenService {
    private final KeyPair keyPair;
    private final UsuarioRepository usuarioRepository;

    public TokenService(KeyPair keyPair, UsuarioRepository usuarioRepository) {
        this.keyPair = keyPair;
        this.usuarioRepository = usuarioRepository;
    }

    public String generarAccessToken(Usuario u) {
        Instant now = Instant.now();
        return Jwts.builder()
                .setId(UUID.randomUUID().toString())
                .setSubject(u.getId().toString())
                .setIssuer("auth-service")
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plusSeconds(60 * 15))) // 15 min
                .claim("email", u.getEmail())
                .claim("name", u.getNombres() + " " + u.getApellidos())
                .claim("roles", rolesAsStrings(u.getRoles()))
                .signWith(keyPair.getPrivate())
                .compact();
    }

    public String generarRefreshToken(Usuario u) {
        Instant now = Instant.now();
        return Jwts.builder()
                .setId(UUID.randomUUID().toString())
                .setSubject(u.getId().toString())
                .setIssuer("auth-service")
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plusSeconds(60L * 60 * 24 * 7))) // 7 días
                .claim("type", "refresh")
                .signWith(keyPair.getPrivate())
                .compact();
    }

    public Optional<Usuario> validarRefreshToken(String token) {
        try {
            var publicKey = (RSAPublicKey) keyPair.getPublic();

            var jwt = Jwts.parser()
                    .verifyWith(publicKey)      // verifica firma RS256
                    .build()
                    .parseSignedClaims(token);  // lanza si está mal firmado o mal formado

            var claims = jwt.getPayload();

            // Expiración (jjwt ya lanza si exp<now, pero igual podemos leerla)
            Date exp = claims.getExpiration();
            if (exp == null || exp.before(new Date())) {
                return Optional.empty();
            }

            // Debe ser de tipo "refresh"
            Object type = claims.get("type");
            if (type == null || !"refresh".equals(type.toString())) {
                return Optional.empty();
            }

            // Recuperar el usuario por el subject (UUID)
            String sub = claims.getSubject();
            UUID userId = UUID.fromString(sub);

            return usuarioRepository.findById(userId);
        } catch (IllegalArgumentException | SignatureException e) {
            // token mal formado o firma inválida
            return Optional.empty();
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public Optional<Usuario> validarAccessToken(String token) {
        try {
            var publicKey = (RSAPublicKey) keyPair.getPublic();
            var jwt = Jwts.parser()
                    .verifyWith(publicKey)
                    .build()
                    .parseSignedClaims(token);

            var claims = jwt.getPayload();
            Date exp = claims.getExpiration();
            if (exp == null || exp.before(new Date())) return Optional.empty();

            // debe NO ser refresh
            Object type = claims.get("type");
            if (type != null && "refresh".equals(type.toString())) return Optional.empty();

            UUID userId = UUID.fromString(claims.getSubject());
            return usuarioRepository.findById(userId);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    private Set<String> rolesAsStrings(Set<?> roles) {
        return roles.stream().map(Object::toString).collect(Collectors.toSet());
    }

}
