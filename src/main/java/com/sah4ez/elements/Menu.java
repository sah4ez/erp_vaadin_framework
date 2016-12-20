package com.sah4ez.elements;

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
    private Map<String, Button> viewButtons = new HashMap<String, Button>();

    private MenuBar.Command loginCommand;

    private CssLayout menuItemsLayout;
    private CssLayout menuPart;

    public Menu(Navigator navigator) {
        this.navigator = navigator;
        setPrimaryStyleName(ValoTheme.MENU_ROOT);
        menuPart = new CssLayout();
        menuPart.addStyleName(ValoTheme.MENU_PART);

        // header of the menu
        final HorizontalLayout top = new HorizontalLayout();
        top.setDefaultComponentAlignment(Alignment.MIDDLE_LEFT);
        top.addStyleName(ValoTheme.MENU_TITLE);
        top.setSpacing(true);
        Label title = new Label("PLM-TEAM");
        title.addStyleName(ValoTheme.LABEL_H3);
        title.setSizeUndefined();
        Image image = new Image(null, new ThemeResource("img/table-logo.png"));
        image.setStyleName("logo");
        top.addComponent(image);
        top.addComponent(title);
        menuPart.addComponent(top);

        loginCommand = loginCommand();


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


        // button for toggling the visibility of the menu when on a small screen
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

    public boolean userIsLoggined(){
        return false;
    }

    public abstract MenuBar.Command loginCommand();


    /**
     * Register a pre-created view instance in the navigation menu and in the
     * {@link Navigator}.
     *
     * @param view    view instance to register
     * @param name    view name
     * @param caption view caption in the menu
     * @param icon    view icon in the menu
     * @see Navigator#addView(String, View)
     */
    public void addView(View view, final String name, String caption,
                        Resource icon) {
        navigator.addView(name, view);
        createViewButton(name, caption, icon);
    }

    /**
     * Register a view in the navigation menu and in the {@link Navigator} based
     * on a view class.
     *
     * @param viewClass class of the views to create
     * @param name      view name
     * @param caption   view caption in the menu
     * @param icon      view icon in the menu
     * @see Navigator#addView(String, Class)
     */
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

    /**
     * Highlights a view navigation button as the currently active view in the
     * menu. This method does not perform the actual navigation.
     *
     * @param viewName the name of the view to show as active
     */
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
}
