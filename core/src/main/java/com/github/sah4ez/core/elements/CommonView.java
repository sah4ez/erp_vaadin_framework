package com.github.sah4ez.core.elements;

import com.github.sah4ez.core.permission.ModifierAccess;
import com.github.sah4ez.core.permission.PermissionAccessUI;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.VerticalLayout;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by aleksandr on 20.12.16.
 */
public abstract class CommonView extends VerticalLayout implements View {
    private Map<String, ModifierAccess> modifiers = new HashMap<>();
    private Map<String, PermissionAccessUI> components = new HashMap<>();
    private Logic logic = null;

    public void setLogic(Logic logic) {
        this.logic = logic;
    }

    public Logic getLogic() {
        return logic;
    }

    public abstract Map<String, ModifierAccess> loadComponents();

    private void loadPermission(){
        Map<String, ModifierAccess> accessMap = loadComponents();
        accessMap.forEach(modifiers::replace);

        components.forEach(this::replaceComponent);
    }

    private void replaceComponent(String name, PermissionAccessUI accessUI){
        accessUI.replacePermissionAccess(modifiers.get(name));
    }

    public void addUI(PermissionAccessUI name) {
        try {
            components.put(name.getClass().toString(), name);
            modifiers.put(name.getClass().toString(), ModifierAccess.READ);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
        loadPermission();
    }
}
