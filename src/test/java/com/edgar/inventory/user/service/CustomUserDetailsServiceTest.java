package com.edgar.inventory.user.service;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;

import com.edgar.inventory.enums.Role;
import com.edgar.inventory.user.entity.User;
import com.edgar.inventory.user.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
class CustomUserDetailsServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CustomUserDetailsService service;
    
    @Test
    void shouldLoadUserByUsername() {
        User user = User.builder()
                .username("edgar")
                .password("1234")
                .role(Role.USER)
                .build();

        when(userRepository.findByUsername("edgar"))
                .thenReturn(Optional.of(user));

        UserDetails result = service.loadUserByUsername("edgar");

        assertThat(result.getUsername()).isEqualTo("edgar");
    }
    
    @Test
    void shouldThrowWhenUserNotFound() {
        when(userRepository.findByUsername("edgar"))
                .thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () ->
                service.loadUserByUsername("edgar")
        );
    }
}
