package com.github.sah4ez.example;

import com.vaadin.navigator.Navigator;
import com.vaadin.ui.MenuBar;
import com.github.sah4ez.core.elements.*;

/**
 * Created by aleksandr on 20.12.16.
 */
public class MyMenu extends Menu {
    public MyMenu(Navigator navigator) {
        super(navigator);
    }

    @Override
    public MenuBar.Command loginCommand() {
        return null;
    }
}
