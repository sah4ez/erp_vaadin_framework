package com.sah4ez.elements;

import com.sah4ez.permission.ModiferAccess;
import com.sah4ez.permission.PermissionAccessUI;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by aleksandr on 20.12.16.
 */
public abstract class CommonView implements View {
    private Map<String, ModiferAccess> modifers = new HashMap<>();
    private Map<String, PermissionAccessUI> components = new HashMap<>();
    private Logic logic = null;

    abstract void setLogic(Logic logic);

    public abstract Map<String, ModiferAccess> loadComponents();

    private void loadPermission(){
        Map<String, ModiferAccess> accessMap = loadComponents();
        accessMap.forEach(modifers::replace);

        components.forEach(this::replaceComponent);
    }

    private void replaceComponent(String name, PermissionAccessUI accessUI){
        accessUI.replacePermissionAccess(modifers.get(name));
    }

    public void addUI(PermissionAccessUI name) {
        try {
            components.put(name.getClass().toString(), name);
            modifers.put(name.getClass().toString(), ModiferAccess.READ);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
        loadPermission();
    }
}
