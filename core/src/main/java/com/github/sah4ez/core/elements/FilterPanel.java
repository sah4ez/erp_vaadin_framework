package com.github.sah4ez.core.elements;

import com.github.sah4ez.core.data.DataContainer;
import com.github.sah4ez.core.data.TreeDataContainer;
import com.vaadin.data.Container;
import com.vaadin.data.util.filter.SimpleStringFilter;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.tepi.filtertable.FilterTable;
import org.tepi.filtertable.FilterTreeTable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aleksandr on 20.12.16.
 */
abstract public class FilterPanel extends VerticalLayout {
    private ComboBox column;
    private TextField text;
    private Button add;
    private Table table;
    private FilterTable filterTable;
    private FilterTreeTable filterTreeTable;
    private CssLayout filters;
    private List<Object> sortColumn = new ArrayList<>();
    private List<Boolean> sortAscending = new ArrayList<>();

    public FilterPanel(Table table) {
        this.table = table;
        this.filterTable = null;
        this.filterTreeTable = null;
        init();
        //addStyleName("blacklayout");
    }

    public FilterPanel(FilterTable table) {
        this.filterTable = table;
        this.table = null;
        this.filterTreeTable = null;
        init();
        //addStyleName("blacklayout");
    }

    public FilterPanel(FilterTreeTable filterTreeTable) {
        this.filterTreeTable = filterTreeTable;
        this.filterTable = null;
        this.table = null;
        init();
    }

    private void init() {
        addComponent(initFilters());
        addComponent(initComponents());
        setWidth("100%");
        setVisible(false);
    }

    private CssLayout initFilters() {
        filters = new CssLayout();
        filters.setCaption("Фильтры:");
        filters.setVisible(false);
        return filters;
    }

    private HorizontalLayout initComponents() {
        HorizontalLayout components = new HorizontalLayout();
        components.addComponent(initColumn());
        components.addComponent(initUp());
        components.addComponent(initDown());
        components.addComponent(initText());
        components.addComponent(initAdd());
        components.setSpacing(true);
        components.setMargin(true);
        return components;
    }

    protected Button filterButton(String caption) {
        Button btn = new Button(column.getItemCaption(column.getValue()) +
                " " + caption + " x");
        btn.setStyleName(ValoTheme.BUTTON_QUIET);

        filters.addComponent(btn);
        return btn;
    }

    private Component initAdd() {
        add = new Button("Добавить");
        add.addClickListener(this::addButton);
        add.setEnabled(false);
        return add;
    }

    protected void addButton(Button.ClickEvent event) {
        if (!filters.isVisible())
            filters.setVisible(true);

        if (column.getValue() == null) return;

        Button btn = filterButton(" содержит: " + text.getValue());

        Container.Filter filter = addFilter(column.getValue(), text.getValue());

        btn.addClickListener(removeFilterListener(btn, filter));

    }

    private TextField initText() {
        text = new TextField();
        text.setWidth("220px");
        text.setInputPrompt("Содержит");
        return text;
    }

    private Button initDown() {
        Button down = new Button("По убыванию");
        down.setStyleName(ValoTheme.BUTTON_LINK);
        down.addClickListener(this::descSortListener);
        return down;
    }

    protected void descSortListener(Button.ClickEvent event){
        if (!filters.isVisible())
            filters.setVisible(true);

        Button btnDesc = filterButton(": по убыванию");

        Object columnValue = column.getValue();
        addSort(columnValue, false);
        btnDesc.addClickListener(removeSortListener(btnDesc, columnValue));
    }

    private Button initUp() {
        Button up = new Button("По возрастанию");
        up.setStyleName(ValoTheme.BUTTON_LINK);
        up.addClickListener(this::ascSortListener);
        return up;
    }

    protected void ascSortListener(Button.ClickEvent event){
        if (!filters.isVisible())
            filters.setVisible(true);

        Button btnAsc = filterButton(": по возрастанию");

        Object columnValue = column.getValue();
        addSort(columnValue, true);
        btnAsc.addClickListener(removeSortListener(btnAsc, columnValue));
    }

    private Button.ClickListener removeSortListener(Button btn, Object column) {
        return clickEvent -> {
            removeFilter(btn);
            removeSort(column);
        };
    }

    private Button.ClickListener removeFilterListener(Button btn, Container.Filter column) {
        return clickEvent -> {
            removeFilter(btn);
            removeFilter(column);
        };
    }

    protected void removeFilter(Button btn) {
        filters.removeComponent(btn);
        if (filters.getComponentCount() == 0)
            filters.setVisible(false);
    }

    private ComboBox initColumn() {
        column = new ComboBox();
        column.setWidth("220px");
        column.setInputPrompt("Столбец для сортировки");
        loadColumns();

        column.addValueChangeListener(clickEvent -> {
            if (column.getValue() != null && !column.getItemCaption(column.getValue()).equals(""))
                add.setEnabled(true);
            else
                add.setEnabled(false);
        });
        return column;
    }

    public void loadColumns() {
        if (table != null) column.addItems(table.getVisibleColumns());
        if (filterTable != null) column.addItems(filterTable.getVisibleColumns());
        if (filterTreeTable != null) column.addItems(filterTreeTable.getVisibleColumns());

        for (Object itemId : column.getContainerDataSource().getItemIds()) {
            if (table != null) {
                column.setItemCaption(itemId, table.getColumnHeader(itemId));
            } else if (filterTable != null) {
                column.setItemCaption(itemId, filterTable.getColumnHeader(itemId));
            } else if (filterTreeTable != null) {
                column.setItemCaption(itemId, filterTreeTable.getColumnHeader(itemId));
            }
        }
    }

//    public void removeAllFilters() {
    //TODO Реализовать удаление всех фильров
//    }

    protected Container.Filter addFilter(Object value, String textValue) {

        Container.Filter filter = new SimpleStringFilter(value, textValue, true, false);

        DataContainer container = getContainer();

        assert container != null;
        container.addContainerFilter(filter);

        if (container instanceof TreeDataContainer) {
            TreeDataContainer treeDataContainer = ((TreeDataContainer) container);
            addParentsAndChildren(treeDataContainer);
        }
        sortListener();
        return filter;
    }

    protected DataContainer getContainer() {
        DataContainer container = null;
        TreeDataContainer treeContainer = null;

        if (tableIsNotNull()) {
            if (table.getContainerDataSource() instanceof TreeDataContainer) {
                treeContainer = ((TreeDataContainer) table.getContainerDataSource());
            } else {
                container = ((DataContainer) table.getContainerDataSource());
            }
        } else if (filterTableIsNotNull()) {
            if (filterTable.getContainerDataSource() instanceof TreeDataContainer) {
                treeContainer = ((TreeDataContainer) filterTable.getContainerDataSource());
            } else {
                container = ((DataContainer) filterTable.getContainerDataSource());
            }
        } else if (filterTreeTableIsNotNull()) {
            if (filterTreeTable.getContainerDataSource() instanceof TreeDataContainer)
                treeContainer = ((TreeDataContainer) filterTreeTable.getContainerDataSource());
        }

        return container != null ? container : treeContainer;
    }

    protected void removeFilter(Container.Filter filter) {
        DataContainer container = getContainer();

        assert container != null;
        container.removeContainerFilter(filter);

        if (container instanceof TreeDataContainer) {
            TreeDataContainer treeDataContainer = ((TreeDataContainer) container);
            addParentsAndChildren(treeDataContainer);
        }

        sortListener();
    }

    protected void addSort(Object sortedColumn, boolean sortedAscending) {
        sortColumn.add(sortedColumn);
        sortAscending.add(sortedAscending);
        sortable();
    }

    private void sortable() {
        if (sortColumn.size() > 0) {

            DataContainer container = getContainer();
            boolean[] booleanArray = new boolean[sortAscending.size()];
            for (int i = 0; i < sortAscending.size(); i++) {
                booleanArray[i] = sortAscending.get(i);
            }

            assert container != null;
            container.sort(sortColumn.toArray(), booleanArray);

            if (container instanceof TreeDataContainer) {
                TreeDataContainer treeDataContainer = (TreeDataContainer) container;

                if (treeDataContainer.getContainerFilters().size() > 0) {
                    addParentsAndChildren(treeDataContainer);
                }
            }
        }
        sortListener();
    }

    protected void removeSort(Object columnName) {
        int removeIndex = sortColumn.indexOf(columnName);
        sortAscending.remove(removeIndex);
        sortColumn.remove(removeIndex);
        DataContainer container = getContainer();

        assert container != null;
        container.sort(new Object[]{getNameIdentifyColumn()}, new boolean[]{true});

        if (container instanceof TreeDataContainer) {
            TreeDataContainer treeDataContainer = ((TreeDataContainer) container);
            addParentsAndChildren(treeDataContainer);
        }
        sortable();
    }

    public void show() {
        this.setVisible(true);
    }

    public void hide() {
        this.setVisible(false);
    }

    private boolean tableIsNotNull() {
        return table != null;
    }

    private boolean filterTableIsNotNull() {
        return filterTable != null;
    }

    private boolean filterTreeTableIsNotNull() {
        return filterTreeTable != null;
    }

    public abstract void addParentsAndChildren(TreeDataContainer treeDataContainer);

    public abstract Object getNameIdentifyColumn();// Нужно вернуть имя колонки с идентификаторами

    public abstract void sortListener();

    ComboBox getColumn() {
        return column;
    }

    CssLayout getFilters() {
        return filters;
    }

    List<Object> getSortColumn() {
        return sortColumn;
    }

    List<Boolean> getSortAscending() {
        return sortAscending;
    }
}

