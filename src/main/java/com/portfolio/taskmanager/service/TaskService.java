package com.portfolio.taskmanager.service;

import com.portfolio.taskmanager.dto.task.TaskRequest;
import com.portfolio.taskmanager.dto.task.TaskResponse;
import com.portfolio.taskmanager.entity.Task;
import com.portfolio.taskmanager.entity.TaskStatus;
import com.portfolio.taskmanager.entity.User;
import com.portfolio.taskmanager.exception.ResourceNotFoundException;
import com.portfolio.taskmanager.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskResponse createTask(TaskRequest request, User owner) {
        Task task = Task.builder()
                .title(request.title())
                .description(request.description())
                .status(request.status() != null ? request.status() : TaskStatus.PENDING)
                .dueDate(request.dueDate())
                .owner(owner)
                .build();

        Task saved = taskRepository.save(task);
        return TaskResponse.fromEntity(saved);
    }

    public Page<TaskResponse> listTasks(User owner, TaskStatus status, Pageable pageable) {
        Page<Task> tasks = (status != null)
                ? taskRepository.findByOwnerAndStatus(owner, status, pageable)
                : taskRepository.findByOwner(owner, pageable);

        return tasks.map(TaskResponse::fromEntity);
    }

    public TaskResponse getTask(Long id, User owner) {
        Task task = findOwnedTaskOrThrow(id, owner);
        return TaskResponse.fromEntity(task);
    }

    public TaskResponse updateTask(Long id, TaskRequest request, User owner) {
        Task task = findOwnedTaskOrThrow(id, owner);

        task.setTitle(request.title());
        task.setDescription(request.description());
        if (request.status() != null) {
            task.setStatus(request.status());
        }
        task.setDueDate(request.dueDate());

        Task updated = taskRepository.save(task);
        return TaskResponse.fromEntity(updated);
    }

    public void deleteTask(Long id, User owner) {
        Task task = findOwnedTaskOrThrow(id, owner);
        taskRepository.delete(task);
    }

    private Task findOwnedTaskOrThrow(Long id, User owner) {
        return taskRepository.findByIdAndOwner(id, owner)
                .orElseThrow(() -> new ResourceNotFoundException("Tarefa não encontrada com id: " + id));
    }
}
