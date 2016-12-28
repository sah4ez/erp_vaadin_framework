package com.github.sah4ez.core.permission;


/**
 * Created by aleksandr on 20.12.16.
 */
public interface PermissionAccessUI {

    void setModifierAccess(ModifierAccess access);

    void replacePermissionAccess(ModifierAccess access);

    ModifierAccess getModifierAccess();

    void setIdentify(String identify);

    String getIdentify();
}

