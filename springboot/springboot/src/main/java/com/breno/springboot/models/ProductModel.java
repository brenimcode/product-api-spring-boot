package com.breno.springboot.models;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

import org.springframework.hateoas.RepresentationModel;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "TB_PRODUCTS")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductModel extends RepresentationModel<ProductModel> implements Serializable {
    private static final long serialVersionUID = 1L; // Versão da serialização

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID idProduct;

    @Schema(example = "Laptop", description = "Product name", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    @Schema(example = "8000.00", description = "Product price", requiredMode = Schema.RequiredMode.REQUIRED)
    private BigDecimal value;

}
