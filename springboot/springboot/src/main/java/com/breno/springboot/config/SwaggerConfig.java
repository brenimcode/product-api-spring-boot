package com.breno.springboot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class SwaggerConfig {
    
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI().info(new Info()
            .title("Product Management")
            .description("RESTful API for products")
            .version("1")).schemaRequirement("Bearer Authentication", createSecurityScheme());
    }

    private SecurityScheme createSecurityScheme(){
        return new SecurityScheme()
        .name("Bearer Authentication")
        .type(SecurityScheme.Type.HTTP)
        .scheme("bearer")
        .bearerFormat("JWT");
    }
}
