package com.sah4ez.example;

import com.sah4ez.elements.Menu;
import com.vaadin.navigator.Navigator;
import com.vaadin.ui.MenuBar;

/**
 * Created by aleksandr on 20.12.16.
 */
public class MyMenu extends Menu{
    public MyMenu(Navigator navigator) {
        super(navigator);
    }

    @Override
    public MenuBar.Command loginCommand() {
        return null;
    }
}
