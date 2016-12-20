package com.sah4ez.permission;

/**
 * Created by aleksandr on 20.12.16.
 */
public interface PermissionAccessUI {

    void setPermissionAccess(ModiferAccess permission);

    void replacePermissionAccess(ModiferAccess permissionAccess);

    ModiferAccess getPermissionAccess();

}

