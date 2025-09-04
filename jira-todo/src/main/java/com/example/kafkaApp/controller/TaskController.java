package com.example.kafkaApp.controller;

import com.example.kafkaApp.dto.CreateTaskDto;
import com.example.kafkaApp.dto.TaskDto;
import com.example.kafkaApp.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/task")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    public ResponseEntity<String> createTask(@RequestBody CreateTaskDto createTaskDto) {
        String taskId = taskService.createTask(createTaskDto.getTaskName(), createTaskDto.getTaskDescription(), createTaskDto.getAssigneeId());
        return ResponseEntity.status(HttpStatus.CREATED).body(taskId + " - Task created successfully" );
    }

    @GetMapping
    public ResponseEntity<List<TaskDto>> getAllTasks() {

        List<TaskDto> tasks = taskService.getAllTasks();
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskDto> getTaskById(@PathVariable Long id) {

        return taskService.getTaskById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
