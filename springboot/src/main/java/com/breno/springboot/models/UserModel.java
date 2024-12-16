package com.breno.springboot.models;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Schema(example = "Mark", description = "Username of the user", requiredMode = Schema.RequiredMode.REQUIRED)
    private String login;

    @Schema(example = "123", description = "Password of the user", requiredMode = Schema.RequiredMode.REQUIRED)
    private String password;

    private UserRole role;

    public UserModel(String login, String password, UserRole role){
        this.login = login;
        this.password = password;
        this.role = role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Verifica o papel (role) do usuário
        if (this.role == UserRole.ADMIN) {
            // Se o usuário for ADMIN, ele terá permissões de ADMIN e USER.
            return List.of(
                    new SimpleGrantedAuthority("ROLE_ADMIN"),
                    new SimpleGrantedAuthority("ROLE_USER"));
        }
        // Se o usuário não for ADMIN, ele terá apenas a permissão de USER.
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getUsername() {
        // Representa o nosso username.
        return this.login;
    }
}
