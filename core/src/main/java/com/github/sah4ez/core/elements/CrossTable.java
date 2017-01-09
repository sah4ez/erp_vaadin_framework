package com.github.sah4ez.core.elements;

import com.github.sah4ez.core.data.CellCondition;
import com.github.sah4ez.core.data.DataContainer;
import com.vaadin.data.Item;
import com.vaadin.ui.CustomTable;

import java.lang.reflect.Field;

/**
 * Created by aleksandr on 06.01.17.
 */
abstract public class CrossTable extends Workspace {

    private DataContainer<?> firstContainer;
    private DataContainer<?> secondContainer;
    private String idFirst = "";
    private String idSecond = "";
    private String valueProperty = "";
    private String captionSecond = "";
    private String captionFirst = "";

    public CrossTable(Logic logic, String identify, DataContainer<?> firstContainer, DataContainer<?> secondContainer) {
        super(logic, identify);
        this.firstContainer = firstContainer;
        this.secondContainer = secondContainer;
        this.getTable().setFilterBarVisible(false);
    }

    public DataContainer<?> getFirstContainer() {
        return firstContainer;
    }

    public void setFirstContainer(DataContainer<?> firstContainer) {
        this.firstContainer = firstContainer;
    }

    public DataContainer<?> getSecondContainer() {
        return secondContainer;
    }

    public void setSecondContainer(DataContainer<?> secondContainer) {
        this.secondContainer = secondContainer;
    }

    public void createData(String idFirst, String captionFirst, String idSecond, String captionSecond, String valueProperty) {
        this.idFirst = idFirst;
        this.captionFirst = captionFirst;
        this.idSecond = idSecond;
        this.captionSecond = captionSecond;
        this.valueProperty = valueProperty;
        CustomTable table = getTable();

        table.setSelectable(false);
        table.setImmediate(true);
        table.setNullSelectionAllowed(true);
        table.setSortEnabled(false);
        table.setSizeFull();

        table.addContainerProperty(idFirst,
                Object.class,
                "0",
                "ID",
                null,
                CustomTable.Align.CENTER);
        table.addContainerProperty(captionFirst,
                Object.class,
                "0",
                "Название",
                null,
                CustomTable.Align.CENTER);

        table.removeAllItems();

        initColumns();

        initRows();

        if (getTable().getCellStyleGenerator() == null){
            getTable().setCellStyleGenerator(cellStyleGenerator());
        }
    }

    private void initColumns() {
        CustomTable table = getTable();
        if (secondContainer.size() == 0) secondContainer.loadAllData();
        secondContainer.stream().forEach(o -> {
            try {
                Field id = o.getClass().getDeclaredField(idSecond);
                Field caption = o.getClass().getDeclaredField(captionSecond);
                id.setAccessible(true);
                caption.setAccessible(true);
                table.addContainerProperty(id.get(o).toString(),
                        Object.class,
                        "0",
                        caption.get(o).toString(),
                        null,
                        CustomTable.Align.CENTER);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }
        });
    }

    private void initRows() {
        CustomTable table = getTable();
        if (firstContainer.size() == 0) firstContainer.loadAllData();

        if (secondContainer.size() == 0) secondContainer.loadAllData();

        firstContainer.stream().forEach(o -> {
            Object getItemId = table.addItem();
            Item row = table.getItem(getItemId);
            String id = getValueProperty(o, idFirst);

            row.getItemProperty(idFirst).setValue(id);
            row.getItemProperty(captionFirst).setValue(getValueProperty(o, captionFirst));

            row.getItemPropertyIds().stream().skip(2)
                    .forEach(s -> row.getItemProperty(s).setValue(getCell(id, s)));

        });
    }

    private String getValueProperty(Object object, String property) {
        String result = "";
        try {
            Field field = object.getClass().getDeclaredField(property);
            field.setAccessible(true);
            result = field.get(object).toString();
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return result;
    }

    private CustomTable.CellStyleGenerator cellStyleGenerator(){
        return (CustomTable.CellStyleGenerator) (customTable, itemId, property) -> {
            if (customTable == null || itemId == null || property == null) return null;

            String result = null;
            Object value = customTable.getItem(itemId).getItemProperty(property).getValue();
            if (value != null && value instanceof CellCondition){
                result = ((CellCondition) value).getCssStyle();
            }
            return result;
        };
    }

    public abstract CellCondition getCell(Object idRow, Object idColumn);
}
