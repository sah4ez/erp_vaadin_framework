package com.sah4ez.example;

import com.sah4ez.elements.CommonView;
import com.sah4ez.example.layout.MyLayout;
import com.sah4ez.permission.ModifierAccess;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Page;
import com.vaadin.ui.UI;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by aleksandr on 20.12.16.
 */
public class MyView extends CommonView {
    private MyLogic logic = new MyLogic(this);

    public MyView() {
        setLogic(logic);
        Page page = UI.getCurrent().getPage();
        page.setUriFragment("!MyView/", false);
        MyLayout layout = new MyLayout(logic);
        addComponent(layout);
        setSizeFull();
    }

    @Override
    public Map<String, ModifierAccess> loadComponents() {
        Map<String, ModifierAccess> map = new HashMap<>();
        map.put("sss", ModifierAccess.EDIT);
        return map;
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {

    }
}
