package com.github.sah4ez.example.layout;

import com.github.sah4ez.core.elements.BottomTabs;
import com.github.sah4ez.core.elements.Logic;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Label;

/**
 * Created by aleksandr on 20.12.16.
 */
public class MyTabSheet extends BottomTabs {

    public MyTabSheet(Logic logic) {
        super(logic, "MyTabSheet1");
    }

    @Override
    public void initTabs() {
        addCaption("Tab1", "Tab2", "Tab3", "Tab4");

        addComponent(new Label("label1"),
                new Label("label2"),
                new Label("label3"),
                new Label("label4"));

        addResource(FontAwesome.AMAZON,
                FontAwesome.AMAZON,
                FontAwesome.AMAZON,
                FontAwesome.AMAZON
        );
    }

    @Override
    public void clear() {

    }
}
