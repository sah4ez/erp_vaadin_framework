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
    private SelectionModeCrossTable selectionModeCrossTable = SelectionModeCrossTable.SINGLE_CELL;
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

    public void setSelectionModeCrossTable(SelectionModeCrossTable mode) {
        selectionModeCrossTable = mode;
    }

    public SelectionModeCrossTable getSelectionModeCrossTable() {
        return selectionModeCrossTable;
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

        switch (selectionModeCrossTable) {
            case MULTI_CELL_IN_ROW: {
                multiCellInRowAction(item, property);
                break;
            }
            case MULTI_CELL_IN_COLUMN: {
                multiCellInColumnAction(item, property);
                break;
            }
            case MULTI_CELL: {
                multiCellAction(item, property);
                break;
            }
            default: {
                singleAction(item, property);
                break;
            }
        }
    }

    private void multiCellInRowAction(Item item, Object property){
        if (contains(item, property)) {
            setPrevValue(item, property);
            remove(item, property);
        } else if (selectedCell.containsKey(item)) {
            saveCurrentValue(item, property);
        } else if (selectedCell.isEmpty()) {
            addItemToSelected(item, property);
        }
    }

    private void multiCellInColumnAction(Item item, Object property){
        if (contains(item, property)) {
            setPrevValue(item, property);
            remove(item, property);
        } else if (!selectedCell.containsKey(item) && !selectedCell.isEmpty()) {
            HashMap p = selectedCell.values().stream().findFirst().orElseGet(HashMap::new);
            if (p.keySet().contains(property)){
                addItemToSelected(item, property);
            }
        } else if (selectedCell.isEmpty()){
            addItemToSelected(item, property);
        }
    }

    private void multiCellAction(Item item, Object property){
        if (contains(item, property)) {
            setPrevValue(item, property);
            remove(item, property);
        }else if (!selectedCell.containsKey(item) || selectedCell.isEmpty()) {
            addItemToSelected(item, property);
        } else {
            saveCurrentValue(item, property);
        }
    }

    private void singleAction(Item item, Object property){
        if (contains(item, property)) {
            clearSelect();
        } else {
            if (selectedCell.size() > 0) {
                clearSelect();
            }
            addItemToSelected(item, property);
        }
    }

    private boolean contains(Item item, Object property){
        return selectedCell.containsKey(item) && selectedCell.get(item).containsKey(property);
    }

    private void remove(Item item, Object property){
        selectedCell.get(item).remove(property);
        if (selectedCell.get(item).isEmpty()) {
            selectedCell.remove(item);
        }
    }

    private void setPrevValue(Item item, Object property){
        CellCondition value = selectedCell.get(item).get(property);
        item.getItemProperty(property).setValue(value);
    }

    private void saveCurrentValue(Item item, Object property){
        selectedCell.get(item).put(property.toString(),
                ((CellCondition) item.getItemProperty(property).getValue()));
        item.getItemProperty(property).setValue(Condition.EDIT);
    }

    private void addItemToSelected(Item item, Object property){
        selectedCell.put(item, new HashMap<>());
        saveCurrentValue(item, property);
    }

    private void clearSelect() {
        selectedCell.forEach((i, p) -> p.forEach((c, v) -> i.getItemProperty(c).setValue(v)));
        selectedCell.clear();
    }
}
