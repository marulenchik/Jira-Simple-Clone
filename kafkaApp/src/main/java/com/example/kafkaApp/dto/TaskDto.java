package com.example.kafkaApp.dto;

import com.example.kafkaApp.entity.Comment;
import com.example.kafkaApp.entity.TaskStatus;
import com.example.kafkaApp.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskDto {
    private Long id;
    private String name;
    private String description;
    private TaskStatus status;
    private Date createdDate;
    private LocalDateTime deadline;
    private List<Comment> comments = new ArrayList<>();
    private User assignee;
}
