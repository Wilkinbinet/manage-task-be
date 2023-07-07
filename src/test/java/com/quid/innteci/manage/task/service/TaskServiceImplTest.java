package com.quid.innteci.manage.task.service;

import com.quid.innteci.manage.task.dto.TaskDto;
import com.quid.innteci.manage.task.model.Task;
import com.quid.innteci.manage.task.model.User;
import com.quid.innteci.manage.task.repository.TaskRepository;
import com.quid.innteci.manage.task.repository.UserRepository;
import com.quid.innteci.manage.task.security.jwt.JwtTokenProvider;
import com.quid.innteci.manage.task.service.impl.TaskServiceImpl;
import enums.StatusTaskEnum;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;

import javax.servlet.http.HttpServletRequest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

/**
 * This class contains unit tests for the TaskServiceImpl class.
 */
@ExtendWith(MockitoExtension.class)
public class TaskServiceImplTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @InjectMocks
    private TaskServiceImpl taskService;

    /**
     * Unit test for the createTask method of TaskServiceImpl.
     * Tests the case where a new task is created with a valid request.
     */
    @Test
    public void createTaskShouldReturnNewTaskWhenGivenValidRequest() {
        HttpServletRequest request = new MockHttpServletRequest();
        TaskDto taskDto = new TaskDto();
        User user = new User();
        Task task = new Task();
        task.setStatus(StatusTaskEnum.PENDING);
        task.setUser(user);

        when(jwtTokenProvider.resolveToken(any(HttpServletRequest.class))).thenReturn("token");
        when(jwtTokenProvider.getIdUser(anyString())).thenReturn(1L);
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(taskRepository.save(any(Task.class))).thenReturn(task);

        Task result = taskService.createTask(request, taskDto);
        assertEquals(StatusTaskEnum.PENDING, result.getStatus());
        assertEquals(user, result.getUser());
    }

    /**
     * Unit test for the markTaskAsComplete method of TaskServiceImpl.
     * Tests the case where a task is marked as complete with a valid ID.
     */
    @Test
    public void markTaskAsCompleteShouldReturnUpdatedTaskWhenGivenValidId() {
        Task task = new Task();
        task.setStatus(StatusTaskEnum.PENDING);

        when(taskRepository.findById(anyLong())).thenReturn(Optional.of(task));
        when(taskRepository.save(any(Task.class))).thenReturn(task); // Add this line

        Task result = taskService.markTaskAsComplete(1L);
        assertEquals(StatusTaskEnum.COMPLETED, result.getStatus());
    }

    /**
     * Unit test for the deleteTask method of TaskServiceImpl.
     * Tests the case where a task is deleted with a valid ID.
     */
    @Test
    public void deleteTaskShouldNotThrowWhenGivenValidId() {
        doNothing().when(taskRepository).deleteById(anyLong());
        assertDoesNotThrow(() -> taskService.deleteTask(1L));
    }

    /**
     * Unit test for the getAllTasksUserLogged method of TaskServiceImpl.
     * Tests the case where a list of tasks is retrieved for a valid request.
     */
    @Test
    public void getAllTasksUserLoggedShouldReturnTaskListWhenGivenValidRequest() {
        HttpServletRequest request = new MockHttpServletRequest();
        List<Task> tasks = Arrays.asList(new Task());

        when(jwtTokenProvider.resolveToken(any(HttpServletRequest.class))).thenReturn("token");
        when(jwtTokenProvider.getIdUser(anyString())).thenReturn(1L);
        when(taskRepository.findByUserId(anyLong())).thenReturn(tasks);

        List<TaskDto> result = taskService.getAllTasksUserLogged(request);
        assertEquals(1, result.size());
    }
}
