package org.example.core;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskCreatedEvent {
    private String taskId;
    private String taskName;
    private String taskDescription;
}
