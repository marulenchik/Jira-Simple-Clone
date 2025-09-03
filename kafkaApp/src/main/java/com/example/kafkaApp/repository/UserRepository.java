package com.example.kafkaApp.repository;

import com.example.kafkaApp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
