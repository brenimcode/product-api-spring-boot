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
import com.breno.springboot.exceptions.TokenGenerationException;
import com.breno.springboot.exceptions.TokenValidationException;
import com.breno.springboot.models.UserModel;

@Service
public class TokenService {

    // Atributo que armazena o segredo para assinar o token, configurado no application.properties.
    @Value("${api.security.token.secret}")
    private String secret;

    /**
     * Gera um token JWT para o usuário fornecido.
     * @param user - Instância de UserModel contendo os detalhes do usuário.
     * @return String - Token JWT gerado.
     */
    public String generateToken(UserModel user) {
        try {
            // Define o algoritmo de assinatura HMAC com o segredo configurado.
            Algorithm algorithm = Algorithm.HMAC256(this.secret);
            
            // Cria o token JWT com as informações do emissor, assunto (login do usuário) e data de expiração.
            String token = JWT.create()
                .withIssuer("auth-api") // Define o emissor do token.
                .withSubject(user.getLogin()) // Define o assunto do token (login do usuário).
                .withExpiresAt(genExpirationDate()) // Define a data de expiração do token.
                .sign(algorithm); // Assina o token usando o algoritmo HMAC.

            return token; // Retorna o token gerado.
        } catch (JWTCreationException exception) {
            // Em caso de erro na geração do token, uma exceção é lançada com a mensagem de erro.
            throw new TokenGenerationException("Error while generating token", exception);
        }
    }

    /**
     * Valida um token JWT fornecido e retorna o login do usuário se o token for válido.
     * @param token - Token JWT a ser validado.
     * @return String - Login do usuário contido no token, ou uma string vazia se o token for inválido.
     */
    public String validateToken(String token) {
        try {
            // Define o algoritmo de verificação HMAC com o segredo configurado.
            Algorithm algorithm = Algorithm.HMAC256(this.secret);
            
            // Valida o token e retorna o assunto (login do usuário) se o token for válido.
            return JWT.require(algorithm)
                .withIssuer("auth-api") // Verifica se o emissor do token é o esperado.
                .build()
                .verify(token) // Verifica o token usando o algoritmo configurado.
                .getSubject(); // Obtém o assunto (login do usuário) do token.
        } catch (JWTVerificationException e) {
            // Se o token for inválido ou ocorrer um erro na verificação, retorna erro personalizado.
            throw new TokenValidationException("Invalid token", e);
        }
    }

    /**
     * Gera a data de expiração do token, configurada para 2 horas a partir do momento atual.
     * @return Instant - Data e hora de expiração do token.
     */
    public Instant genExpirationDate() {
        // Retorna a data/hora atual acrescida de 2 horas, no fuso horário de Brasília.
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}

