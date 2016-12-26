package com.github.sah4ez.core.elements;

import com.github.sah4ez.core.permission.ModifierAccess;
import com.github.sah4ez.core.permission.PermissionAccess;
import com.github.sah4ez.core.permission.PermissionAccessUI;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.MenuBar;

/**
 * Created by aleksandr on 20.12.16.
 */
public abstract class MenuNavigator extends MenuBar implements PermissionAccessUI {

    private String identify = "";

    private ModifierAccess permissionAccess = ModifierAccess.HIDE;

    private MenuItem add;
    private MenuItem delete;
    private MenuItem print;
    private MenuItem filter;

    public static final String ENABLE_BUTTON_STYLE ="highlight";

    private Workspace parent;

    public MenuNavigator(String caption, Workspace parent, String identify) {
        this.parent = parent;
        setIdentify(identify);
        setWidth("100%");
        Command addCommand = menuItem -> add();

        Command deleteCommand = menuItem -> delete();

        Command printCommand = menuItem -> print();

        Command filterCommand = menuItem -> filter();

        add = this.addItem("add" + caption, new ThemeResource("ico16/add.png"), addCommand);
        add.setDescription("Добавить");

        delete = this.addItem("delete" + caption, new ThemeResource("ico16/delete.png"), deleteCommand);
        delete.setDescription("Удалить");

        print = this.addItem("print" + caption, new ThemeResource("ico16/printer.png"), printCommand);
        print.setDescription("Печать");

        filter = this.addItem("filter" + caption, new ThemeResource("ico16/filter.png"), filterCommand);
        filter.setDescription("Сортировать");
        this.setStyleName("v-menubar-menuitem-caption-null-size");
        this.addStyleName("menu-navigator");
    }

    protected Workspace getParentLayout(){
        return this.parent;
    }

    @Override
    public void setIdentify(String identify) {
        this.identify = identify;
    }

    @Override
    public String getIdentify() {
        return identify;
    }

    abstract public void add();

    abstract public void delete();

    abstract public void print();

    public void filter() {
        FilterPanel filterPanel = parent.getFilterPanel();
        filterPanel.loadColumns();
        if (filterPanel.isVisible()) {
            filter.setStyleName(null);
            filterPanel.hide();
        } else {
            filter.setStyleName(ENABLE_BUTTON_STYLE);
            filterPanel.show();
        }
    }

    public MenuItem getAdd() {
        return add;
    }

    public MenuItem getDelete() {
        return delete;
    }

    public MenuItem getPrint() {
        return print;
    }

    public ModifierAccess getModifierAccess() {
        return this.permissionAccess;
    }

    public void setPermissionAccess(ModifierAccess permission) {
        this.permissionAccess = permission;
        switch (permission) {
            case EDIT: {
                edit();
                break;
            }
            case READ: {
                read();
                break;
            }
            case HIDE: {
                hide();
                break;
            }
        }
    }

    private void edit(){
        this.getItems().forEach(menuItem -> {
            menuItem.setEnabled(true);
            menuItem.setVisible(true);
        });
        this.setEnabled(true);
        this.setVisible(true);
    }

    private void read(){
        this.getItems().forEach(menuItem -> {
            menuItem.setEnabled(false);
            menuItem.setVisible(true);
        });
        this.setEnabled(false);
        this.setVisible(true);
    }

    private void hide(){
        this.getItems().forEach(menuItem -> {
            menuItem.setEnabled(false);
            menuItem.setVisible(false);
        });
        this.setEnabled(false);
        this.setVisible(false);
    }

    public void replacePermissionAccess(ModifierAccess permissionAccess) {
        PermissionAccess.replacePermissionAccess(this, permissionAccess);
    }

    public void setReadMode(){
        this.setEnabled(false);
    }

    public void setEditMode(){
        this.setEnabled(true);
    }

    public MenuItem getFilter() {
        return filter;
    }

    public void setFilter(MenuItem filter) {
        this.filter = filter;
    }

    public void hideAdd(){
        add.setVisible(false);
    }

    public void showAdd(){
        add.setVisible(true);
    }

    public void hideDelete(){
        delete.setVisible(false);
    }

    public void showDelete(){
        delete.setVisible(true);
    }

    public void hidePrint(){
        print.setVisible(false);
    }

    public void showPrint(){
        print.setVisible(true);
    }

    public void hideFilter(){
        filter.setVisible(false);
    }

    public void showFilter(){
        filter.setVisible(true);
    }

    public void editModeOff(){
        getAdd().setStyleName(null);
    }

    public void editModeOn(){
        getAdd().setStyleName(ENABLE_BUTTON_STYLE);
    }
}
