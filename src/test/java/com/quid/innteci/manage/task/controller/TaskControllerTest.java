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

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TaskControllerTest {

    @InjectMocks
    private TaskController taskController;

    @Mock
    private TaskServiceImpl taskServiceImpl;

    private TaskDto taskDto;
    private Task task;

    @BeforeEach
    void setUp() {
        taskDto = new TaskDto();
        taskDto.setTitle("Test task");

        task = new Task();
        task.setTitle("Test task");
    }

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

    @Test
    void createTask() {
        when(taskServiceImpl.createTask(any(), any(TaskDto.class))).thenReturn(task);
        ResponseEntity<TaskDto> responseEntity = taskController.createTask(new MockHttpServletRequest(), taskDto);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(task.getTitle(), responseEntity.getBody().getTitle());
    }

    @Test
    void markTaskAsComplete() {
        when(taskServiceImpl.markTaskAsComplete(anyLong())).thenReturn(task);
        ResponseEntity<TaskDto> responseEntity = taskController.markTaskAsComplete(1L);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(task.getTitle(), responseEntity.getBody().getTitle());
    }

    @Test
    void deleteTask() {
        doNothing().when(taskServiceImpl).deleteTask(anyLong());
        ResponseEntity<Void> responseEntity = taskController.deleteTask(1L);

        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
    }
}