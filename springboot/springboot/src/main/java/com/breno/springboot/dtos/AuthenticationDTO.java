package com.breno.springboot.dtos;

import io.swagger.v3.oas.annotations.media.Schema;

public record AuthenticationDTO(
    @Schema(example = "johndoe", description = "Username for authentication", required = true) 
    String login,

    @Schema(example = "password123", description = "Password for authentication", required = true) 
    String password
) {
}