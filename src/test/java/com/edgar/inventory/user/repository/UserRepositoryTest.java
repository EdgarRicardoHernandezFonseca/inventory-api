package com.edgar.inventory.user.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.edgar.inventory.user.entity.User;
import com.edgar.inventory.enums.Role;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void shouldFindByUsername() {
        User user = User.builder()
                .username("edgar")
                .password("1234")
                .role(Role.USER)
                .build();

        userRepository.save(user);

        var result = userRepository.findByUsername("edgar");

        assertThat(result).isPresent();
    }
}