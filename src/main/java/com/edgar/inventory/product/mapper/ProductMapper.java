package com.edgar.inventory.product.mapper;

import com.edgar.inventory.product.dto.ProductResponse;
import com.edgar.inventory.product.entity.Product;

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