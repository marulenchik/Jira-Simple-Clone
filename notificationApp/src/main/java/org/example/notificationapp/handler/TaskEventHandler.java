package org.example.notificationapp.handler;

import org.example.core.CommentCreatedEvent;
import org.example.core.TaskCreatedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class TaskEventHandler {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @KafkaListener(
            topics = "task-create-events-topic",
            containerFactory = "taskKafkaListenerContainerFactory")
    public void handleTaskCreatedEvent(TaskCreatedEvent taskCreatedEvent) {
        LOGGER.info("Received task created event for task: {} with ID: {}",
                taskCreatedEvent.getTaskName(), taskCreatedEvent.getTaskId());
        sendTaskCreatedNotification(taskCreatedEvent);
    }

    @KafkaListener(
            topics = "comment-create-events-topic",
            containerFactory = "commentKafkaListenerContainerFactory")
    public void handleCommentCreatedEvent(CommentCreatedEvent commentCreatedEvent) {
        LOGGER.info("Received comment created event for task ID: {} by author: {} with content: {}",
                commentCreatedEvent.getTaskId(), commentCreatedEvent.getAuthor(), commentCreatedEvent.getContent());
        sendCommentCreatedNotification(commentCreatedEvent);
    }

    private void sendTaskCreatedNotification(TaskCreatedEvent event) {
        LOGGER.info("Sending notification: New task '{}' has been created", event.getTaskName());
    }

    private void sendCommentCreatedNotification(CommentCreatedEvent event) {
        LOGGER.info("Sending notification: New comment added to task {} by {}", event.getTaskId(), event.getAuthor());
    }
}