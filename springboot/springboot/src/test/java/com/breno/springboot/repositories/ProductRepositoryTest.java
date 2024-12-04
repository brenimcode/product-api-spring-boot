package com.breno.springboot.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import com.breno.springboot.dtos.ProductRecordDTO;
import com.breno.springboot.models.ProductModel;

import jakarta.persistence.EntityManager;

@DataJpaTest
@ActiveProfiles("test")
public class ProductRepositoryTest {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    EntityManager entityManager;

    @Test
    @DisplayName("Should save product successfully")
    void saveProductSuccess() {
        ProductModel productModel = new ProductModel();
        BeanUtils.copyProperties(new ProductRecordDTO("testProduct", new BigDecimal(10)), productModel);

        ProductModel savedProduct = productRepository.save(productModel);

        assertNotNull(savedProduct);
        assertEquals("testProduct", savedProduct.getName());
    }

}
