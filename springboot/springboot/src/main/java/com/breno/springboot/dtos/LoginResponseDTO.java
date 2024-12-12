package com.breno.springboot.dtos;

import io.swagger.v3.oas.annotations.media.Schema;

public record LoginResponseDTO(
    @Schema(example = "eyJhbGciOiJIUzI1NiIsInR5cCI", 
           description = "JWT token generated for authentication", required = true) 
    String token
) {
}