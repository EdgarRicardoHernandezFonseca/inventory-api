package com.edgar.inventory.stock.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.edgar.inventory.exception.ResourceNotFoundException;
import com.edgar.inventory.product.entity.Product;
import com.edgar.inventory.product.repository.ProductRepository;
import com.edgar.inventory.stock.repository.StockMovementRepository;

@ExtendWith(MockitoExtension.class)
class StockServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private StockMovementRepository movementRepository;

    @InjectMocks
    private StockService stockService;
    
    @Test
    void shouldIncreaseStock() {
        Product product = Product.builder()
                .id(1L)
                .stock(10)
                .build();

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        stockService.increaseStock(1L, 5);

        assertThat(product.getStock()).isEqualTo(15);

        verify(productRepository).save(product);
        verify(movementRepository).save(any());
    }
    
    @Test
    void shouldDecreaseStock() {
        Product product = Product.builder()
                .id(1L)
                .stock(10)
                .build();

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        stockService.decreaseStock(1L, 5);

        assertThat(product.getStock()).isEqualTo(5);

        verify(productRepository).save(product);
        verify(movementRepository).save(any());
    }
    
    @Test
    void shouldThrowExceptionWhenNotEnoughStock() {
        Product product = Product.builder()
                .id(1L)
                .stock(2)
                .build();

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        assertThrows(RuntimeException.class, () ->
                stockService.decreaseStock(1L, 5)
        );

        verify(movementRepository, never()).save(any());
    }
    
    @Test
    void shouldThrowWhenProductNotFound() {
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () ->
                stockService.increaseStock(1L, 5)
        );
    }
}