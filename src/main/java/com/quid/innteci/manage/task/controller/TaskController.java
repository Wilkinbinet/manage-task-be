package com.quid.innteci.manage.task.controller;


import com.quid.innteci.manage.task.dto.TaskDto;
import com.quid.innteci.manage.task.model.Task;
import com.quid.innteci.manage.task.service.impl.TaskServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskServiceImpl taskServiceImpl;

    @Autowired
    public TaskController(TaskServiceImpl taskServiceImpl) {
        this.taskServiceImpl = taskServiceImpl;
    }

    @GetMapping
    public ResponseEntity<List<TaskDto>> getAllTasks(HttpServletRequest request) {
        return ResponseEntity.ok(taskServiceImpl.getAllTasksUserLogged(request));
    }

    @PostMapping
    public ResponseEntity<TaskDto> createTask(HttpServletRequest request,@Valid @RequestBody TaskDto taskDto) {
        Task savedTask = taskServiceImpl.createTask(request, taskDto);
        TaskDto responseDto = new TaskDto();
        BeanUtils.copyProperties(savedTask, responseDto);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @PutMapping("/{taskId}/complete")
    public ResponseEntity<TaskDto> markTaskAsComplete(@PathVariable Long taskId) {
        Task completedTask = taskServiceImpl.markTaskAsComplete(taskId);
        TaskDto responseDto = new TaskDto();
        BeanUtils.copyProperties(completedTask, responseDto);
        return ResponseEntity.ok(responseDto);
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long taskId) {
        taskServiceImpl.deleteTask(taskId);
        return ResponseEntity.noContent().build();
    }
}