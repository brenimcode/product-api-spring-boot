package com.breno.springboot.dtos;

import com.breno.springboot.models.UserRole;

import io.swagger.v3.oas.annotations.media.Schema;

public record RegisterDTO(
    @Schema(example = "johndoe", description = "Username for registration", required = true)
    String login,

    @Schema(example = "password123", description = "Password for the new user", required = true)
    String password,

    @Schema(example = "USER", description = "Role of the user", required = true)
    UserRole role
) {
}