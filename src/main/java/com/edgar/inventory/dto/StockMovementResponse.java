package com.edgar.inventory.dto;

import java.time.LocalDateTime;

import com.edgar.inventory.enums.MovementType;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @Builder
public class StockMovementResponse {
    private Long productId;
    private String productName;
    private MovementType type;
    private Integer quantity;
    private LocalDateTime date;
}
