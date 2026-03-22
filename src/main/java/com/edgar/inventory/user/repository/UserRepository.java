package com.edgar.inventory.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.edgar.inventory.user.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);
}