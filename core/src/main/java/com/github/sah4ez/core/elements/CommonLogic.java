package com.github.sah4ez.core.elements;

import com.github.sah4ez.core.data.DataContainer;
import com.vaadin.navigator.View;
import com.vaadin.ui.CustomTable;

/**
 * Created by aleksandr on 20.12.16.
 */
abstract public class CommonLogic implements Logic {

    private View view;

    public CommonLogic(View view){
        this.view = view;
    }

    public View getView(){
        return this.view;
    }

    public void setDataToTable(DataContainer container, CustomTable table) {
        if (container == null || table == null) return;

        table.setContainerDataSource(container);
        table.setVisibleColumns(container.getCaption());
        table.setColumnHeaders(container.getHeaders());
        table.setColumnCollapsingAllowed(true);
        for (int i = 0; i < container.getCaption().length; i++) {
            table.setColumnCollapsed(container.getCaption()[i],
                    container.getVisible()[i].booleanValue());
        }
    }
}

