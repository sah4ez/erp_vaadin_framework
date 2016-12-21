package com.github.sah4ez.core.elements;

import com.github.sah4ez.core.data.DataContainer;
import com.github.sah4ez.core.data.TreeBeanContainer;
import com.vaadin.data.Container;
import com.vaadin.data.util.filter.SimpleStringFilter;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.tepi.filtertable.FilterTable;
import org.tepi.filtertable.FilterTreeTable;

import java.util.ArrayList;
import java.util.Iterator;
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
    private FilterTable fitlerTable;
    private FilterTreeTable filterTreeTable;
    private CssLayout filters;
    private HorizontalLayout components;
    private List<Object> sortColumn = new ArrayList<>();
    private List<Boolean> sortAscending = new ArrayList<>();

    public FilterPanel(Table table) {
        this.table = table;
        this.fitlerTable = null;
        this.filterTreeTable = null;
        init();
        //addStyleName("blacklayout");
    }

    public FilterPanel(FilterTable table) {
        this.fitlerTable = table;
        this.table = null;
        this.filterTreeTable = null;
        init();
        //addStyleName("blacklayout");
    }

    public FilterPanel(FilterTreeTable filterTreeTable) {
        this.filterTreeTable = filterTreeTable;
        this.fitlerTable = null;
        this.table = null;
        init();
    }

    private void init() {
        addComponent(initFilters());
        addComponent(initComponents());
        setWidth("100%");
        setVisible(false);
    }

    public FilterPanel addComponents() {
        removeAllComponents();
        addComponent(initFilters());
        addComponent(initComponents());
        return this;
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

    private Component initAdd() {
        add = new Button("Добавить");
        add.addClickListener(event -> {
            if (!filters.isVisible())
                filters.setVisible(true);
            if (column.getValue() == null) return;
            Button btn = new Button(column.getItemCaption(column.getValue()) + " содержит: " + text.getValue() + " x");
            btn.setStyleName(ValoTheme.BUTTON_QUIET);
            filters.addComponent(btn);
            Container.Filter filter = addFilter(column.getValue(), text.getValue());
            btn.addClickListener(btnClick -> {
                filters.removeComponent(btn);
                if (filters.getComponentCount() == 0)
                    filters.setVisible(false);
                removeFilter(filter);
            });


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

            Button btnDesc = new Button(column.getItemCaption(column.getValue()) + ": по убыванию x");
            btnDesc.setStyleName(ValoTheme.BUTTON_QUIET);

            filters.addComponent(btnDesc);

            Object columnValue = column.getValue();
            addSort(columnValue, false);
            btnDesc.addClickListener(btnClick -> {
                filters.removeComponent(btnDesc);
                if (filters.getComponentCount() == 0)
                    filters.setVisible(false);
                removeSort(columnValue);
            });
        });
        return down;
    }

    private Button initUp() {
        up = new Button("По возрастанию");
        up.setStyleName(ValoTheme.BUTTON_LINK);
        up.addClickListener(upClick -> {
            if (!filters.isVisible())
                filters.setVisible(true);

            Button btnAsc = new Button(column.getItemCaption(column.getValue()) + ": по возрастанию x");
            btnAsc.setStyleName(ValoTheme.BUTTON_QUIET);
            filters.addComponent(btnAsc);
            Object columnValue = column.getValue();
            addSort(columnValue, true);
            btnAsc.addClickListener(btnClick -> {
                filters.removeComponent(btnAsc);
                if (filters.getComponentCount() == 0)
                    filters.setVisible(false);
                removeSort(columnValue);
            });
        });
        return up;
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

    public void loadColumns(){
        if (table != null) column.addItems(table.getVisibleColumns());
        if (fitlerTable != null) column.addItems(fitlerTable.getVisibleColumns());
        if (filterTreeTable != null) column.addItems(filterTreeTable.getVisibleColumns());

        Iterator iterator = column.getContainerDataSource().getItemIds().iterator();
        while (iterator.hasNext()) {
            Object itemId = iterator.next();
            if (table != null) column.setItemCaption(itemId, table.getColumnHeader(itemId));
            if (fitlerTable != null) column.setItemCaption(itemId, fitlerTable.getColumnHeader(itemId));
            if (filterTreeTable != null) column.setItemCaption(itemId, filterTreeTable.getColumnHeader(itemId));
        }
    }

    public void removeAllFilters() {
        //TODO Реализовать удаление всех фильров
    }

    private Container.Filter addFilter(Object value, String textValue) {

        beforeFilter();
        Container.Filter filter = new SimpleStringFilter(value, textValue, true, false);
        if (table != null && table.getContainerDataSource() instanceof TreeBeanContainer) {
            TreeBeanContainer treeBeanContainer = ((TreeBeanContainer) table.getContainerDataSource());
            treeBeanContainer.addContainerFilter(filter);
            addParentsAndChildren(treeBeanContainer);
        }
        if (fitlerTable != null && fitlerTable.getContainerDataSource() instanceof DataContainer) {
            DataContainer abstractDataContainer = ((DataContainer) fitlerTable.getContainerDataSource());
            abstractDataContainer.addContainerFilter(filter);
        }
        if (filterTreeTable != null && filterTreeTable.getContainerDataSource() instanceof Container.Hierarchical) {
            TreeBeanContainer treeBeanContainer = ((TreeBeanContainer) filterTreeTable.getContainerDataSource());
            treeBeanContainer.addContainerFilter(filter);
            addParentsAndChildren(treeBeanContainer);
        }
        sortListener();
        return filter;
    }

    private void removeFilter(Container.Filter filter) {
        TreeBeanContainer treeBeanContainer = null;
        if ((table != null && table.getContainerDataSource() instanceof TreeBeanContainer) ||
                (fitlerTable != null && fitlerTable.getContainerDataSource() instanceof TreeBeanContainer)) {
            if (table != null)
                treeBeanContainer = ((TreeBeanContainer) table.getContainerDataSource());

            if (fitlerTable != null)
                treeBeanContainer = ((TreeBeanContainer) fitlerTable.getContainerDataSource());

            if (treeBeanContainer == null) return;
            treeBeanContainer.removeContainerFilter(filter);
            addParentsAndChildren(treeBeanContainer);
        }
//        if (fitlerTable != null && fitlerTable.getContainerDataSource() instanceof TreeBeanContainer) {
//            treeBeanContainer = ((TreeBeanContainer) fitlerTable.getContainerDataSource());
//            if (treeBeanContainer == null) return;
//            treeBeanContainer.removeContainerFilter(filter);
//            addParentsAndChildren(treeBeanContainer);
//        }
        if (fitlerTable != null && fitlerTable.getContainerDataSource() instanceof DataContainer) {
            DataContainer abstractDataContainer = ((DataContainer) fitlerTable.getContainerDataSource());
            if (abstractDataContainer == null) return;
            abstractDataContainer.removeContainerFilter(filter);
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
            TreeBeanContainer treBeanContainer = null;
            if (table != null && table.getContainerDataSource() instanceof TreeBeanContainer) {
                treBeanContainer = ((TreeBeanContainer) table.getContainerDataSource());
                if (treBeanContainer != null) {
                    boolean[] booleanArray = new boolean[sortAscending.size()];
                    for (int i = 0; i < sortAscending.size(); i++) {
                        booleanArray[i] = sortAscending.get(i);
                    }
                    treBeanContainer.sort(sortColumn.toArray(), booleanArray);
                    if (treBeanContainer.getContainerFilters().size() > 0) {
                        addParentsAndChildren(treBeanContainer);
                    }
                }
            }
            if (fitlerTable != null && fitlerTable.getContainerDataSource() instanceof DataContainer) {
                DataContainer abstractDataContainer
                        = ((DataContainer) fitlerTable.getContainerDataSource());
                if (abstractDataContainer != null) {
                    boolean[] booleanArray = new boolean[sortAscending.size()];
                    for (int i = 0; i < sortAscending.size(); i++) {
                        booleanArray[i] = sortAscending.get(i);
                    }
                    abstractDataContainer.sort(sortColumn.toArray(), booleanArray);
//                    if (abstractDataContainer.getContainerFilters().size() > 0) {
//                        addParentsAndChildren(treBeanContainer);
//                    }
//                    abstractDataContainer.sort(sortColumn.toArray(), booleanArray);
                }
            }
            if (filterTreeTable != null && filterTreeTable.getContainerDataSource() instanceof TreeBeanContainer) {
                treBeanContainer = ((TreeBeanContainer) filterTreeTable.getContainerDataSource());
                if (treBeanContainer != null) {
                    boolean[] booleanArray = new boolean[sortAscending.size()];
                    for (int i = 0; i < sortAscending.size(); i++) {
                        booleanArray[i] = sortAscending.get(i);
                    }
                    treBeanContainer.sort(sortColumn.toArray(), booleanArray);
                    if (treBeanContainer.getContainerFilters().size() > 0) {
                        addParentsAndChildren(treBeanContainer);
                    }
                    treBeanContainer.sort(sortColumn.toArray(), booleanArray);
                }
            }
        }
        sortListener();
    }

    private void removeSort(Object columnName) {
        int removeIndex = sortColumn.indexOf(columnName);
        sortAscending.remove(removeIndex);
        sortColumn.remove(removeIndex);
        TreeBeanContainer treeBeanContainer = null;
        if ((table != null && table.getContainerDataSource() instanceof TreeBeanContainer)
                || (filterTreeTable != null && filterTreeTable.getContainerDataSource() instanceof TreeBeanContainer)) {
            if (table != null)
                treeBeanContainer = ((TreeBeanContainer) table.getContainerDataSource());
            if (filterTreeTable != null)
                treeBeanContainer = ((TreeBeanContainer) filterTreeTable.getContainerDataSource());
            if (treeBeanContainer != null) {
                treeBeanContainer.sort(new Object[]{getNameIdentifyColumn()}, new boolean[]{true});
                addParentsAndChildren(treeBeanContainer);
                sortable();
            }
        }
        if (fitlerTable != null && fitlerTable.getContainerDataSource() instanceof DataContainer) {
            DataContainer abstractDataContainer = ((DataContainer) fitlerTable.getContainerDataSource());
            if (abstractDataContainer != null) {
                abstractDataContainer.sort(new Object[]{getNameIdentifyColumn()}, new boolean[]{true});
                sortable();
            }
        }

    }

    public void show(){
        this.setVisible(true);
    }

    public void hide(){
        this.setVisible(false);
    }

    public abstract void addParentsAndChildren(TreeBeanContainer treeBeanContainer);

    public abstract Object getNameIdentifyColumn();// Нужно вернуть имя колонки с идентификаторами

    public abstract void sortListener();

    public abstract void beforeFilter();
}

