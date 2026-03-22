package com.edgar.inventory.product.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Pageable;

import com.edgar.inventory.product.entity.Product;

@DataJpaTest
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    @DisplayName("Should find products with low stock")
    void shouldFindLowStockProducts() {
        Product product = Product.builder()
                .name("Laptop")
                .price(BigDecimal.valueOf(2000))
                .stock(3)
                .minStock(5)
                .build();

        productRepository.save(product);

        var result = productRepository.findByStockLessThanEqual(5);

        assertThat(result).isNotEmpty();
        assertThat(result.get(0).getName()).isEqualTo("Laptop");
    }
    
    @Test
    @DisplayName("Should find products by name")
    void shouldFindByName() {
        Product product = Product.builder()
                .name("Gaming Laptop")
                .price(BigDecimal.valueOf(3000))
                .stock(10)
                .minStock(5)
                .build();

        productRepository.save(product);

        var result = productRepository.findByNameContainingIgnoreCase("laptop", Pageable.unpaged());

        assertThat(result.getContent()).isNotEmpty();
    }
    
    @Test
    @DisplayName("Should find products by price range")
    void shouldFindByPriceRange() {
        Product product = Product.builder()
                .name("Phone")
                .price(BigDecimal.valueOf(1000))
                .stock(10)
                .minStock(2)
                .build();

        productRepository.save(product);

        var result = productRepository.findByPriceBetween(
                BigDecimal.valueOf(500),
                BigDecimal.valueOf(1500),
                Pageable.unpaged()
        );

        assertThat(result.getContent()).isNotEmpty();
    }
}