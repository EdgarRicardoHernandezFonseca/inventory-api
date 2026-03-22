package com.edgar.inventory.stock.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.edgar.inventory.product.entity.Product;
import com.edgar.inventory.product.repository.ProductRepository;
import com.edgar.inventory.stock.entity.StockMovement;
import com.edgar.inventory.enums.MovementType;

@DataJpaTest
class StockMovementRepositoryTest {

    @Autowired
    private StockMovementRepository repository;

    @Autowired
    private ProductRepository productRepository;

    @Test
    void shouldFindByProductId() {
        Product product = productRepository.save(
                Product.builder()
                        .name("Tablet")
                        .price(BigDecimal.valueOf(800))
                        .stock(10)
                        .minStock(3)
                        .build()
        );

        StockMovement movement = StockMovement.builder()
                .product(product)
                .type(MovementType.INCREASE)
                .quantity(5)
                .build();

        repository.save(movement);

        var result = repository.findByProductId(product.getId());

        assertThat(result).isNotEmpty();
    }
}