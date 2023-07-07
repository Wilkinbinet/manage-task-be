package com.quid.innteci.manage.task.controller;

import com.quid.innteci.manage.task.dto.TaskDto;
import com.quid.innteci.manage.task.model.Task;
import com.quid.innteci.manage.task.service.impl.TaskServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

/**
 * This class contains unit tests for the TaskController class.
 */
@ExtendWith(MockitoExtension.class)
public class TaskControllerTest {

    @InjectMocks
    private TaskController taskController;

    @Mock
    private TaskServiceImpl taskServiceImpl;

    private TaskDto taskDto;
    private Task task;

    /**
     * Sets up the test environment before each test case.
     */
    @BeforeEach
    void setUp() {
        taskDto = new TaskDto();
        taskDto.setTitle("Test task");

        task = new Task();
        task.setTitle("Test task");
    }

    /**
     * Unit test for the getAllTasks method.
     */
    @Test
    void getAllTasks() {

        TaskDto taskDto = new TaskDto();
        taskDto.setTitle("Test task");

        List<TaskDto> taskDtoList = Arrays.asList(taskDto);

        when(taskServiceImpl.getAllTasksUserLogged(any())).thenReturn(taskDtoList);

        ResponseEntity<List<TaskDto>> responseEntity = taskController.getAllTasks(new MockHttpServletRequest());

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(1, responseEntity.getBody().size());
    }

    /**
     * Unit test for the createTask method.
     */
    @Test
    void createTask() {
        when(taskServiceImpl.createTask(any(), any(TaskDto.class))).thenReturn(task);

        HttpServletRequest request = new MockHttpServletRequest();
        ResponseEntity<TaskDto> responseEntity = taskController.createTask(request, taskDto);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(task.getTitle(), responseEntity.getBody().getTitle());
    }

    /**
     * Unit test for the markTaskAsComplete method.
     */
    @Test
    void markTaskAsComplete() {
        when(taskServiceImpl.markTaskAsComplete(anyLong())).thenReturn(task);

        ResponseEntity<TaskDto> responseEntity = taskController.markTaskAsComplete(1L);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(task.getTitle(), responseEntity.getBody().getTitle());
    }

    /**
     * Unit test for the deleteTask method.
     */
    @Test
    void deleteTask() {
        doNothing().when(taskServiceImpl).deleteTask(anyLong());

        ResponseEntity<Void> responseEntity = taskController.deleteTask(1L);

        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
    }
}