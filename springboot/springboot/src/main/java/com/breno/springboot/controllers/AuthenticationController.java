package com.breno.springboot.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.breno.springboot.dtos.AuthenticationDTO;
import com.breno.springboot.dtos.RegisterDTO;
import com.breno.springboot.models.UserModel;
import com.breno.springboot.repositories.UserRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository repository;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AuthenticationDTO data) {
        // Cria um token de autenticação usando as credenciais fornecidas (login e
        // senha)
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.password());

        // Autentica o token com o AuthenticationManager, verificando as credenciais
        var auth = this.authenticationManager.authenticate(usernamePassword);

        // Retorna uma resposta HTTP 200 (OK) se a autenticação for bem-sucedida
        return ResponseEntity.ok().build();
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid RegisterDTO data) {
        // Verifica se já existe um usuário com o mesmo login no banco de dados
        if (this.repository.findByLogin(data.login()) != null) {
            // Se o login já estiver em uso, retorna uma resposta HTTP 400 (Bad Request)
            return ResponseEntity.badRequest().build();
        }

        // Criptografa a senha fornecida pelo usuário usando BCrypt
        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());

        // Cria um novo modelo de usuário com as informações fornecidas e a senha
        // criptografada
        UserModel newUser = new UserModel(data.login(), encryptedPassword, data.role());

        // Salva o novo usuário no banco de dados
        this.repository.save(newUser);

        // Retorna uma resposta HTTP 200 (OK) se o registro for bem-sucedido
        return ResponseEntity.ok().build();
    }

}
