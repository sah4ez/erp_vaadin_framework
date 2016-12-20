package com.sah4ez.permission;

/**
 * Created by aleksandr on 20.12.16.
 */
public interface PermissionAccessUI {

    void setPermissionAccess(ModifierAccess permission);

    void replacePermissionAccess(ModifierAccess permissionAccess);

    ModifierAccess getModifierAccess();

}

