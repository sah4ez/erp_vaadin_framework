package com.github.sah4ez.core.elements;

import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.server.*;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by aleksandr on 20.12.16.
 */
public abstract class Menu extends CssLayout {

    private static final String VALO_MENUITEMS = "valo-menuitems";
    private static final String VALO_MENU_TOGGLE = "valo-menu-toggle";
    private static final String VALO_MENU_VISIBLE = "valo-menu-visible";

    private Navigator navigator;
    private Map<String, Button> viewButtons = new HashMap<>();

    private MenuBar.Command loginCommand;

    private CssLayout menuItemsLayout;
    private CssLayout menuPart;

    private Image image;

    public Menu(Navigator navigator) {
        this.navigator = navigator;
        setPrimaryStyleName(ValoTheme.MENU_ROOT);
        menuPart = new CssLayout();
        menuPart.addStyleName(ValoTheme.MENU_PART);

        final HorizontalLayout top = new HorizontalLayout();
        top.setDefaultComponentAlignment(Alignment.MIDDLE_LEFT);
        top.addStyleName(ValoTheme.MENU_TITLE);
        top.setSpacing(true);

        Label title = new Label("PLM-TEAM");
        title.addStyleName(ValoTheme.LABEL_H3);
        title.setSizeUndefined();
        top.addComponent(title);

        menuPart.addComponent(top);
        loginCommand = loginCommand();

        //TODO необходимо реализовать поведение компонента в зависимости от состояния пользователя
        if (userIsLoggined()) {
//            menuPart.addComponent(buildUserMenu());
//            menuPart.addComponent(addAttr());
        } else {
            MenuBar loginMenu = new MenuBar();
            loginMenu.addItem("Войти", loginCommand);

            loginMenu.addStyleName("user-menu");
            loginMenu.addStyleName("login-menu");
            menuPart.addComponent(loginMenu);
        }


        final Button showMenu = new Button("Меню", event -> {
            if (menuPart.getStyleName().contains(VALO_MENU_VISIBLE)) {
                menuPart.removeStyleName(VALO_MENU_VISIBLE);
            } else {
                menuPart.addStyleName(VALO_MENU_VISIBLE);
            }
        });

        showMenu.addStyleName(ValoTheme.BUTTON_PRIMARY);
        showMenu.addStyleName(ValoTheme.BUTTON_SMALL);
        showMenu.addStyleName(VALO_MENU_TOGGLE);
        showMenu.setIcon(FontAwesome.NAVICON);
        menuPart.addComponent(showMenu);

        // container for the navigation buttons, which are added by addView()
        menuItemsLayout = new CssLayout();
        menuItemsLayout.setPrimaryStyleName(VALO_MENUITEMS);
        menuPart.addComponent(menuItemsLayout);

        addComponent(menuPart);
    }

    public boolean userIsLoggined() {
        return false;
    }

    public abstract MenuBar.Command loginCommand();


    public void addView(View view, final String name, String caption,
                        Resource icon) {
        navigator.addView(name, view);
        createViewButton(name, caption, icon);
    }

    public void addView(Class<? extends View> viewClass, final String name,
                        String caption, Resource icon) {
        navigator.addView(name, viewClass);
        createViewButton(name, caption, icon);
    }

    private void createViewButton(final String name, String caption,
                                  Resource icon) {
        Button button = new Button(caption, (Button.ClickListener) event -> navigator.navigateTo(name));
        button.setPrimaryStyleName(ValoTheme.MENU_ITEM);
        button.setIcon(icon);
        menuItemsLayout.addComponent(button);
        viewButtons.put(name, button);
    }

    public void setActiveView(String viewName) {
        for (Button button : viewButtons.values()) {
            button.removeStyleName("selected");
        }
        Button selected = viewButtons.get(viewName);
        if (selected != null) {
            selected.addStyleName("selected");
        }
        menuPart.removeStyleName(VALO_MENU_VISIBLE);
    }

    public void setImage(Image image){
        this.image = image;
    }
}
