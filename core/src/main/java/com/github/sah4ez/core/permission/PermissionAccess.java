package com.github.sah4ez.core.permission;

import java.util.Map;

/**
 * Created by aleksandr on 20.12.16.
 */
public final class PermissionAccess {
    public static ModifierAccess getModifierAccess(Integer id) {
        switch (id) {
            case 1: {
                return ModifierAccess.EDIT;
            }
            case 2: {
                return ModifierAccess.READ;
            }
            case 3: {
                return ModifierAccess.HIDE;
            }
            default: {
                return ModifierAccess.HIDE;
            }
        }
    }

    public static void replacePermissionAccess(PermissionAccessUI component, ModifierAccess newValue) {
        switch (component.getModifierAccess()) {
            case EDIT: {
                if (newValue.equals(ModifierAccess.HIDE) || newValue.equals(ModifierAccess.READ)) break;
                component.setPermissionAccess(newValue);
                break;
            }
            case READ: {
                if (newValue.equals(ModifierAccess.HIDE)) break;
                component.setPermissionAccess(newValue);
                break;
            }
            case HIDE: {
                component.setPermissionAccess(newValue);
                break;
            }
        }
    }

    public static void replacePermissionAccess(Map<String, ModifierAccess> map, String name, ModifierAccess newValue) {
        switch (map.get(name)) {
            case EDIT: {
                if (newValue.equals(ModifierAccess.HIDE) || newValue.equals(ModifierAccess.READ)) break;
                map.replace(name, newValue);
                break;
            }
            case READ: {
                if (newValue.equals(ModifierAccess.HIDE)) break;
                map.replace(name, newValue);
                break;
            }
            case HIDE: {
                map.replace(name, newValue);
                break;
            }
        }
    }
}
