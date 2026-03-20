package com.edgar.inventory.product.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.edgar.inventory.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByStockLessThanEqual(Integer stock);
}
