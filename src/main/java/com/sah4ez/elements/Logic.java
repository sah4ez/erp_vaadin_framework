package com.sah4ez.elements;

import com.sah4ez.data.DataContainer;
import com.sah4ez.permission.PermissionAccessUI;

/**
 * Created by aleksandr on 20.12.16.
 */
public interface Logic {
    void addUi(PermissionAccessUI ui);

    void setDataToTable(DataContainer container, Object table);
}
