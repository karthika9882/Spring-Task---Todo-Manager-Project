
package com.taskflow.taskflow.service;

import com.taskflow.taskflow.model.Task;
import com.taskflow.taskflow.model.TaskPriority;
import com.taskflow.taskflow.model.TaskStatus;
import com.taskflow.taskflow.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Task createTask(Task task) {
        task.setId(null);
        if (task.getStatus() == null) {
            task.setStatus(TaskStatus.PENDING);
        }
        if (task.getPriority() == null) {
            task.setPriority(TaskPriority.MEDIUM);
        }
        return taskRepository.save(task);
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public Task getTaskById(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() ->
                        new TaskNotFoundException(id));
    }

    public Task updateTask(Long id, Task updatedTask) {
        Task existingTask = getTaskById(id);

        existingTask.setTitle(updatedTask.getTitle());
        existingTask.setDescription(updatedTask.getDescription());
        existingTask.setPriority(updatedTask.getPriority());

        if (updatedTask.getStatus() != null) {
            existingTask.setStatus(updatedTask.getStatus());
        }

        return taskRepository.save(existingTask);
    }

    public Task toggleTaskStatus(Long id) {
        Task task = getTaskById(id);

        if (task.getStatus() == TaskStatus.COMPLETED) {
            task.setStatus(TaskStatus.PENDING);
        } else {
            task.setStatus(TaskStatus.COMPLETED);
        }

        return taskRepository.save(task);
    }

    public List<Task> filterTasks(
            TaskStatus status,
            TaskPriority priority) {

        if (status != null && priority != null) {
            return taskRepository.findByStatusAndPriority(status, priority);
        }

        if (status != null) {
            return taskRepository.findByStatus(status);
        }

        if (priority != null) {
            return taskRepository.findByPriority(priority);
        }

        return taskRepository.findAll();
    }

    public void deleteTask(Long id) {
        Task task = getTaskById(id);
        taskRepository.delete(task);
    }
}