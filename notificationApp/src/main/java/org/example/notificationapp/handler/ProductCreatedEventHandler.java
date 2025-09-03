package org.example.notificationapp.handler;

import org.example.core.TaskCreatedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@KafkaListener(topics="task-create-events-topic")
public class ProductCreatedEventHandler {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @KafkaHandler
    public void handle(TaskCreatedEvent taskCreatedEvent ){
        LOGGER.info("Received task created event: " + taskCreatedEvent.getTaskName());
    }
}
