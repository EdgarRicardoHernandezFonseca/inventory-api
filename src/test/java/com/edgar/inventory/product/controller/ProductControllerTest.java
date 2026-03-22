package com.edgar.inventory.product.controller;

import com.edgar.inventory.product.dto.ProductRequest;
import com.edgar.inventory.product.dto.ProductResponse;
import com.edgar.inventory.product.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser(roles = {"ADMIN"})
    void shouldCreateProduct() throws Exception {

        ProductRequest request = new ProductRequest("Laptop",
                BigDecimal.valueOf(2000), 10, 5);

        ProductResponse response = ProductResponse.builder()
                .id(1L)
                .name("Laptop")
                .build();

        when(productService.create(any())).thenReturn(response);

        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.name").value("Laptop"));
    }

    @Test
    @WithMockUser
    void shouldGetAllProducts() throws Exception {

        ProductResponse product = ProductResponse.builder()
                .name("Laptop")
                .build();

        when(productService.getAll(anyInt(), anyInt()))
                .thenReturn(new PageImpl<>(List.of(product)));

        mockMvc.perform(get("/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.content[0].name").value("Laptop"));
    }

    @Test
    @WithMockUser
    void shouldGetLowStockProducts() throws Exception {

        ProductResponse product = ProductResponse.builder()
                .name("Laptop")
                .build();

        when(productService.getLowStockProducts())
                .thenReturn(List.of(product));

        mockMvc.perform(get("/products/low-stock"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Laptop"));
    }

    @Test
    @WithMockUser
    void shouldSearchProducts() throws Exception {

        ProductResponse product = ProductResponse.builder()
                .name("Laptop")
                .build();

        when(productService.search(any(), any(), any(), any(), anyInt(), anyInt()))
                .thenReturn(new PageImpl<>(List.of(product)));

        mockMvc.perform(get("/products/search")
                        .param("name", "lap"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.content[0].name").value("Laptop"));
    }
}