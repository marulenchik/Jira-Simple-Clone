package org.example.notificationapp.config;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.example.core.CommentCreatedEvent;
import org.example.core.TaskCreatedEvent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
public class KafkaConsumerConfig {

    @Value("${spring.kafka.consumer.bootstrap-servers}")
    private String bootstrapServers;

    @Value("${spring.kafka.consumer.group-id}")
    private String groupId;

    @Value("${spring.kafka.consumer.auto-offset-reset}")
    private String autoOffsetReset;

    // Common configuration for both consumer factories
    private Map<String, Object> commonConfig() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        configProps.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        configProps.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, autoOffsetReset);
        configProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        configProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        configProps.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
        configProps.put(JsonDeserializer.USE_TYPE_INFO_HEADERS, false);
        return configProps;
    }

    // Consumer factory for TaskCreatedEvent
    @Bean
    public ConsumerFactory<String, TaskCreatedEvent> taskEventConsumerFactory() {
        return new DefaultKafkaConsumerFactory<>(
                commonConfig(),
                new StringDeserializer(),
                new JsonDeserializer<>(TaskCreatedEvent.class)
        );
    }

    // Listener container factory for TaskCreatedEvent
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, TaskCreatedEvent> taskKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, TaskCreatedEvent> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(taskEventConsumerFactory());
        factory.setConcurrency(1);
        return factory;
    }

    // Consumer factory for CommentCreatedEvent
    @Bean
    public ConsumerFactory<String, CommentCreatedEvent> commentEventConsumerFactory() {
        return new DefaultKafkaConsumerFactory<>(
                commonConfig(),
                new StringDeserializer(),
                new JsonDeserializer<>(CommentCreatedEvent.class)
        );
    }

    // Listener container factory for CommentCreatedEvent
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, CommentCreatedEvent> commentKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, CommentCreatedEvent> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(commentEventConsumerFactory());
        factory.setConcurrency(1);
        return factory;
    }
}