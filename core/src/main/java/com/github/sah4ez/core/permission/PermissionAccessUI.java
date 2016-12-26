package com.github.sah4ez.core.permission;

import com.sun.istack.internal.NotNull;

/**
 * Created by aleksandr on 20.12.16.
 */
public interface PermissionAccessUI {

    void setPermissionAccess(ModifierAccess access);

    void replacePermissionAccess(ModifierAccess access);

    ModifierAccess getModifierAccess();

    void setIdentify(@NotNull String identify);

    String getIdentify();
}

