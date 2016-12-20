package com.sah4ez.permission;

import java.util.Map;

import static com.sah4ez.permission.ModiferAccess.*;

/**
 * Created by aleksandr on 20.12.16.
 */
public class PermissionAccess {
    public static ModiferAccess getPermissionAccess(Integer id) {
        switch (id) {
            case 1: {
                return EDIT;
            }
            case 2: {
                return READ;
            }
            case 3: {
                return HIDE;
            }
            default: {
                return HIDE;
            }
        }
    }

    public static void replacePermissionAccess(PermissionAccessUI component, ModiferAccess newValue) {
        switch (component.getPermissionAccess()) {
            case EDIT: {
                if (newValue.equals(HIDE) || newValue.equals(READ)) break;
                component.setPermissionAccess(newValue);
                break;
            }
            case READ: {
                if (newValue.equals(HIDE)) break;
                component.setPermissionAccess(newValue);
                break;
            }
            case HIDE: {
                component.setPermissionAccess(newValue);
                break;
            }
        }
    }

    public static void replacePermissionAccess(Map<String, ModiferAccess > map, String name, ModiferAccess  newValue) {
        switch (map.get(name)) {
            case EDIT: {
                if (newValue.equals(HIDE) || newValue.equals(READ)) break;
                map.replace(name, newValue);
                break;
            }
            case READ: {
                if (newValue.equals(HIDE)) break;
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
