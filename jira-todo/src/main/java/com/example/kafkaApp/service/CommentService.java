package com.example.kafkaApp.service;

import com.example.kafkaApp.entity.Comment;

public interface CommentService {
    String createComment(String content, String author, Long taskId);
}
