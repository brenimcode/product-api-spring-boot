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
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@Tag(name = "Products", description = "Operations related to products")
public class ProductController {

    @Autowired
    ProductRepository productRepository;

    @PostMapping("/products")
    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(summary = "Save product", description = "Responsible for saving a new product in the system.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Product created successfully."),
            @ApiResponse(responseCode = "409", description = "Product already exists with this name."),
            @ApiResponse(responseCode = "500", description = "Internal error while creating the product.")
    })
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
    @Operation(summary = "List products", description = "Responsible for listing all registered products.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "List of products", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductModel.class), examples = @ExampleObject(value = "[{\"idProduct\":\"1234\",\"name\":\"Laptop\",\"price\":1200.0, \"_links\": {\"self\": {\"href\": \"/products/1234\"}}}]"))),
            @ApiResponse(responseCode = "404", description = "No products found",content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<List<ProductModel>> getAllProducts() {
        List<ProductModel> productsList = productRepository.findAll();

        if (productsList.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        for (ProductModel product : productsList) {
            // Adiciona link HATEOAS para cada produto
            product.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ProductController.class)
                    .getOneProduct(product.getIdProduct())).withSelfRel()); // Adiciona o link ao modelo

        }

        return ResponseEntity.ok().body(productsList);
    }

    @GetMapping("/products/{id}")
    @Operation(summary = "Get product by ID", description = "Fetches the details of a specific product by its ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successful request. Returns the product details.", content = @Content(schema = @Schema(implementation = ProductModel.class), examples = @ExampleObject(value = "{\n"
                    +
                    "    \"idProduct\": \"035ffa72-d29b-4e86-8d07-4e100b3bfb0b\",\n" +
                    "    \"name\": \"Mouse\",\n" +
                    "    \"value\": 120.00,\n" +
                    "    \"_links\": {\n" +
                    "        \"self\": {\n" +
                    "            \"href\": \"http://localhost:8080/products\"\n" +
                    "        }\n" +
                    "    }\n" +
                    "}"))),
            @ApiResponse(responseCode = "404", description = "Product not found.")
    })
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
    @Operation(summary = "Update product", description = "Responsible for updating the details of a specific product.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Product updated successfully."),
            @ApiResponse(responseCode = "404", description = "Product not found.")
    })
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
    @Operation(summary = "Delete product", description = "Responsible for deleting a specific product by its ID.", responses = {
            @ApiResponse(responseCode = "200", description = "Product deleted successfully.", content = @Content(schema = @Schema(type = "string", example = "Product deleted."))),
            @ApiResponse(responseCode = "404", description = "Product not found.", content = @Content(schema = @Schema(type = "string", example = "Product not found.")))
    })
    public ResponseEntity<Object> deleteProduct(@PathVariable(value = "id") UUID id) {
        Optional<ProductModel> product = productRepository.findById(id);
        if (product.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found.");
        }
        productRepository.delete(product.get());
        return ResponseEntity.ok().body("Product deleted.");
    }

}
