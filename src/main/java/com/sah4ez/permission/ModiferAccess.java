package com.sah4ez.permission;

/**
 * Created by aleksandr on 20.12.16.
 */
public enum ModiferAccess {

    EDIT(1, "edit"), //1
    READ(2, "read"), //2
    HIDE(3, "hide") //3;
    ;

    private int id = 0;
    private String message = "";

    ModiferAccess(int id, String condition){
        this.id = id;
        this.message = message;
    }

    public int getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }
}
