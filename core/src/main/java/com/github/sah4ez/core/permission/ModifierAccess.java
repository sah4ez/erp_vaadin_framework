package com.github.sah4ez.core.permission;

/**
 * Created by aleksandr on 20.12.16.
 */
public enum ModifierAccess {

    EDIT(1, "edit"), //1
    READ(2, "read"), //2
    HIDE(3, "hide") //3;
    ;

    private int id = 0;
    private String message = "";

    ModifierAccess(int id, String condition){
        this.id = id;
        this.message = condition;
    }

    public int getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }
}
