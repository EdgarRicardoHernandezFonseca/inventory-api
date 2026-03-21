package com.edgar.inventory.stock.service;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.edgar.inventory.entity.Product;
import com.edgar.inventory.entity.StockMovement;
import com.edgar.inventory.enums.MovementType;
import com.edgar.inventory.exception.ResourceNotFoundException;
import com.edgar.inventory.product.repository.ProductRepository;
import com.edgar.inventory.repository.StockMovementRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StockService {

    private final ProductRepository productRepository;
    private final StockMovementRepository movementRepository;
    private static final Logger log = LoggerFactory.getLogger(StockService.class);

    public void increaseStock(Long productId, Integer quantity) {
        Product product = getProduct(productId);

        product.setStock(product.getStock() + quantity);
        productRepository.save(product);

        log.info("Stock increased | productId={} | quantity={} | newStock={}",
                productId, quantity, product.getStock());

        saveMovement(product, MovementType.INCREASE, quantity, "Stock refill");
    }

    public void decreaseStock(Long productId, Integer quantity) {
        Product product = getProduct(productId);

        if (product.getStock() < quantity) {
            log.warn("Stock decrease failed | productId={} | requested={} | available={}",
                    productId, quantity, product.getStock());

            throw new RuntimeException("Not enough stock");
        }

        product.setStock(product.getStock() - quantity);
        productRepository.save(product);

        log.info("Stock decreased | productId={} | quantity={} | newStock={}",
                productId, quantity, product.getStock());

        saveMovement(product, MovementType.DECREASE, quantity, "Sale");
    }

    private Product getProduct(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
    }

    private void saveMovement(Product product, MovementType type, Integer quantity, String reason) {
        StockMovement movement = StockMovement.builder()
                .product(product)
                .type(type)
                .quantity(quantity)
                .reason(reason)
                .createdAt(LocalDateTime.now())
                .build();

        movementRepository.save(movement);
    }
}
