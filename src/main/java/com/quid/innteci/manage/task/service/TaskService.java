package com.quid.innteci.manage.task.service;

import com.quid.innteci.manage.task.dto.TaskDto;
import com.quid.innteci.manage.task.model.Task;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface TaskService {
    Task createTask(HttpServletRequest request, TaskDto taskDto);
    Task markTaskAsComplete(Long taskId);
    void deleteTask(Long taskId);
    List<TaskDto> getAllTasksUserLogged(HttpServletRequest request);
}
