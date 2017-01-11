package com.github.sah4ez.core.elements;

import com.github.sah4ez.core.data.CellCondition;
import com.github.sah4ez.core.data.Condition;
import com.github.sah4ez.core.data.DataContainer;
import com.vaadin.data.Item;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.ui.CustomTable;

import java.lang.reflect.Field;
import java.util.HashMap;

/**
 * Created by aleksandr on 06.01.17.
 */
abstract public class CrossTable extends Workspace {

    private DataContainer<?> firstContainer;
    private DataContainer<?> secondContainer;
    private String idFirst = "";
    private String idSecond = "";
    private String captionSecond = "";
    private String captionFirst = "";
    private SelectionMode selectionMode = SelectionMode.SINGLE_CELL;
    private HashMap<Item, HashMap<String, CellCondition>> selectedCell = new HashMap<>();

    public CrossTable(Logic logic, String identify, DataContainer<?> firstContainer, DataContainer<?> secondContainer) {
        super(logic, identify);
        this.firstContainer = firstContainer;
        this.secondContainer = secondContainer;
        this.getTable().setFilterBarVisible(false);
        getTable().setColumnCollapsingAllowed(true);
        getTable().addItemClickListener(selectionModeListener());
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

    public void createData(String idFirst, String captionFirst, String idSecond, String captionSecond) {
        this.idFirst = idFirst;
        this.captionFirst = captionFirst;
        this.idSecond = idSecond;
        this.captionSecond = captionSecond;
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

        if (getTable().getCellStyleGenerator() == null) {
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

    private CustomTable.CellStyleGenerator cellStyleGenerator() {
        return (CustomTable.CellStyleGenerator) (customTable, itemId, property) -> {
            if (customTable == null || itemId == null || property == null) return null;

            String result = null;
            Object value = customTable.getItem(itemId).getItemProperty(property).getValue();
            if (value != null && value instanceof CellCondition) {
                result = ((CellCondition) value).getCssStyle();
            }
            return result;
        };
    }

    public abstract CellCondition getCell(Object idRow, Object idColumn);

    public void setSelectionMode(SelectionMode mode) {
        selectionMode = mode;
    }

    public SelectionMode getSelectionMode() {
        return selectionMode;
    }

    private ItemClickEvent.ItemClickListener selectionModeListener() {
        return this::actionSelectionMode;
    }

    protected void actionSelectionMode(ItemClickEvent itemClickEvent) {
        CustomTable table = ((CustomTable) itemClickEvent.getSource());
        Item item = itemClickEvent.getItem();
        Object property = itemClickEvent.getPropertyId();


        if (table == null || item == null || property == null) return;

        if (!(item.getItemProperty(property).getValue() instanceof CellCondition)) return;

        switch (selectionMode) {
            case MULTI_CELL_IN_ROW: {
                if (selectedCell.containsKey(item) && selectedCell.get(item).containsKey(property)) {
                    CellCondition value = selectedCell.get(item).get(property);
                    item.getItemProperty(property).setValue(value);

                    selectedCell.get(item).remove(property);
                    if (selectedCell.get(item).isEmpty()) {
                        selectedCell.remove(item);
                    }
                } else if (selectedCell.containsKey(item)) {
                    selectedCell.get(item).put(property.toString(),
                            ((CellCondition) item.getItemProperty(property).getValue()));
                    item.getItemProperty(property).setValue(Condition.EDIT);
                } else if (selectedCell.isEmpty()) {
                    selectedCell.put(item, new HashMap<>());
                    selectedCell.get(item).put(property.toString(),
                            ((CellCondition) item.getItemProperty(property).getValue()));
                    item.getItemProperty(property).setValue(Condition.EDIT);
                }
                break;
            }
            case MULTI_CELL_IN_COLUMN: {
                if (selectedCell.containsKey(item) && selectedCell.get(item).containsKey(property)) {
                    CellCondition value = selectedCell.get(item).get(property);
                    item.getItemProperty(property).setValue(value);

                    selectedCell.get(item).remove(property);
                    if (selectedCell.get(item).isEmpty()) {
                        selectedCell.remove(item);
                    }
                } else if (!selectedCell.containsKey(item) && !selectedCell.isEmpty()) {
                    HashMap p = selectedCell.values().stream().findFirst().orElseGet(HashMap::new);
                    if (p.keySet().contains(property)){
                        selectedCell.put(item, new HashMap<>());
                        selectedCell.get(item).put(property.toString(),
                                ((CellCondition) item.getItemProperty(property).getValue()));
                        item.getItemProperty(property).setValue(Condition.EDIT);
                    }
                } else if (selectedCell.isEmpty()){
                    selectedCell.put(item, new HashMap<>());
                    selectedCell.get(item).put(property.toString(),
                            ((CellCondition) item.getItemProperty(property).getValue()));
                    item.getItemProperty(property).setValue(Condition.EDIT);
                }
                break;
            }
            case MULTI_CELL: {
                if (selectedCell.containsKey(item) && selectedCell.get(item).containsKey(property)) {
                    CellCondition value = selectedCell.get(item).get(property);
                    item.getItemProperty(property).setValue(value);

                    selectedCell.get(item).remove(property);
                    if (selectedCell.get(item).isEmpty()) {
                        selectedCell.remove(item);
                    }
                }else if (!selectedCell.containsKey(item) || selectedCell.isEmpty()) {
                    selectedCell.put(item, new HashMap<>());
                    selectedCell.get(item).put(property.toString(),
                            ((CellCondition) item.getItemProperty(property).getValue()));
                    item.getItemProperty(property).setValue(Condition.EDIT);
                } else {
                    selectedCell.get(item).put(property.toString(),
                            ((CellCondition) item.getItemProperty(property).getValue()));
                    item.getItemProperty(property).setValue(Condition.EDIT);
                }
                break;
            }
            default: {
                if (selectedCell.containsKey(item) && selectedCell.get(item).containsKey(property)) {
                    clearSelect();
                } else {
                    if (selectedCell.size() > 0) {
                        clearSelect();
                    }
                    selectedCell.put(item, new HashMap<>());
                    CellCondition condition = (CellCondition) item.getItemProperty(property).getValue();
                    selectedCell.get(item).put(property.toString(), condition);
                    item.getItemProperty(property).setValue(Condition.EDIT);
                }
                break;
            }
        }
    }

    private void clearSelect() {
        selectedCell.forEach((i, p) -> p.forEach((c, v) -> i.getItemProperty(c).setValue(v)));
        selectedCell.clear();
    }
}
