package org.example.core;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentCreatedEvent {
    private String commentId;
    private String content;
    private String author;
    private Long taskId;
}
