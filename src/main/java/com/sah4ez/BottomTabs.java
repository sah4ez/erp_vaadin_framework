package com.sah4ez;

import com.sah4ez.permission.ModiferAccess;
import com.sah4ez.permission.PermissionAccess;
import com.sah4ez.permission.PermissionAccessUI;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Component;
import com.vaadin.ui.TabSheet;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by aleksandr on 20.12.16.
 */
abstract public class BottomTabs extends TabSheet implements PermissionAccessUI {

    public ModiferAccess permissionAccess = ModiferAccess.HIDE;
    public final Logic logic;
    public final List<String> caption = new ArrayList<>();
    public final List<Component> components = new ArrayList<>();
    public final List<ThemeResource> resources = new ArrayList<>();

    public BottomTabs(Logic logic) {
        this.logic = logic;
        caption.removeAll(caption);
        components.removeAll(components);
        resources.removeAll(resources);
        setSizeFull();

    }

    public void init() {
        for (int i = 0; i < this.components.size(); i++) {
            if (i < resources.size() && i < caption.size()) {
                this.addTab(this.components.get(i)
                        , this.caption.get(i)
                        , this.resources.get(i));
            }
        }
    }

    public int getSelecteTabIndex() {
        return this.getTabPosition(this.getTab(this.getSelectedTab()));
    }

    public void setPermissionAccess(ModiferAccess permission) {
        this.permissionAccess = permission;
        components.forEach(component -> {
            if (component instanceof PermissionAccessUI) {
                ((PermissionAccessUI) component).replacePermissionAccess(permission);
            }
        });
    }

    public void replacePermissionAccess(ModiferAccess permissionAccess) {
        PermissionAccess.replacePermissionAccess(this, permissionAccess);
    }

    public ModiferAccess getPermissionAccess() {
        return this.permissionAccess;
    }
}

