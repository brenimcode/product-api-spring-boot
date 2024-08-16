package com.breno.springboot.models;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "TB_USERS")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class UserModel implements UserDetails {
    

    // Depois trocar para UUID.
    private String id;
    private String login;
    private String password;

    private String role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Aqui vai verificar o role(cargo) por exemplo, se o usuario é um admin ele pode fazer POST se nao, nao pode.

        throw new UnsupportedOperationException("Unimplemented method 'getAuthorities'");
    }

    @Override
    public String getUsername() {
        // Representa o nosso username.
        return this.login;
    }
}
