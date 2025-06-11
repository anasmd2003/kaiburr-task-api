package com.kaiburr.taskapi.controller;

import com.kaiburr.taskapi.model.Task;
import com.kaiburr.taskapi.model.TaskExecution;
import com.kaiburr.taskapi.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskService service;

    @GetMapping
    public ResponseEntity<?> getTasks(@RequestParam(required = false) String id) {
        if (id != null) {
            return service.getTaskById(id)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } else {
            return ResponseEntity.ok(service.getAllTasks());
        }
    }

    @PutMapping
    public ResponseEntity<?> createTask(@RequestBody Task task) {
        return ResponseEntity.ok(service.createOrUpdateTask(task));
    }

    @DeleteMapping
    public ResponseEntity<?> deleteTask(@RequestParam String id) {
        service.deleteTask(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchTask(@RequestParam String name) {
        List<Task> tasks = service.searchByName(name);
        return tasks.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(tasks);
    }

    @PutMapping("/{id}/execute")
    public ResponseEntity<?> runCommand(@PathVariable String id) throws Exception {
        return ResponseEntity.ok(service.executeCommand(id));
    }
}
