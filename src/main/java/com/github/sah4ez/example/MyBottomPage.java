package com.github.sah4ez.example;

import com.github.sah4ez.core.elements.BottomPage;
import com.github.sah4ez.example.layout.MyLayout;
import com.vaadin.server.Resource;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;

import java.util.EventObject;

/**
 * Created by pc999 on 1/10/17.
 */
public class MyBottomPage extends BottomPage<Button, MyLayout> {
    public MyBottomPage(Button component, String caption, Resource resource) {
        component.addListener(action());
        setComponent(component);
        setCaption(caption);
        setResource(resource);
    }

    private Component.Listener action() {
        return this::listener;
    }

    @Override
    public void listener(EventObject eventObject) {
        getExternalComponet().setCaption(getComponent().getCaption());//.getRowGenerator().generateRow(new CustomTable("dwa"), 12);//.getComponent().setCaption(event.getItem().toString();
    }
}
