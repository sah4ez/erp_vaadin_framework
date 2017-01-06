package com.github.sah4ez.core.elements;

import com.github.sah4ez.core.data.DataContainer;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.ui.CustomTable;

/**
 * Created by aleksandr on 06.01.17.
 */
abstract public class CrossTable extends Workspace{

    private DataContainer<?> firstContainer;
    private DataContainer<?> secondContainer;
    private String idFirst = "";
    private String idSecond = "";
    private String stateFirstEntity = "";
    private String captionSecond = "";
    private String captionFirst = "";

    public CrossTable(Logic logic, String identify, DataContainer<?> firstContainer, DataContainer<?> secondContainer){
        super(logic, identify);
        this.firstContainer = firstContainer;
        this.secondContainer = secondContainer;
    }

    @Override
    protected ItemClickEvent.ItemClickListener editTableItemClick() {
        return itemClickEvent -> {};
    }

    @Override
    protected ItemClickEvent.ItemClickListener selectTableItemClick() {
        return itemClickEvent -> {};
    }

    @Override
    protected ItemClickEvent.ItemClickListener editTableAllItemClick() {
        return itemClickEvent -> {};
    }

    @Override
    protected ItemClickEvent.ItemClickListener selectTableAllItemClick() {
        return itemClickEvent -> {};
    }

    public void setFirstContainer(DataContainer<?> firstContainer) {
        this.firstContainer = firstContainer;
    }

    public DataContainer<?> getFirstContainer() {
        return firstContainer;
    }

    public void setSecondContainer(DataContainer<?> secondContainer) {
        this.secondContainer = secondContainer;
    }

    public DataContainer<?> getSecondContainer() {
        return secondContainer;
    }

    public void createData(String idFirst, String captionFirst, String idSecond, String captionSecond, String stateFirstEntity) {
        this.idFirst = idFirst;
        this.captionFirst = captionFirst;
        this.idSecond = idSecond;
        this.captionSecond = captionSecond;
        this.stateFirstEntity = stateFirstEntity;
        CustomTable table = getTable();


    }

    private void initRows(CustomTable table){

    }
}
