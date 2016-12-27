package com.github.sah4ez.example.layout;

import com.github.sah4ez.core.elements.MenuNavigator;
import com.github.sah4ez.core.elements.Workspace;

/**
 * Created by aleksandr on 20.12.16.
 */
public class MyMenu extends MenuNavigator {

    public MyMenu(String caption, Workspace parent) {
        super(caption, parent, "MyMenu1");
    }

    @Override
    public void add() {
        if (getAdd().getStyleName() == null)
            getAdd().setStyleName(ENABLE_BUTTON_STYLE);
        else
            getAdd().setStyleName(null);
    }

    @Override
    public void delete() {
        if (getDelete().getStyleName() == null)
            getDelete().setStyleName(ENABLE_BUTTON_STYLE);
        else
            getDelete().setStyleName(null);
    }

    @Override
    public void print() {
        if (getPrint().getStyleName() == null)
            getPrint().setStyleName(ENABLE_BUTTON_STYLE);
        else
            getPrint().setStyleName(null);

    }
}
