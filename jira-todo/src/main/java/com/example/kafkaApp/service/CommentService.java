package com.example.kafkaApp.service;

public interface CommentService {
    String createComment(String content, String author, Long taskId);
}
