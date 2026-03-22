package com.edgar.inventory.stock.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.edgar.inventory.stock.entity.StockMovement;

public interface StockMovementRepository extends JpaRepository<StockMovement, Long> {

    List<StockMovement> findByProductId(Long productId);
}