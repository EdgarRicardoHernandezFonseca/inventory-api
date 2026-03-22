package com.edgar.inventory.stock.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.edgar.inventory.stock.service.StockService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/stock")
@RequiredArgsConstructor
public class StockController {

    private final StockService stockService;

    @SecurityRequirement(name = "bearerAuth")
    @PostMapping("/increase/{productId}")
    public ResponseEntity<String> increase(@PathVariable Long productId,
                                           @RequestParam Integer quantity) {
        stockService.increaseStock(productId, quantity);
        return ResponseEntity.ok("Stock increased");
    }

    @SecurityRequirement(name = "bearerAuth")
    @PostMapping("/decrease/{productId}")
    public ResponseEntity<String> decrease(@PathVariable Long productId,
                                           @RequestParam Integer quantity) {
        stockService.decreaseStock(productId, quantity);
        return ResponseEntity.ok("Stock decreased");
    }
}