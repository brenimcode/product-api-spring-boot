package com.breno.springboot.dtos;

import com.breno.springboot.models.UserRole;

public record RegisterDTO(String login, String password, UserRole role) {
    
}
