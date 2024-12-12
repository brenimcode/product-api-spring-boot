package com.breno.springboot.dtos;

import java.math.BigDecimal;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


public record ProductRecordDTO(
    @NotBlank 
    @Schema(example = "Notebook", description = "Nome do produto", requiredMode = Schema.RequiredMode.REQUIRED)
    String name,

    @NotNull 
    @Schema(example = "1999.99", description = "Preço do produto", requiredMode = Schema.RequiredMode.REQUIRED)
    BigDecimal value
) {
}