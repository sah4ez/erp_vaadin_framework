package com.github.sah4ez.core.data;

/**
 * Created by aleksandr on 08.01.17.
 */
public enum Condition implements CellCondition{

    EDIT(1, "edit"),
    NOT_USE(2, "not-use"),
    USE(3, "use"),
    USE_EDIT(4, "use-edit"),
    USE_NOT_EDIT(5, "use-not-edit");

    private Integer id;
    private String text;

    Condition(Integer id, String text){
        this.id = id;
        this.text = text;
    }

    public Integer getId() {
        return id;
    }

    @Override
    public String getCssStyle() {
        return text;
    }
}

