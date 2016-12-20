package com.sah4ez.elements;

/**
 * Created by aleksandr on 20.12.16.
 */
public enum Mode {
    CHANGE(1),
    REMOVE(2),
    NORMAL(3),
    PROJECT(4),
    TASK(5);

    private int id = 0;

    Mode(int i) {
        setId(i);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
