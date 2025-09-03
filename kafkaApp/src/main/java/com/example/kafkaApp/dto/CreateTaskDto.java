package com.example.kafkaApp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateTaskDto {
    private String taskName;
    private String taskDescription;
    private Long assigneeId;
}
