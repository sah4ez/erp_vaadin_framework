package com.github.sah4ez.core.elements;

import com.github.sah4ez.core.data.DataContainer;
import com.github.sah4ez.core.permission.PermissionAccessUI;
import com.vaadin.ui.CustomTable;

/**
 * Created by aleksandr on 20.12.16.
 */
public interface Logic {
    void addUi(PermissionAccessUI ui);

    void setDataToTable(DataContainer container, CustomTable table);

    CommonView getView();
}
