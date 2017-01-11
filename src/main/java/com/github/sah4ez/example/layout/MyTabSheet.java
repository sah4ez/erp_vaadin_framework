package com.github.sah4ez.example.layout;

import com.github.sah4ez.core.elements.BottomTabs;
import com.github.sah4ez.core.elements.Logic;
import com.github.sah4ez.example.MyBottomPage;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;

/**
 * Created by aleksandr on 20.12.16.
 */
public class MyTabSheet extends BottomTabs {
    private MyBottomPage page;

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

        page = new MyBottomPage(new Button("Hello"), "Tab5", FontAwesome.AMAZON);
        addPage(page);
    }

    public MyBottomPage getPage() {
        return page;
    }

    @Override
    public void clear() {

    }
}
