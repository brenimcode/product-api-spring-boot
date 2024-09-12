package com.breno.springboot.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.breno.springboot.repositories.UserRepository;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    TokenService tokenService;

    @Autowired
    UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {
        String requestURI = request.getRequestURI(); // Obtém o URI da requisição

        // Verifica se o endpoint é "/products" ou se o método é POST, PUT, DELETE para
        // "/products"
        if (requestURI.startsWith("/products")) {
            var token = this.recoverToken(request);
            if (token != null) {
                try {
                    var login = tokenService.validateToken(token);
                    if (login != null) { // Verifica se o token é válido
                        UserDetails user = userRepository.findByLogin(login);
                        if (user != null) {
                            // Cria o objeto de autenticação com as credenciais do usuário
                            var authentication = new UsernamePasswordAuthenticationToken(user, null,
                                    user.getAuthorities());
                            // Define o contexto de segurança com o usuário autenticado
                            SecurityContextHolder.getContext().setAuthentication(authentication);
                        }
                    } else {
                        // Token inválido
                        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                        response.getWriter().write("Invalid token.");
                        return; // Interrompe o filtro
                    }
                } catch (Exception e) {
                    // Erro ao validar o token
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.getWriter().write("Token validation error: " + e.getMessage());
                    return; // Interrompe o filtro
                }
            } else {
                // Token não fornecido
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Token is missing.");
                return; // Interrompe o filtro
            }
        }

        filterChain.doFilter(request, response);
    }

    // Método para extrair o token do cabeçalho "Authorization"
    private String recoverToken(HttpServletRequest request) {
        var authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer "))
            return null;
        return authHeader.replace("Bearer ", "");
    }

}
