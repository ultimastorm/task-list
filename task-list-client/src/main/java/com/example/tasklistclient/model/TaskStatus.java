package com.example.tasklistclient.model;

public enum TaskStatus {
    OPEN(0),
    DONE(1);

    private final int value;

    private TaskStatus(int v) {
       this.value = v;
    }

    public int getValue() {
        return this.value;
    }
}
