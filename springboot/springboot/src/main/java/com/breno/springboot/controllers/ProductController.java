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

import com.breno.springboot.dtos.ProductRecordDto;
import com.breno.springboot.models.ProductModel;
import com.breno.springboot.repositories.ProductRepository;

import jakarta.validation.Valid;

@RestController
public class ProductController {

    @Autowired
    ProductRepository productRepository;

    @PostMapping("/products")
    public ResponseEntity<ProductModel> saveProduct(@RequestBody @Valid ProductRecordDto productRecordDto) {
        ProductModel productModel = new ProductModel();
        // Converte Dto para Model usando um recurso do Spring:
        BeanUtils.copyProperties(productRecordDto, productModel);
        // Implementar melhorias como mensagem personalizdas e tratamento especifico

        return ResponseEntity.status(HttpStatus.CREATED).body(productRepository.save(productModel));
    }

    @GetMapping("/products")
    public ResponseEntity<List<ProductModel>> getAllProducts() {
        List<ProductModel> productsList = productRepository.findAll();

        if(!productsList.isEmpty()){

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
            @RequestBody @Valid ProductRecordDto productRecordDto) {
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
