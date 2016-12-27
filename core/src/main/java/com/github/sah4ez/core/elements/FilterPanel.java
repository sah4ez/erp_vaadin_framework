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
    private Button up;
    private Button down;
    private TextField text;
    private Button add;
    private Table table;
    private FilterTable filterTable;
    private FilterTreeTable filterTreeTable;
    private AbstractSelect select;
    private CssLayout filters;
    private HorizontalLayout components;
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
        components = new HorizontalLayout();
        components.addComponent(initColumn());
        components.addComponent(initUp());
        components.addComponent(initDown());
        components.addComponent(initText());
        components.addComponent(initAdd());
        components.setSpacing(true);
        components.setMargin(true);
        return components;
    }

    private Button filterButton(String caption) {
        Button btn = new Button(column.getItemCaption(column.getValue()) +
                " " + caption + " x");
        btn.setStyleName(ValoTheme.BUTTON_QUIET);

        filters.addComponent(btn);
        return btn;
    }

    private Component initAdd() {
        add = new Button("Добавить");
        add.addClickListener(event -> {

            if (!filters.isVisible())
                filters.setVisible(true);

            if (column.getValue() == null) return;

            Button btn = filterButton(" содержит: " + text.getValue());

            Container.Filter filter = addFilter(column.getValue(), text.getValue());

            btn.addClickListener(removeFilter(btn, filter));

        });
        add.setEnabled(false);
        return add;
    }

    private TextField initText() {
        text = new TextField();
        text.setWidth("220px");
        text.setInputPrompt("Содержит");
        return text;
    }

    private Button initDown() {
        down = new Button("По убыванию");
        down.setStyleName(ValoTheme.BUTTON_LINK);
        down.addClickListener(upClick -> {
            if (!filters.isVisible())
                filters.setVisible(true);

            Button btnDesc = filterButton(": по убыванию x");

            Object columnValue = column.getValue();
            addSort(columnValue, false);
            btnDesc.addClickListener(removeSort(btnDesc, columnValue));
        });
        return down;
    }

    private Button initUp() {
        up = new Button("По возрастанию");
        up.setStyleName(ValoTheme.BUTTON_LINK);
        up.addClickListener(upClick -> {
            if (!filters.isVisible())
                filters.setVisible(true);

            Button btnAsc = filterButton(": по возрастанию x");

            Object columnValue = column.getValue();
            addSort(columnValue, true);
            btnAsc.addClickListener(removeSort(btnAsc, columnValue));
        });
        return up;
    }

    private Button.ClickListener removeSort(Button btn, Object column) {
        return clickEvent -> {
            filters.removeComponent(btn);
            if (filters.getComponentCount() == 0)
                filters.setVisible(false);
            removeSort(column);
        };
    }

    private Button.ClickListener removeFilter(Button btn, Container.Filter column) {
        return clickEvent -> {
            filters.removeComponent(btn);
            if (filters.getComponentCount() == 0)
                filters.setVisible(false);
            removeFilter(column);
        };
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

    public void removeAllFilters() {
        //TODO Реализовать удаление всех фильров
    }

    private Container.Filter addFilter(Object value, String textValue) {

        beforeFilter();
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

    private DataContainer getContainer() {
        if (tableIsNotNull()) {
            return (DataContainer) table.getContainerDataSource();
        } else if (filterTableIsNotNull()) {
            return (TreeDataContainer) filterTable.getContainerDataSource();
        } else if (filterTreeTableIsNotNull()) {
            return (TreeDataContainer) filterTreeTable.getContainerDataSource();
        }
        return null;
    }

    private void removeFilter(Container.Filter filter) {
        DataContainer container = getContainer();

        assert container != null;
        container.removeContainerFilter(filter);

        if (container instanceof TreeDataContainer) {
            TreeDataContainer treeDataContainer = ((TreeDataContainer) container);
            addParentsAndChildren(treeDataContainer);
        }

        sortListener();
    }

    private void addSort(Object sortedColumn, boolean sortedAscending) {
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

    private void removeSort(Object columnName) {
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
        return table != null && table.getContainerDataSource() instanceof TreeDataContainer;
    }

    private boolean filterTableIsNotNull() {
        return filterTable != null && filterTable.getContainerDataSource() instanceof DataContainer;
    }

    private boolean filterTreeTableIsNotNull() {
        return filterTreeTable != null && filterTreeTable.getContainerDataSource() instanceof TreeDataContainer;
    }

    public abstract void addParentsAndChildren(TreeDataContainer treeDataContainer);

    public abstract Object getNameIdentifyColumn();// Нужно вернуть имя колонки с идентификаторами

    public abstract void sortListener();

    public abstract void beforeFilter();
}

