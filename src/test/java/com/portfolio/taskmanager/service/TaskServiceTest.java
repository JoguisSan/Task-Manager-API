package com.portfolio.taskmanager.service;

import com.portfolio.taskmanager.dto.task.TaskRequest;
import com.portfolio.taskmanager.dto.task.TaskResponse;
import com.portfolio.taskmanager.entity.Task;
import com.portfolio.taskmanager.entity.TaskStatus;
import com.portfolio.taskmanager.entity.User;
import com.portfolio.taskmanager.exception.ResourceNotFoundException;
import com.portfolio.taskmanager.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService;

    private User owner;

    @BeforeEach
    void setUp() {
        owner = User.builder().id(1L).username("joao").email("joao@email.com").password("hash").build();
    }

    @Test
    void deveCriarTarefaComSucesso() {
        TaskRequest request = new TaskRequest("Estudar Spring", "Revisar JWT", TaskStatus.PENDING, LocalDate.now().plusDays(3));
        Task savedTask = Task.builder()
                .id(1L)
                .title(request.title())
                .description(request.description())
                .status(TaskStatus.PENDING)
                .dueDate(request.dueDate())
                .owner(owner)
                .build();

        when(taskRepository.save(any(Task.class))).thenReturn(savedTask);

        TaskResponse response = taskService.createTask(request, owner);

        assertThat(response.title()).isEqualTo("Estudar Spring");
        assertThat(response.status()).isEqualTo(TaskStatus.PENDING);
        verify(taskRepository).save(any(Task.class));
    }

    @Test
    void deveLancarExcecaoQuandoTarefaNaoEncontrada() {
        when(taskRepository.findByIdAndOwner(99L, owner)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> taskService.getTask(99L, owner))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("99");
    }

    @Test
    void deveAtualizarTarefaExistente() {
        Task existing = Task.builder().id(1L).title("Antigo").status(TaskStatus.PENDING).owner(owner).build();
        TaskRequest request = new TaskRequest("Novo título", "Nova descrição", TaskStatus.IN_PROGRESS, null);

        when(taskRepository.findByIdAndOwner(1L, owner)).thenReturn(Optional.of(existing));
        when(taskRepository.save(any(Task.class))).thenReturn(existing);

        TaskResponse response = taskService.updateTask(1L, request, owner);

        assertThat(response.title()).isEqualTo("Novo título");
        assertThat(response.status()).isEqualTo(TaskStatus.IN_PROGRESS);
    }

    @Test
    void deveDeletarTarefaExistente() {
        Task existing = Task.builder().id(1L).title("Tarefa").owner(owner).build();
        when(taskRepository.findByIdAndOwner(1L, owner)).thenReturn(Optional.of(existing));

        taskService.deleteTask(1L, owner);

        verify(taskRepository).delete(existing);
    }
}
