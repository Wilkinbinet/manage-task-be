package com.quid.innteci.manage.task.service.impl;


import com.quid.innteci.manage.task.service.TaskService;
import enums.StatusTaskEnum;
import com.quid.innteci.manage.task.dto.TaskDto;
import com.quid.innteci.manage.task.model.Task;
import com.quid.innteci.manage.task.model.User;
import com.quid.innteci.manage.task.repository.TaskRepository;
import com.quid.innteci.manage.task.repository.UserRepository;
import com.quid.innteci.manage.task.security.jwt.JwtTokenProvider;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * This class implements the TaskService interface and provides task-related operations.
 */
@Service
@Transactional
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    /**
     * Constructs an instance of TaskServiceImpl.
     *
     * @param taskRepository    the TaskRepository instance for task operations
     * @param userRepository   the UserRepository instance for user operations
     * @param jwtTokenProvider the JwtTokenProvider instance for JWT token management
     */
    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository, UserRepository userRepository, JwtTokenProvider jwtTokenProvider) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    /**
     * Creates a new task for the logged-in user.
     *
     * @param request  the HttpServletRequest object containing the request information
     * @param taskDto  the TaskDto object containing the task details
     * @return the created task
     * @throws IllegalArgumentException if the user is not found
     */
    @Override
    public Task createTask(HttpServletRequest request, TaskDto taskDto) {
        String token = jwtTokenProvider.resolveToken(request);

        Task task = new Task();
        BeanUtils.copyProperties(taskDto, task);
        Optional<User> optionalUser = userRepository.findById(jwtTokenProvider.getIdUser(token));
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            task.setUser(user);
            task.setStatus(StatusTaskEnum.PENDING);
            return taskRepository.save(task);
        }
        throw new IllegalArgumentException("User not found");
    }

    /**
     * Marks a task as complete.
     *
     * @param taskId  the ID of the task to mark as complete
     * @return the updated task
     * @throws IllegalArgumentException if the task is not found
     */
    @Override
    public Task markTaskAsComplete(Long taskId) {
        Optional<Task> optionalTask = taskRepository.findById(taskId);
        if (optionalTask.isPresent()) {
            Task task = optionalTask.get();
            task.setStatus(StatusTaskEnum.COMPLETED);
            return taskRepository.save(task);
        }
        throw new IllegalArgumentException("Task not found with ID: " + taskId);
    }

    /**
     * Deletes a task.
     *
     * @param taskId  the ID of the task to delete
     */
    @Override
    public void deleteTask(Long taskId) {
        taskRepository.deleteById(taskId);
    }

    /**
     * Retrieves all tasks for the logged-in user.
     *
     * @param request  the HttpServletRequest object containing the request information
     * @return a list of TaskDto objects representing the tasks
     */
    @Override
    public List<TaskDto> getAllTasksUserLogged(HttpServletRequest request) {
        String token = jwtTokenProvider.resolveToken(request);
        List<Task> tasks = taskRepository.findByUserId(jwtTokenProvider.getIdUser(token));
        List<TaskDto> taskDtos = tasks.stream().map(task -> {
            TaskDto dto = new TaskDto();
            BeanUtils.copyProperties(task, dto);
            return dto;
        }).collect(Collectors.toList());
        return taskDtos;
    }
}