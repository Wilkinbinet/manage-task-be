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

@Service
@Transactional
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository, UserRepository userRepository, JwtTokenProvider jwtTokenProvider) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public Task createTask(HttpServletRequest request, TaskDto taskDto) {

        // Obtener el token de sesión del encabezado de la solicitud
        String token = jwtTokenProvider.resolveToken(request);

        Task task = new Task();
        BeanUtils.copyProperties(taskDto, task);
        Optional<User> optionalUser = userRepository.findById(
                                                    jwtTokenProvider.getIdUser(token));
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            task.setUser(user);
            task.setStatus(StatusTaskEnum.PENDING);
            return taskRepository.save(task);
        }
        throw new IllegalArgumentException("User not found");
    }

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

    @Override
    public void deleteTask(Long taskId) {
        taskRepository.deleteById(taskId);
    }

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