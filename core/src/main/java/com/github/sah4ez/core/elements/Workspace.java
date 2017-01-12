package com.github.sah4ez.core.elements;

import com.github.sah4ez.core.data.TreeDataContainer;
import com.github.sah4ez.core.permission.ModifierAccess;
import com.github.sah4ez.core.permission.PermissionAccess;
import com.github.sah4ez.core.permission.PermissionAccessUI;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.*;
import org.tepi.filtertable.FilterTable;
import org.tepi.filtertable.FilterTreeTable;

/**
 * Created by aleksandr on 20.12.16.
 */
abstract public class Workspace extends CssLayout implements PermissionAccessUI {
    private Logic logic;

    private Float splitPosition = 50f;

    private Mode mode = Mode.NORMAL;

    private String identify = "";

    private String CAPTION = "";
    private ThemeResource PICTURE = null;

    private FilterTable table = null;
    private FilterTable tableAll = null;

    private ItemClickEvent.ItemClickListener editItemClickListener;
    private ItemClickEvent.ItemClickListener editItemClickListenerAll;

    private VerticalSplitPanel verticalSplitPanel = null;
    private HorizontalSplitPanel horizontalSplitPanel = null;

    private BottomTabs bottomTabs = null;

    private MenuNavigator navigator = null;

    private FilterPanel filterPanel = null;

    private ModifierAccess modifierAccess = ModifierAccess.HIDE;

    private VerticalLayout layout;
    private ItemClickEvent.ItemClickListener selectItemClickListener;
    private ItemClickEvent.ItemClickListener selectItemClickListenerAll;

    public Workspace(Logic logic, String identify) {
        this.logic = logic;
        setIdentify(identify);
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

    private VerticalSplitPanel verticalSplitPanel() {
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

    private HorizontalSplitPanel horizontalSplitPanel() {

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
        this.splitPosition = splitPosition;
        horizontalSplitPanel.setSplitPosition(this.splitPosition, Unit.PERCENTAGE);
        if (this.splitPosition == 100f) {
            horizontalSplitPanel.setLocked(true);
        } else {
            horizontalSplitPanel.setLocked(false);
        }
    }

    private MenuBar navigatorLayout() {

        navigator = new MenuNavigator("", this, MenuNavigator.class.toString() + "1") {
            private String identify = "";

            @Override
            public String getIdentify() {
                return this.identify;
            }

            @Override
            public void setIdentify(String identify) {
                this.identify = identify;
            }

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
        return navigator;
    }

    private FilterPanel filterPanel() {
        filterPanel = new FilterPanel(this.getTable()) {
            @Override
            public void addParentsAndChildren(TreeDataContainer treeDataContainer) {

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
        };
        filterPanel.setWidth("100%");
        return filterPanel;
    }

    @Override
    public void setIdentify(String identify) {
        this.identify = identify;
    }

    @Override
    public String getIdentify() {
        return this.identify;
    }

    public String getCAPTION() {
        return CAPTION;
    }
//    ====================

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

    public void setTable(FilterTable fTable) {
        table = fTable;
        table.setFilterBarVisible(true);
        table.setMultiSelect(true);
        table.setSizeFull();
        table.setSelectable(true);
        table.setImmediate(true);
        table.setNullSelectionAllowed(true);
    }

    public VerticalSplitPanel getVerticalSplitPanel() {
        return verticalSplitPanel;
    }

    public HorizontalSplitPanel getHorizontalSplitPanel() {
        return horizontalSplitPanel;
    }

    public TabSheet getBottomTabs() {
        return bottomTabs;
    }

    public void setBottomTabs(BottomTabs bottomTabs) {
        this.bottomTabs = bottomTabs;
        verticalSplitPanel.setSecondComponent(this.bottomTabs);
        logic.addUi(bottomTabs);
    }

    public MenuNavigator getNavigator() {
        return navigator;
    }

    public void setNavigator(MenuNavigator navigator) {
        this.navigator = navigator;
        layout.addComponent(this.navigator, 0);
        logic.addUi(navigator);
    }

    public FilterPanel getFilterPanel() {
        return this.filterPanel;
    }

    public void setFilterPanel(FilterPanel filterPanel) {
        this.filterPanel = filterPanel;
    }

    public FilterTreeTable getFilterTable() {
        throw new UnsupportedOperationException("Not implemented for common class Workspace" +
                ", override this method if use FilterTreeTable.");
    }

    public void setModifierAccess(ModifierAccess permission) {
        if (navigator != null) {
            navigator.replacePermissionAccess(permission);
        }
        if (bottomTabs != null) {
            bottomTabs.replacePermissionAccess(permission);
        }

        this.modifierAccess = permission;

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

    public void replacePermissionAccess(ModifierAccess permissionAccess) {
        PermissionAccess.replacePermissionAccess(this, permissionAccess);
    }

    public ModifierAccess getModifierAccess() {
        return modifierAccess;
    }

    public void hideBottomPanel() {
        getVerticalSplitPanel().setSplitPosition(100, Unit.PERCENTAGE);
        getVerticalSplitPanel().setLocked(true);
    }

    public void showBottomPanel() {
        getVerticalSplitPanel().setSplitPosition(50, Unit.PERCENTAGE);
        getVerticalSplitPanel().setLocked(false);
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

    public ItemClickEvent.ItemClickListener getEditItemClickListenerAll() {
        return editItemClickListenerAll;
    }

    public void setEditItemClickListenerAll(ItemClickEvent.ItemClickListener editItemClickListenerAll) {
        this.editItemClickListenerAll = editItemClickListenerAll;
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

    public abstract void clearLayout();
}
