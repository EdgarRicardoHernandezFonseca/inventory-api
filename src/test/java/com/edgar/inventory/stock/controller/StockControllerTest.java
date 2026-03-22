package com.edgar.inventory.stock.controller;

import com.edgar.inventory.product.controller.ProductController;
import com.edgar.inventory.security.jwt.JwtAuthenticationFilter;
import com.edgar.inventory.stock.service.StockService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(
	    controllers = StockController.class,
	    excludeAutoConfiguration = {
	        org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class,
	        org.springframework.boot.autoconfigure.security.servlet.SecurityFilterAutoConfiguration.class
	    }
	)
@AutoConfigureMockMvc(addFilters = false)
class StockControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StockService stockService;
    
    @MockBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Test
    @WithMockUser(roles = {"ADMIN"})
    void shouldIncreaseStock() throws Exception {

        mockMvc.perform(post("/stock/increase/1")
                        .param("quantity", "5"))
                .andExpect(status().isOk())
                .andExpect(content().string("Stock increased"));

        verify(stockService).increaseStock(1L, 5);
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    void shouldDecreaseStock() throws Exception {

        mockMvc.perform(post("/stock/decrease/1")
                        .param("quantity", "3"))
                .andExpect(status().isOk())
                .andExpect(content().string("Stock decreased"));

        verify(stockService).decreaseStock(1L, 3);
    }
}