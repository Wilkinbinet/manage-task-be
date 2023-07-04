package com.quid.innteci.manage.task.repository;

import com.quid.innteci.manage.task.model.Task;
import com.quid.innteci.manage.task.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class TaskRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private TaskRepository taskRepository;

    @Test
    void shouldFindByUserId() {
        User user = new User();
        entityManager.persist(user);

        Task task = new Task();
        task.setUser(user);
        entityManager.persist(task);

        List<Task> tasks = taskRepository.findByUserId(user.getId());

        assertThat(tasks).hasSize(1).contains(task);
    }

    @Test
    void shouldDeleteByIdAndUserId() {
        User user = new User();
        entityManager.persist(user);

        Task task = new Task();
        task.setUser(user);
        entityManager.persist(task);

        taskRepository.deleteByIdAndUserId(task.getId(), user.getId());

        Optional<Task> deletedTask = taskRepository.findById(task.getId());

        assertThat(deletedTask).isEmpty();
    }

}
