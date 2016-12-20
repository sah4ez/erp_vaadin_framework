package com.sah4ez.example.layout;

import com.sah4ez.elements.Logic;
import com.sah4ez.elements.Workspace;
import com.sah4ez.example.data.ElementContainer;
import com.vaadin.event.ItemClickEvent;

/**
 * Created by aleksandr on 20.12.16.
 */
public class MyLayout extends Workspace {
    private ElementContainer container = new ElementContainer();
    private MyTabSheet tabSheet;
    private MyMenu menu;

    public MyLayout(Logic logic) {
        super(logic);
        tabSheet = new MyTabSheet(logic);
        menu = new MyMenu("myMenu", this);
        logic.setDataToTable(container.loadAllData(), getTable());
        setBottomTabs(tabSheet);
        setNavigator(menu);
    }

    @Override
    protected ItemClickEvent.ItemClickListener editTableItemClick() {
        return itemClickEvent -> {
        };
    }

    @Override
    protected ItemClickEvent.ItemClickListener selectTableItemClick() {
        return itemClickEvent -> {
        };
    }

    @Override
    protected ItemClickEvent.ItemClickListener editTableAllItemClick() {
        return itemClickEvent -> {
        };
    }

    @Override
    protected ItemClickEvent.ItemClickListener selectTableAllItemClick() {
        return itemClickEvent -> {
        };
    }

    public void initBottomTabs() {
    }

    public void initMenuNavigator() {
    }
}
