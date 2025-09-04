package com.example.kafkaApp.repository;

import com.example.kafkaApp.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
}

