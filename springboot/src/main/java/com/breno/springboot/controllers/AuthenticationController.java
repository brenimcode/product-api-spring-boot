package com.breno.springboot.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.breno.springboot.dtos.AuthenticationDTO;
import com.breno.springboot.dtos.LoginResponseDTO;
import com.breno.springboot.dtos.RegisterDTO;
import com.breno.springboot.models.UserModel;
import com.breno.springboot.repositories.UserRepository;
import com.breno.springboot.security.TokenService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("auth")
@Tag(name = "Authentication", description = "User authentication and registration")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository repository;

    @Autowired
    private TokenService tokenService;


    @PostMapping("/login")
    @Operation(summary = "Login", description = "Authenticates a user and returns a JWT token.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully logged in, returns the token."),
            @ApiResponse(responseCode = "401", description = "Invalid credentials."),
    })
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid AuthenticationDTO data) {
        // Cria um token de autenticação usando as credenciais fornecidas (login e
        // senha)
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.password());

        try {
        var auth = this.authenticationManager.authenticate(usernamePassword);
        var token = tokenService.generateToken((UserModel) auth.getPrincipal());
        return ResponseEntity.ok(new LoginResponseDTO(token));
    } catch (BadCredentialsException e) {
        
         return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
         .body(new LoginResponseDTO("Login inválido")); // 401
    }

    }

    @PostMapping("/register")
    @Operation(summary = "Register", description = "Registers a new user in the system.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "User successfully created."),
            @ApiResponse(responseCode = "400", description = "User already registered."),
    })
    public ResponseEntity<String> register(@RequestBody @Valid RegisterDTO data) {
        // Verifica se já existe um usuário com o mesmo login no banco de dados
        if (this.repository.findByLogin(data.login()) != null) {
            // Se o login já estiver em uso, retorna uma resposta HTTP 400 (Bad Request)
            return ResponseEntity.badRequest().body("User already registered with this login.");
        }

        // Criptografa a senha fornecida pelo usuário usando BCrypt
        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());

        // Cria um novo modelo de usuário com as informações fornecidas e a senha
        // criptografada
        UserModel newUser = new UserModel(data.login(), encryptedPassword, data.role());

        // Salva o novo usuário no banco de dados
        this.repository.save(newUser);

        // Retorna uma resposta HTTP 200 (OK) se o registro for bem-sucedido
        return ResponseEntity.status(HttpStatus.CREATED).body("User created successfully.");
    }

}
