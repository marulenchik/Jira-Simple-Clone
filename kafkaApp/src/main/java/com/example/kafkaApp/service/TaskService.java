package com.example.kafkaApp.service;

import com.example.kafkaApp.dto.TaskDto;

import java.util.List;
import java.util.Optional;

public interface TaskService {
    String createTask(String title, String description, Long assigneeId);
    List<TaskDto> getAllTasks();
    Optional<TaskDto> getTaskById(Long id);
}
