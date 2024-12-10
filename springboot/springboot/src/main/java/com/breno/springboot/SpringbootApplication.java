package com.breno.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;




@SpringBootApplication
@OpenAPIDefinition(
	info = @Info(
		title = "Gerenciamento de Produtos",
		description = "API RESTFul de produtos",
		version = "1"
	)
)
public class SpringbootApplication {
	

	public static void main(String[] args) {
		SpringApplication.run(SpringbootApplication.class, args);
	}

}
