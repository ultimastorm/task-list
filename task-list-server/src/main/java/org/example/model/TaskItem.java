package org.example.model;

import jakarta.persistence.*;

import java.io.Serializable;


@Entity
@Table(name = "tasks")
public class TaskItem implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", length = 500)
    private String description;

//    @Convert(converter = TaskStatusConverter.class)
    @Column(name = "status", nullable = false)
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
