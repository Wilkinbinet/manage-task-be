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

    /**
     * Constructs an instance of the TaskController.
     *
     * @param taskServiceImpl the TaskServiceImpl instance
     */
    @Autowired
    public TaskController(TaskServiceImpl taskServiceImpl) {
        this.taskServiceImpl = taskServiceImpl;
    }

    /**
     * Retrieves all tasks for the logged-in user and returns them as a ResponseEntity.
     *
     * @param request the HttpServletRequest object
     * @return the ResponseEntity with the list of TaskDto objects
     */
    @GetMapping
    public ResponseEntity<List<TaskDto>> getAllTasks(HttpServletRequest request) {
        return ResponseEntity.ok(taskServiceImpl.getAllTasksUserLogged(request));
    }

    /**
     * Creates a new task based on the provided TaskDto and returns the created task as a ResponseEntity.
     *
     * @param request the HttpServletRequest object
     * @param taskDto the TaskDto object representing the new task
     * @return the ResponseEntity with the created TaskDto object
     */
    @PostMapping
    public ResponseEntity<TaskDto> createTask(HttpServletRequest request, @Valid @RequestBody TaskDto taskDto) {
        Task savedTask = taskServiceImpl.createTask(request, taskDto);
        TaskDto responseDto = new TaskDto();
        BeanUtils.copyProperties(savedTask, responseDto);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    /**
     * Marks a task as complete based on the provided taskId and returns the updated task as a ResponseEntity.
     *
     * @param taskId the ID of the task to mark as complete
     * @return the ResponseEntity with the updated TaskDto object
     */
    @PutMapping("/{taskId}/complete")
    public ResponseEntity<TaskDto> markTaskAsComplete(@PathVariable Long taskId) {
        Task completedTask = taskServiceImpl.markTaskAsComplete(taskId);
        TaskDto responseDto = new TaskDto();
        BeanUtils.copyProperties(completedTask, responseDto);
        return ResponseEntity.ok(responseDto);
    }

    /**
     * Deletes a task based on the provided taskId and returns a ResponseEntity with no content.
     *
     * @param taskId the ID of the task to delete
     * @return the ResponseEntity with no content
     */
    @DeleteMapping("/{taskId}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long taskId) {
        taskServiceImpl.deleteTask(taskId);
        return ResponseEntity.noContent().build();
    }
}
