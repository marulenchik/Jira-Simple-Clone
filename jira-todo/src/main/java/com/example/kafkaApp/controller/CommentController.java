package com.example.kafkaApp.controller;

import com.example.kafkaApp.dto.CreateCommentDto;
import com.example.kafkaApp.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/comment")
public class CommentController {
    private CommentService commentService;

    public CommentController(CommentService commentService){
        this.commentService = commentService;
    }

    @PostMapping
    public ResponseEntity<String> createComment(@RequestBody CreateCommentDto createCommentDto) {
        String CommentId = commentService.createComment(createCommentDto.getContent(), createCommentDto.getAuthor(), createCommentDto.getTaskId());
        return ResponseEntity.status(HttpStatus.CREATED).body(CommentId + " - Task created successfully" );
    }
}
