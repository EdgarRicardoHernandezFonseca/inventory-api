package com.edgar.inventory.stock.service;

import java.time.LocalDateTime;

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

    public void increaseStock(Long productId, Integer quantity) {
        Product product = getProduct(productId);

        product.setStock(product.getStock() + quantity);
        productRepository.save(product);

        saveMovement(product, MovementType.INCREASE, quantity, "Stock refill");
    }

    public void decreaseStock(Long productId, Integer quantity) {
        Product product = getProduct(productId);

        if (product.getStock() < quantity) {
            throw new RuntimeException("Not enough stock");
        }

        product.setStock(product.getStock() - quantity);
        productRepository.save(product);

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
