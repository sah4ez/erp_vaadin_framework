package com.sah4ez.example;

import com.sah4ez.example.layout.MyLayout;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Page;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

/**
 * Created by aleksandr on 20.12.16.
 */
public class MyView extends VerticalLayout implements View{
    private MyLogic logic = new MyLogic(this);

    public MyView(){
        Page page = UI.getCurrent().getPage();
        page.setUriFragment("!MyView/", false);
        MyLayout layout = new MyLayout(logic);
        addComponent(layout);
        setSizeFull();
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {

    }
}
