package com.example.kafkaApp.service;

import com.example.kafkaApp.dto.TaskDto;
import com.example.kafkaApp.entity.Task;
import com.example.kafkaApp.entity.User;
import com.example.kafkaApp.repository.TaskRepository;
import com.example.kafkaApp.repository.UserRepository;
import org.example.core.TaskCreatedEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static com.example.kafkaApp.entity.TaskStatus.TODO;

@Service
public class TaskServiceImpl implements TaskService {

    private final UserRepository userRepository;
    private final TaskRepository taskRepository;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public TaskServiceImpl(UserRepository userRepository, TaskRepository taskRepository, KafkaTemplate<String, Object> kafkaTemplate) {
        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public String createTask(String title, String description, Long assigneeId) {
    
        if (assigneeId == null) {
            throw new IllegalArgumentException("Assignee ID cannot be null");
        }
        
        User assignee = userRepository.findById(assigneeId)
                .orElseThrow(() -> new RuntimeException("Assignee with ID " + assigneeId + " not found"));

        // 1. Создаём сущность Task
        Task task = new Task();
        task.setName(title);
        task.setDescription(description);
        task.setStatus(TODO);
        task.setCreatedDate(new Date());
        task.setAssignee(assignee);

        // 2. Сохраняем задачу в БД
        Task savedTask = taskRepository.save(task);

        // 3. Получаем id, который сгенерировала база
        String taskId = savedTask.getId().toString();

        // 4. Формируем событие
        TaskCreatedEvent taskCreatedEvent = new TaskCreatedEvent(
                taskId,
                savedTask.getName(),
                savedTask. getDescription()
        );

        // 5. Отправляем в Kafka
        kafkaTemplate.send("task-create-events-topic", taskId, taskCreatedEvent);

        return taskId;
    }

    public List<TaskDto> getAllTasks(){
        List<Task> tasks = taskRepository.findAll();
        List<TaskDto> taskDtos = new ArrayList<>();

        for (Task task : tasks) {
            taskDtos.add(convertTaskToTaskDto(task));
        }

        return taskDtos;
    }

    public Optional<TaskDto> getTaskById(Long id) {
        Optional<Task> taskOptional = taskRepository.findById(id);

        if (taskOptional.isPresent()) {
            Task task = taskOptional.get();
            TaskDto dto = convertTaskToTaskDto(task);
            return Optional.of(dto);
        } else {
            return Optional.empty();
        }
    }


    public TaskDto convertTaskToTaskDto(Task task) {
        TaskDto taskDto = new TaskDto();
        taskDto.setId(task.getId());
        taskDto.setName(task.getName());
        taskDto.setDescription(task.getDescription());
        taskDto.setStatus(task.getStatus());
        taskDto.setCreatedDate(task.getCreatedDate());
        taskDto.setDeadline(task.getDeadline());
        taskDto.setComments(task.getComments());
        taskDto.setAssignee(task.getAssignee());
        return taskDto;
    }
}