package com.edgar.inventory.mapper;

import com.edgar.inventory.dto.ProductResponse;
import com.edgar.inventory.entity.Product;

public class ProductMapper {

    public static ProductResponse toResponse(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .stock(product.getStock())
                .minStock(product.getMinStock())
                .lowStock(product.getStock() <= product.getMinStock())
                .build();
    }
}