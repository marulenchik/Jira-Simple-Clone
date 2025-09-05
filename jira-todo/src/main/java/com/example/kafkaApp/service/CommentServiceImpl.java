package com.example.kafkaApp.service;

import com.example.kafkaApp.entity.Comment;
import com.example.kafkaApp.entity.Task;
import com.example.kafkaApp.entity.User;
import com.example.kafkaApp.repository.CommentRepository;
import com.example.kafkaApp.repository.TaskRepository;
import com.example.kafkaApp.repository.UserRepository;
import org.example.core.CommentCreatedEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {

    private final UserRepository userRepository;
    private final TaskRepository taskRepository;
    private final CommentRepository commentRepository;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public CommentServiceImpl(UserRepository userRepository, TaskRepository taskRepository, CommentRepository commentRepository, KafkaTemplate<String, Object> kafkaTemplate) {
        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
        this.commentRepository = commentRepository;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public String createComment(String content, String author, Long taskId) {

        User user = userRepository.findByUsername(author)
                .orElseThrow(() -> new RuntimeException("User not found: " + author));

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found: " + taskId));

        Comment comment = new Comment();
        comment.setContent(content);
        comment.setUser(user);
        comment.setTask(task);

        Comment savedComment = commentRepository.save(comment);

        String commentId = savedComment.getId().toString();

        CommentCreatedEvent commentCreatedEvent = new CommentCreatedEvent(
                commentId,
                savedComment.getContent(),
                author,
                taskId
        );

        // 5. Отправляем в Kafka
        kafkaTemplate.send("comment-create-events-topic", commentId, commentCreatedEvent);

        return commentId;
    }

}
