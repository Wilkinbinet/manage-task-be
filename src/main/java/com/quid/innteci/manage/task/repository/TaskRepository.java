package com.quid.innteci.manage.task.repository;

import com.quid.innteci.manage.task.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByUserId(Long userId);

    List<Task> findByUserIdAndStatus(Long userId, String status);

    void deleteByIdAndUserId(Long id, Long userId);

    List<Task> findByUser_Id(Long userId);

}