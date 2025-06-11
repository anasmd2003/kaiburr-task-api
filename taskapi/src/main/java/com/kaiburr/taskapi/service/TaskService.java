package com.kaiburr.taskapi.service;

import com.kaiburr.taskapi.model.Task;
import com.kaiburr.taskapi.model.TaskExecution;
import com.kaiburr.taskapi.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class TaskService {

    @Autowired
    private TaskRepository repository;

    public List<Task> getAllTasks() {
        return repository.findAll();
    }

    public Optional<Task> getTaskById(String id) {
        return repository.findById(id);
    }

    public Task createOrUpdateTask(Task task) {
        if (isCommandSafe(task.getCommand())) {
            return repository.save(task);
        } else {
            throw new IllegalArgumentException("Unsafe command detected.");
        }
    }

    public void deleteTask(String id) {
        repository.deleteById(id);
    }

    public List<Task> searchByName(String name) {
        return repository.findByNameContainingIgnoreCase(name);
    }

    public TaskExecution executeCommand(String id) {
        Task task = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        if (!isCommandSafe(task.getCommand())) {
            throw new IllegalArgumentException("Unsafe command detected.");
        }

        try {
            Date start = new Date();
            Process process = Runtime.getRuntime().exec(task.getCommand());

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String output = reader.lines().collect(Collectors.joining("\n"));
            Date end = new Date();

            TaskExecution exec = new TaskExecution(start, end, output);

            if (task.getTaskExecutions() == null) {
                task.setTaskExecutions(new ArrayList<>());
            }

            task.getTaskExecutions().add(exec);
            repository.save(task);

            return exec;

        } catch (Exception e) {
            throw new RuntimeException("Failed to execute command: " + e.getMessage(), e);
        }
    }

    private boolean isCommandSafe(String command) {
        String lower = command.toLowerCase();
        return !(lower.contains("rm ") || lower.contains("sudo") || lower.contains("shutdown"));
    }
}
