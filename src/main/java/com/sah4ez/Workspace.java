package com.sah4ez;

import com.sah4ez.data.TreeBeanContainer;
import com.sah4ez.permission.ModiferAccess;
import com.sah4ez.permission.PermissionAccess;
import com.sah4ez.permission.PermissionAccessUI;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.*;
import org.tepi.filtertable.FilterTable;
import org.tepi.filtertable.FilterTreeTable;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Created by aleksandr on 20.12.16.
 */
abstract public class Workspace extends CssLayout implements PermissionAccessUI {
    private Logic logic;

    private Float splitPosition = 50f;

    private Mode mode = Mode.NORMAL;

    public String CAPTION = "";
    public ThemeResource PICTURE = null;

    private FilterTable table = null;
    private FilterTable tableAll = null;

    private ItemClickEvent.ItemClickListener editItemClickListener;

    private ItemClickEvent.ItemClickListener editItemClickListenerAll;

    private VerticalSplitPanel verticalSplitPanel = null;
    private HorizontalSplitPanel horizontalSplitPanel = null;

    private BottomTabs bottomTabs = null;

    private MenuNavigator abstractNavigator = null;

    private FilterPanel filterPanel = null;

    private ModiferAccess permissionAccess = ModiferAccess.HIDE;

    private VerticalLayout layout;
    private ItemClickEvent.ItemClickListener selectItemClickListener;
    private ItemClickEvent.ItemClickListener selectItemClickListenerAll;

    public Workspace(Logic logic) {
        this.logic = logic;
        table();
        tableAll();
        navigatorLayout();
        filterPanel();
        horizontalSplitPanel();
        verticalSplitPanel();
        addComponent(verticalSplitPanel);
        editOff();
        setSizeFull();
    }

    private FilterTable table() {
        table = new FilterTable();
        table.setFilterBarVisible(true);
        table.setMultiSelect(true);
        table.setSizeFull();
        table.setSelectable(true);
        table.setImmediate(true);
        table.setNullSelectionAllowed(true);
        editItemClickListener = editTableItemClick();
        selectItemClickListener = selectTableItemClick();
        table.addItemClickListener(editItemClickListener);
        table.addItemClickListener(selectItemClickListener);
        return table;
    }

    abstract protected ItemClickEvent.ItemClickListener editTableItemClick();

    abstract protected ItemClickEvent.ItemClickListener selectTableItemClick();

    private FilterTable tableAll() {
        tableAll = new FilterTable();
        tableAll.setFilterBarVisible(true);
        tableAll.setSizeFull();
        tableAll.setSelectable(true);
        tableAll.setMultiSelect(true);
        tableAll.setImmediate(true);
        tableAll.setColumnCollapsingAllowed(true);
        editItemClickListenerAll = editTableAllItemClick();
        selectItemClickListenerAll = selectTableAllItemClick();
        tableAll.addItemClickListener(editItemClickListenerAll);
        tableAll.addItemClickListener(selectItemClickListenerAll);
        return tableAll;
    }

    abstract protected ItemClickEvent.ItemClickListener editTableAllItemClick();

    abstract protected ItemClickEvent.ItemClickListener selectTableAllItemClick();

    public VerticalSplitPanel verticalSplitPanel() {
        verticalSplitPanel = new VerticalSplitPanel();
        verticalSplitPanel.setSizeFull();
        layout = new VerticalLayout();
        layout.setSizeFull();
        layout.addComponents(filterPanel, horizontalSplitPanel);
        layout.setExpandRatio(horizontalSplitPanel, 1.0f);
        verticalSplitPanel.setFirstComponent(layout);
        verticalSplitPanel.setStyleName("large");
        return verticalSplitPanel;
    }

    public HorizontalSplitPanel horizontalSplitPanel() {

        horizontalSplitPanel = new HorizontalSplitPanel();
        horizontalSplitPanel.setSizeFull();
        horizontalSplitPanel.setFirstComponent(table);
        horizontalSplitPanel.setSecondComponent(tableAll);
        setSplitPosition(100f);
        horizontalSplitPanel.setStyleName("large");
        return horizontalSplitPanel;
    }

    public Float getSplitPosition() {
        return splitPosition;
    }

    public void setSplitPosition(Float splitPosition) {
        //TODO реализовать цветовую индикацию при нажатии на menuItem
        this.splitPosition = splitPosition;
//        getAbstractNavigator().addStyleName("not-push");
        horizontalSplitPanel.setSplitPosition(this.splitPosition, Unit.PERCENTAGE);
        if (splitPosition == 100f) {
            horizontalSplitPanel.setLocked(true);
//            if (getAbstractNavigator().getItems().size() > 0)
//                (getAbstractNavigator()
//                        .getItems()
//                        .get(0))
//                        .setStyleName("not-push");
        } else {
            horizontalSplitPanel.setLocked(false);
//            if (getAbstractNavigator().getItems().size() > 0)
//                (getAbstractNavigator()
//                        .getItems()
//                        .get(0))
//                        .setStyleName("push");
        }
    }

    public void setTable(FilterTable fTable) {
        table = fTable;
        table.setFilterBarVisible(true);
        table.setMultiSelect(true);
        table.setSizeFull();
        table.setSelectable(true);
        table.setImmediate(true);
        table.setNullSelectionAllowed(true);
    }

    private MenuBar navigatorLayout() {
        abstractNavigator = new MenuNavigator("", this) {
            @Override
            public void add() {

            }

            @Override
            public void delete() {

            }

            @Override
            public void print() {

            }

            @Override
            public void filter() {

            }
        };
        return abstractNavigator;
    }

    private FilterPanel filterPanel() {
        filterPanel = new FilterPanel(this.getTable()) {
            @Override
            public void addParentsAndChildren(TreeBeanContainer treeBeanContainer) {

            }

            @Override
            public Object getNameIdentifyColumn() {
                if (table.getContainerPropertyIds().contains("numberOfString")) return "numberOfString";
                if (table.getContainerPropertyIds().contains("id_product")) return "id_product";
                return "id";
            }

            @Override
            public void sortListener() {

            }

            @Override
            public void beforeFilter() {

            }
        };
        filterPanel.setWidth("100%");
        return filterPanel;
    }
//    ====================


    public String getCAPTION() {
        return CAPTION;
    }

    public void setCAPTION(String CAPTION) {
        this.CAPTION = CAPTION;
    }

    public ThemeResource getPICTURE() {
        return PICTURE;
    }

    public void setPICTURE(ThemeResource PICTURE) {
        this.PICTURE = PICTURE;
    }

    public FilterTable getTableAll() {
        return tableAll;
    }

    public void setTableAll(FilterTable tableAll) {
        this.tableAll = tableAll;
    }

    public FilterTable getTable() {
        return table;
    }

    public VerticalSplitPanel getVerticalSplitPanel() {
        return verticalSplitPanel;
    }

    public void setVerticalSplitPanel(VerticalSplitPanel verticalSplitPanel) {
        this.verticalSplitPanel = verticalSplitPanel;
    }

    public HorizontalSplitPanel getHorizontalSplitPanel() {
        return horizontalSplitPanel;
    }

    public void setHorizontalSplitPanel(HorizontalSplitPanel horizontalSplitPanel) {
        this.horizontalSplitPanel = horizontalSplitPanel;
    }

    public TabSheet getBottomTabs() {
        return bottomTabs;
    }

    public void setBottomTabs(BottomTabs bottomTabs) {
        this.bottomTabs = bottomTabs;
        verticalSplitPanel.setSecondComponent(this.bottomTabs);
        logic.addUi(bottomTabs);
    }

    public MenuNavigator getAbstractNavigator() {
        return abstractNavigator;
    }

    public void setAbstractNavigator(MenuNavigator abstractNavigator) {
        this.abstractNavigator = abstractNavigator;
        layout.addComponent(this.abstractNavigator, 0);
        logic.addUi(abstractNavigator);
    }

    public void setFilterPanel(FilterPanel filterPanel) {
        this.filterPanel = filterPanel;
    }

    public FilterPanel getFilterPanel() {
        return this.filterPanel;
    }

    public FilterTreeTable getFilterTable() {
        throw new NotImplementedException();
    }

    public void setPermissionAccess(ModiferAccess permission) {
        if (abstractNavigator != null) {
            abstractNavigator.replacePermissionAccess(permission);
        }
        if (bottomTabs != null) {
            bottomTabs.replacePermissionAccess(permission);
        }

        this.permissionAccess = permission;

        switch (permission) {
            case EDIT: {
                this.setVisible(true);
                this.setEnabled(true);
                break;
            }
            case READ: {
                this.setVisible(true);
                this.setEnabled(false);
                break;
            }
            case HIDE: {
                this.setVisible(false);
                this.setEnabled(false);
                break;
            }
        }
    }

    public void replacePermissionAccess(ModiferAccess permissionAccess) {
        PermissionAccess.replacePermissionAccess(this, permissionAccess);
    }

    public ModiferAccess getPermissionAccess() {
        return permissionAccess;
    }

    public void hideBottomPanel() {
        getVerticalSplitPanel().setSplitPosition(100, Unit.PERCENTAGE);
        getVerticalSplitPanel().setLocked(true);
    }

    public void showBottomPanel() {
        getVerticalSplitPanel().setSplitPosition(50, Unit.PERCENTAGE);
        getVerticalSplitPanel().setLocked(true);
    }

    public void editOff() {
        mode = Mode.NORMAL;
        getTable().removeItemClickListener(editItemClickListener);
        getTableAll().removeItemClickListener(editItemClickListenerAll);

        getTable().addItemClickListener(selectItemClickListener);
        getTableAll().addItemClickListener(selectItemClickListenerAll);
    }

    public void editOn() {
        mode = Mode.CHANGE;
        getTable().removeItemClickListener(selectItemClickListener);
        getTableAll().removeItemClickListener(selectItemClickListenerAll);

        getTable().addItemClickListener(editItemClickListener);
        getTableAll().addItemClickListener(editItemClickListenerAll);
    }

    public Mode getMode() {
        return mode;
    }

    public void setMode(Mode mode) {
        this.mode = mode;
    }

    protected ItemClickEvent.ItemClickListener getEditItemClickListener() {
        return editItemClickListener;
    }

    protected void setEditItemClickListener(ItemClickEvent.ItemClickListener editItemClickListener) {
        this.editItemClickListener = editItemClickListener;
    }

    public void setEditItemClickListenerAll(ItemClickEvent.ItemClickListener editItemClickListenerAll) {
        this.editItemClickListenerAll = editItemClickListenerAll;
    }

    public ItemClickEvent.ItemClickListener getEditItemClickListenerAll() {
        return editItemClickListenerAll;
    }

    public ItemClickEvent.ItemClickListener getSelectItemClickListener() {
        return selectItemClickListener;
    }

    public void setSelectItemClickListener(ItemClickEvent.ItemClickListener selectItemClickListener) {
        this.selectItemClickListener = selectItemClickListener;
    }

    public ItemClickEvent.ItemClickListener getSelectItemClickListenerAll() {
        return selectItemClickListenerAll;
    }

    public void setSelectItemClickListenerAll(ItemClickEvent.ItemClickListener selectItemClickListenerAll) {
        this.selectItemClickListenerAll = selectItemClickListenerAll;
    }

}
