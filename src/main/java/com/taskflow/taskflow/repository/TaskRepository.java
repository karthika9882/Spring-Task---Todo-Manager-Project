
package com.taskflow.taskflow.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.taskflow.taskflow.model.Task;
import com.taskflow.taskflow.model.TaskPriority;
import com.taskflow.taskflow.model.TaskStatus;

public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByStatus(TaskStatus status);

    List<Task> findByPriority(TaskPriority priority);

    List<Task> findByStatusAndPriority(
            TaskStatus status,
            TaskPriority priority
    );
}