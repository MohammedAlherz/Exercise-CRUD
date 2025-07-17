package com.example.tasktrackersystem.Controller;

import com.example.tasktrackersystem.Api.ApiResponse;
import com.example.tasktrackersystem.Model.TaskTrackerSystemModel;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/api/v1/task-tracker-system")
public class TaskTrackerSystemController {

    ArrayList<TaskTrackerSystemModel> tasks = new ArrayList<>();
    static final AtomicLong idGenerator = new AtomicLong(1);

    // Create Task
    @PostMapping("/add")
    public ApiResponse addTask(@RequestBody TaskTrackerSystemModel task) {
        task.setId(idGenerator.getAndIncrement());
        tasks.add(task);
        return new ApiResponse("Task added successfully!");
    }

    // Get All Tasks
    @GetMapping("/get")
    public ArrayList<TaskTrackerSystemModel> getTasks() {
        return tasks;
    }

    // Update Task by ID
    @PutMapping("/update/{id}")
    public ApiResponse updateTask(@RequestBody TaskTrackerSystemModel updatedTask, @PathVariable long id) {
        for (TaskTrackerSystemModel task : tasks) {
            if (task.getId() == id) {
                task.setTitle(updatedTask.getTitle());
                task.setDescription(updatedTask.getDescription());
                task.setStatus(updatedTask.getStatus());
                return new ApiResponse("Task updated successfully!");
            }
        }
        return new ApiResponse("Task not found!");
    }

    // Delete Task by ID
    @DeleteMapping("/delete/{id}")
    public ApiResponse deleteTask(@PathVariable long id) {
        for (TaskTrackerSystemModel task : tasks) {
            if (task.getId() == id) {
                tasks.remove(task);
                return new ApiResponse("Task deleted successfully!");
            }
        }
        return new ApiResponse("Task not found!");
    }

    // Change Status by ID (Toggle between "done" and "not done")
    @PutMapping("/changeStatus/{id}")
    public ApiResponse changeStatus(@PathVariable long id) {
        for (TaskTrackerSystemModel task : tasks) {
            if (task.getId() == id) {
                String currentStatus = task.getStatus();
                task.setStatus(currentStatus.equalsIgnoreCase("done") ? "not done" : "done");
                return new ApiResponse("Task status changed successfully!");
            }
        }
        return new ApiResponse("Task not found!");
    }

    // Search Task by Title
    @GetMapping("/search/{title}")
    public Object searchTask(@PathVariable String title) {
        for (TaskTrackerSystemModel task : tasks) {
            if (Objects.equals(task.getTitle(), title)) {
                return task;  // return full task info
            }
        }
        return new ApiResponse("Task not found!");
    }
}
