package com.portfolio.taskmanager.controller;

import com.portfolio.taskmanager.dto.task.TaskRequest;
import com.portfolio.taskmanager.dto.task.TaskResponse;
import com.portfolio.taskmanager.entity.TaskStatus;
import com.portfolio.taskmanager.entity.User;
import com.portfolio.taskmanager.service.TaskService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
@Tag(name = "Tarefas", description = "Endpoints de gerenciamento de tarefas do usuário autenticado")
public class TaskController {

    private final TaskService taskService;

    @PostMapping
    public ResponseEntity<TaskResponse> create(@Valid @RequestBody TaskRequest request,
                                                @AuthenticationPrincipal User user) {
        return ResponseEntity.status(HttpStatus.CREATED).body(taskService.createTask(request, user));
    }

    @GetMapping
    public ResponseEntity<Page<TaskResponse>> list(@RequestParam(required = false) TaskStatus status,
                                                    @AuthenticationPrincipal User user,
                                                    Pageable pageable) {
        return ResponseEntity.ok(taskService.listTasks(user, status, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskResponse> getById(@PathVariable Long id,
                                                 @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(taskService.getTask(id, user));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskResponse> update(@PathVariable Long id,
                                                @Valid @RequestBody TaskRequest request,
                                                @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(taskService.updateTask(id, request, user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id,
                                        @AuthenticationPrincipal User user) {
        taskService.deleteTask(id, user);
        return ResponseEntity.noContent().build();
    }
}
