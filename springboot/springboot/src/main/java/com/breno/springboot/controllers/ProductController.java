package com.breno.springboot.controllers;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.breno.springboot.dtos.ProductRecordDTO;
import com.breno.springboot.dtos.ResponseDTO;
import com.breno.springboot.models.ProductModel;
import com.breno.springboot.repositories.ProductRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
public class ProductController {

    @Autowired
    ProductRepository productRepository;

    @PostMapping("/products")
    public ResponseEntity<?> saveProduct(@RequestBody @Valid ProductRecordDTO productRecordDto) {
        // Verificação de existência de produto (exemplo, caso o nome de produto deva
        // ser único)
        if (productRepository.existsByName(productRecordDto.name())) {
            ResponseDTO<ProductModel> errorResponse = new ResponseDTO<>(
                    "error",
                    "Product already exists with this name",
                    null);
            return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
        }

        ProductModel productModel = new ProductModel();
        BeanUtils.copyProperties(productRecordDto, productModel);

        try {
            ProductModel savedProduct = productRepository.save(productModel);
            ResponseDTO<ProductModel> successResponse = new ResponseDTO<>(
                    "success",
                    "Product created successfully",
                    savedProduct);
            return ResponseEntity.status(HttpStatus.CREATED).body(successResponse);
        } catch (Exception e) {
            ResponseDTO<ProductModel> errorResponse = new ResponseDTO<>(
                    "error",
                    "Internal error while creating the product",
                    null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @GetMapping("/products")
    @Tag(name = "Produtos" , description = "Informações do produto")
    @Operation(summary = "Listagem de produtos", description = "Essa função é responsavel por listar produtos.")
    public ResponseEntity<List<ProductModel>> getAllProducts() {
        List<ProductModel> productsList = productRepository.findAll();

        if (!productsList.isEmpty()) {

            for (ProductModel product : productsList) {
                // Adiciona link HATEOAS para cada produto
                product.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ProductController.class)
                        .getOneProduct(product.getIdProduct())).withSelfRel()); // Adiciona o link ao modelo

            }
        }

        return ResponseEntity.ok().body(productsList);
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<Object> getOneProduct(@PathVariable(value = "id") UUID id) {
        Optional<ProductModel> product = productRepository.findById(id);
        if (product.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found.");
        }
        product.get().add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ProductController.class)
                .getAllProducts()).withSelfRel());
        return ResponseEntity.ok().body(product.get());
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<Object> updateProduct(@PathVariable(value = "id") UUID id,
            @RequestBody @Valid ProductRecordDTO productRecordDto) {
        Optional<ProductModel> product = productRepository.findById(id);
        if (product.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found.");
        }
        var productModel = product.get();
        BeanUtils.copyProperties(productRecordDto, productModel);
        return ResponseEntity.ok().body(productRepository.save(productModel));
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<Object> deleteProduct(@PathVariable(value = "id") UUID id) {
        Optional<ProductModel> product = productRepository.findById(id);
        if (product.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found.");
        }
        productRepository.delete(product.get());
        return ResponseEntity.ok().body("Product deleted.");
    }

}
