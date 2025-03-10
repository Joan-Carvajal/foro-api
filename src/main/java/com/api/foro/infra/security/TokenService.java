package com.api.foro.infra.security;

import com.api.foro.domain.user.Usuario;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

@Service
public class TokenService {

    @Value("${api.security.secret}")
    private String apiSecrect;
    public String generarToken(Usuario usuario) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(apiSecrect);
            return  JWT.create()
                    .withIssuer("Foro")
                    .withSubject(usuario.getEmail())
                    .withClaim("id", usuario.getId())
                    .withExpiresAt(generarFechaExpiracion())
                    .withNotBefore(new Date(System.currentTimeMillis()))
                    .sign(algorithm);
        } catch (JWTCreationException exception){
            throw  new RuntimeException();
        }

    }

    public String getSubject(String token) {
        if (token == null){
            throw new RuntimeException();
        }
        DecodedJWT verifier = null;
        try {
            Algorithm algorithm = Algorithm.HMAC256(apiSecrect);
            verifier = JWT.require(algorithm)
                    // specify any specific claim validations
                    .withIssuer("Foro")
                    // reusable verifier instance
                    .build()
                    .verify(token);
            verifier.getSubject();
        } catch (JWTVerificationException exception) {
            System.out.println(exception.toString());
        }
        if (verifier.getSubject() ==null){
            throw  new RuntimeException("Verifier invalido");
        }
        return verifier.getSubject();

    }
        private Instant generarFechaExpiracion(){
            return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-05:00"));
        }
}
