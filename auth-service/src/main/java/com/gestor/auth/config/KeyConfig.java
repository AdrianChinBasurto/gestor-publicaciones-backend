package com.gestor.auth.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.security.*;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class KeyConfig {
    @Bean
    public KeyPair keyPair() {
        try{
            KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
            kpg.initialize(2048);
            return kpg.generateKeyPair();
        }catch(Exception e){
            throw new RuntimeException("No se pudo general claves RSA",e);
        }
    }

    //Represantacion simple de JWKS 1 CLAVE

    @Bean
    public Map<String, Object> jwks(KeyPair kp) {
        var pub = (java.security.interfaces.RSAPublicKey)kp.getPublic();
        String n = Base64.getUrlEncoder().withoutPadding().encodeToString(pub.getModulus().toByteArray());
        String e = Base64.getUrlEncoder().withoutPadding().encodeToString(pub.getPublicExponent().toByteArray());

        return Map.of(
                "key", new Object[]{
                        Map.of(
                                "kty", "RSA",
                                "alg","RS256",
                                "use","sig",
                                "kid", "auth-service-key-1",
                                "n",n,
                                "e",e
                        ),
                }
        );


    }


}
