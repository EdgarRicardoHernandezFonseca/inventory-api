package com.edgar.inventory.product.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.edgar.inventory.dto.ProductRequest;
import com.edgar.inventory.dto.ProductResponse;
import com.edgar.inventory.entity.Product;
import com.edgar.inventory.mapper.ProductMapper;
import com.edgar.inventory.product.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public ProductResponse create(ProductRequest request) {
        Product product = Product.builder()
                .name(request.getName())
                .price(request.getPrice())
                .stock(request.getStock())
                .minStock(request.getMinStock())
                .build();

        return ProductMapper.toResponse(productRepository.save(product));
    }

    public List<ProductResponse> getAll() {
        return productRepository.findAll()
                .stream()
                .map(ProductMapper::toResponse)
                .toList();
    }

    public List<ProductResponse> getLowStockProducts() {
        return productRepository.findByStockLessThanEqual(5)
                .stream()
                .map(ProductMapper::toResponse)
                .toList();
    }
}