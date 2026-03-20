package com.edgar.inventory.dto;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ProductRequest {
    private String name;
    private BigDecimal price;
    private Integer stock;
    private Integer minStock;
}
