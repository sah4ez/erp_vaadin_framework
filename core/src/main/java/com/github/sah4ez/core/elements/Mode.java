package com.github.sah4ez.core.elements;

/**
 * Created by aleksandr on 20.12.16.
 */
public enum Mode {
    CHANGE(1, "change"),
    REMOVE(2, "remove"),
    NORMAL(3, "normal"),
    PROJECT(4, "project"),
    TASK(5, "task");

    private int id = 0;
    private String message = "";

    Mode(int i, String mode) {
        this.id = i;
        this.message = mode;
    }

    public int getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }
}
