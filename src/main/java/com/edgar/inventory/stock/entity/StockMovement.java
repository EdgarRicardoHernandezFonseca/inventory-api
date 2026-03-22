package com.edgar.inventory.stock.entity;

import java.time.LocalDateTime;

import com.edgar.inventory.enums.MovementType;
import com.edgar.inventory.enums.Role;
import com.edgar.inventory.product.entity.Product;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "stock_movements")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class StockMovement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private Product product;

    @Enumerated(EnumType.STRING)
    private MovementType type;

    @Column(nullable = false)
    private Integer quantity;

    private String reason; // opcional (venta, ajuste, etc.)

    private LocalDateTime createdAt;
}
