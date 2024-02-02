package com.alefligiero.agenciabackend.services;

import com.alefligiero.agenciabackend.domain.user.User;
import com.alefligiero.agenciabackend.services.exceptions.JWTException;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
@RequiredArgsConstructor
public class TokenService {

    @Value("${api.security.jwt.secret}")
    private String jwtSecret;

    public String generateToken(User user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(jwtSecret);
            return JWT.create()
                    .withIssuer("agencia-iff")
                    .withSubject(user.getEmail())
                    .withClaim("email", user.getEmail())
                    .withExpiresAt(getExpirationTime())
                    .sign(algorithm);
        } catch (JWTCreationException exception) {
            throw new JWTException("Error while generating token");
        }
    }

    public String validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(jwtSecret);
            return JWT.require(algorithm)
                    .withIssuer("agencia-iff")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException exception) {
            throw new JWTException("Error while validating token");
        }
    }

    private Instant getExpirationTime() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }

}
