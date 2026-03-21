package com.edgar.inventory.product.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
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

    public Page<ProductResponse> getAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        return productRepository.findAll(pageable)
                .map(ProductMapper::toResponse);
    }

    public List<ProductResponse> getLowStockProducts() {
        return productRepository.findByStockLessThanEqual(5)
                .stream()
                .map(ProductMapper::toResponse)
                .toList();
    }
    
    public Page<ProductResponse> search(
            String name,
            BigDecimal minPrice,
            BigDecimal maxPrice,
            Integer lowStock,
            int page,
            int size) {

        Pageable pageable = PageRequest.of(page, size);

        if (name != null) {
            return productRepository
                    .findByNameContainingIgnoreCase(name, pageable)
                    .map(ProductMapper::toResponse);
        }

        if (minPrice != null && maxPrice != null) {
            return productRepository
                    .findByPriceBetween(minPrice, maxPrice, pageable)
                    .map(ProductMapper::toResponse);
        }

        if (lowStock != null) {
            return productRepository
                    .findByStockLessThanEqual(lowStock, pageable)
                    .map(ProductMapper::toResponse);
        }

        return productRepository.findAll(pageable)
                .map(ProductMapper::toResponse);
    }
}