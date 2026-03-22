package com.edgar.inventory.product.service;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.edgar.inventory.product.dto.ProductRequest;
import com.edgar.inventory.product.dto.ProductResponse;
import com.edgar.inventory.product.entity.Product;
import com.edgar.inventory.product.repository.ProductRepository;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;
    
    @Test
    void shouldCreateProduct() {
        ProductRequest request = new ProductRequest("Laptop",
                BigDecimal.valueOf(2000), 10, 5);

        Product saved = Product.builder()
                .id(1L)
                .name("Laptop")
                .price(BigDecimal.valueOf(2000))
                .stock(10)
                .minStock(5)
                .build();

        when(productRepository.save(any(Product.class))).thenReturn(saved);

        ProductResponse response = productService.create(request);

        assertThat(response.getName()).isEqualTo("Laptop");
        verify(productRepository).save(any(Product.class));
    }
    
    @Test
    void shouldReturnPagedProducts() {
        Product product = Product.builder()
                .name("Laptop")
                .price(BigDecimal.valueOf(2000))
                .stock(10)
                .minStock(5)
                .build();

        Page<Product> page = new PageImpl<>(List.of(product));

        when(productRepository.findAll(any(Pageable.class))).thenReturn(page);

        Page<ProductResponse> result = productService.getAll(0, 10);

        assertThat(result.getContent()).isNotEmpty();
    }
    
    @Test
    void shouldSearchByName() {
        Product product = Product.builder().name("Laptop").build();

        Page<Product> page = new PageImpl<>(List.of(product));

        when(productRepository.findByNameContainingIgnoreCase(eq("lap"), any()))
                .thenReturn(page);

        Page<ProductResponse> result =
                productService.search("lap", null, null, null, 0, 10);

        assertThat(result.getContent()).isNotEmpty();
    }
    
    @Test
    void shouldReturnLowStockProducts() {
        Product product = Product.builder().name("Laptop").stock(3).build();

        when(productRepository.findByStockLessThanEqual(5))
                .thenReturn(List.of(product));

        List<ProductResponse> result = productService.getLowStockProducts();

        assertThat(result).isNotEmpty();
    }
}
