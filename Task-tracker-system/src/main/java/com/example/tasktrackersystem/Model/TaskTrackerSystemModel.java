package com.example.tasktrackersystem.Model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.concurrent.atomic.AtomicLong;

@Data
@AllArgsConstructor
public class TaskTrackerSystemModel {
    private static final AtomicLong idGenerator = new AtomicLong(0);

    private long id;
    private String title;
    private String description;
    private String status;

}
