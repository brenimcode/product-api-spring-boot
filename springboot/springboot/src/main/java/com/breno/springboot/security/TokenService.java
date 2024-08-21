package com.breno.springboot.security;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.breno.springboot.models.UserModel;

@Service
public class TokenService {

    // Indica que nas variaveis de ambiente, vai estar armazenado esse atributo secret, vou configurar no app.properties
    @Value("${api.security.token.secret}")
    private String secret;


    public String generateToken(UserModel user){
        try{
            // 
            Algorithm algorithm = Algorithm.HMAC256(this.secret);
            String token = JWT.create()
            .withIssuer("auth-api")
            .withSubject(user.getLogin())
            .withExpiresAt(genExpirationDate())
            .sign(algorithm);

            return token;
        }
        catch(JWTCreationException exception){
            throw new RuntimeException("Error while generation token", exception);
        }
    }

    public String validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(this.secret);
            return JWT.require(algorithm)
            .withIssuer("auth-api")
            .build()
            .verify(token)
            .getSubject();
        } catch (JWTVerificationException e) {
            return "";
        }
    }


    // Criando a classe que retorna o tempo agora + 2h, com timezone de brasilia
    public Instant genExpirationDate() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }


}
