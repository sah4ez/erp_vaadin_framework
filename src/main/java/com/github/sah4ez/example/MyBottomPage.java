package com.github.sah4ez.example;

import com.github.sah4ez.core.elements.BottomPage;
import com.github.sah4ez.example.layout.MyLayout;
import com.vaadin.server.Resource;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;

/**
 * Created by pc999 on 1/10/17.
 */
public class MyBottomPage extends BottomPage<Button, MyLayout> {
    public MyBottomPage(Button component, String caption, Resource resource) {
        setComponent(component);
        setCaption(caption);
        setResource(resource);
    }

    public void action(Component.Event eventObject) {
        getExternalComponet().setCaption(getComponent().getCaption());//.getRowGenerator().generateRow(new CustomTable("dwa"), 12);//.getComponent().setCaption(event.getItem().toString();
    }
}
