package com.sah4ez.elements;

import com.sah4ez.data.DataContainer;
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

    public void setDataToTable(DataContainer container, Object table) {

        if (table instanceof CustomTable) {
            CustomTable tmpTable = ((CustomTable) table);
            tmpTable.setContainerDataSource(container);
            tmpTable.setVisibleColumns(container.getCaption());
            tmpTable.setColumnHeaders(container.getHeaders());
            tmpTable.setColumnCollapsingAllowed(true);
            for (int i = 0; i < container.getCaption().length; i++) {
                tmpTable.setColumnCollapsed(container.getCaption()[i],
                        container.getVisible()[i].booleanValue());
            }
        }
    }
}

