package com.example.tasklistclient.model;

import java.io.Serializable;


public class TaskItem implements Serializable {
    private Long id;

    private String name;

    private String description;

    private TaskStatus status;

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    @Override
    public String toString() {
        return "TaskItem [" + id + ", "  + status + ", " + name + "] ";
    }
}
