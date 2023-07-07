package com.quid.innteci.manage.task.repository;

import com.quid.innteci.manage.task.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * This interface serves as the repository for the Task entity.
 */
@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    /**
     * Retrieves a list of tasks based on the user ID.
     *
     * @param userId the ID of the user
     * @return a list of tasks associated with the user
     */
    List<Task> findByUserId(Long userId);

    /**
     * Retrieves a list of tasks based on the user ID and status.
     *
     * @param userId the ID of the user
     * @param status the status of the tasks
     * @return a list of tasks associated with the user and matching the specified status
     */
    List<Task> findByUserIdAndStatus(Long userId, String status);

    /**
     * Deletes a task based on the task ID and user ID.
     *
     * @param id     the ID of the task to delete
     * @param userId the ID of the user who owns the task
     */
    void deleteByIdAndUserId(Long id, Long userId);

}