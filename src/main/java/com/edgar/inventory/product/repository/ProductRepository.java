package com.edgar.inventory.product.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.edgar.inventory.product.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByStockLessThanEqual(Integer stock);
    
    Page<Product> findByNameContainingIgnoreCase(String name, Pageable pageable);

    Page<Product> findByPriceBetween(BigDecimal min, BigDecimal max, Pageable pageable);

    Page<Product> findByStockLessThanEqual(Integer stock, Pageable pageable);
}
