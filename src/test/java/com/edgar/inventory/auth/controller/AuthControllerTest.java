package com.edgar.inventory.auth.controller;

import com.edgar.inventory.security.jwt.JwtService;
import com.edgar.inventory.user.entity.User;
import com.edgar.inventory.user.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.Mockito.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuthController.class)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldRegisterUser() throws Exception {

        User user = new User();
        user.setUsername("edgar");
        user.setPassword("1234");

        when(passwordEncoder.encode(any())).thenReturn("encoded");
        when(userRepository.save(any())).thenReturn(user);

        mockMvc.perform(post("/auth/register")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk())
                .andExpect(content().string("User registered"));
    }

    @Test
    void shouldLoginUser() throws Exception {

        User user = new User();
        user.setUsername("edgar");
        user.setPassword("encoded");

        when(userRepository.findByUsername("edgar"))
                .thenReturn(Optional.of(user));

        when(passwordEncoder.matches(any(), any())).thenReturn(true);
        when(jwtService.generateToken(any())).thenReturn("fake-jwt");

        mockMvc.perform(post("/auth/login")
                        .contentType("application/json")
                        .content("""
                                {
                                  "username": "edgar",
                                  "password": "1234"
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(content().string("fake-jwt"));
    }
}