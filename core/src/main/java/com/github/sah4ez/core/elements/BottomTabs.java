package com.github.sah4ez.core.elements;

/**
 * Created by aleksandr on 20.12.16.
 */
import com.github.sah4ez.core.elements.Logic;
import com.github.sah4ez.core.permission.ModifierAccess;
import com.github.sah4ez.core.permission.PermissionAccess;
import com.github.sah4ez.core.permission.PermissionAccessUI;
import com.vaadin.server.Resource;
import com.vaadin.ui.Component;
import com.vaadin.ui.TabSheet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

abstract public class BottomTabs extends TabSheet implements PermissionAccessUI {

    private ModifierAccess permissionAccess = ModifierAccess.HIDE;
    private final List<String> captions = new ArrayList<>();
    private final List<Component> components = new ArrayList<>();
    private final List<Resource> resources = new ArrayList<>();
    private String identify = "";
    private Logic logic;

    public BottomTabs(Logic logic, String identify) {
        this.logic = logic;
        setIdentify(identify);
        captions.removeAll(captions);
        components.removeAll(components);
        resources.removeAll(resources);
        setSizeFull();
        init();

    }

    private void init() {
        initTabs();
        for (int i = 0; i < this.components.size(); i++) {
            if (i < resources.size() && i < captions.size()) {
                this.addTab(this.components.get(i)
                        , this.captions.get(i)
                        , this.resources.get(i));
            }
        }
    }

    @Override
    public void setIdentify(String identify) {
        this.identify = identify;
    }

    @Override
    public String getIdentify() {
        return identify;
    }

    public void addCaption(String ... args){
        Arrays.stream(args).forEach(this::addCaption);
    }

    public void addCaption(String caption){
        captions.add(caption);
    }

    public void addComponent(Component ... args){
        Arrays.stream(args).forEach(this::addComponent);
    }

    public void addComponent(Component component){
        components.add(component);
    }

    public void addResource(Resource... args){
        Arrays.stream(args).forEach(this::addResource);
    }

    public void addResource(Resource resource){
        resources.add(resource);
    }
    public abstract void initTabs();

    public int getSelecteTabIndex() {
        return this.getTabPosition(this.getTab(this.getSelectedTab()));
    }

    public void setPermissionAccess(ModifierAccess permission) {
        this.permissionAccess = permission;
        components.forEach(component -> {
            if (component instanceof PermissionAccessUI) {
                ((PermissionAccessUI) component).replacePermissionAccess(permission);
            }
        });
    }

    public void replacePermissionAccess(ModifierAccess permissionAccess) {
        PermissionAccess.replacePermissionAccess(this, permissionAccess);
    }

    public ModifierAccess getModifierAccess() {
        return this.permissionAccess;
    }

    public Logic getLogic() {
        return logic;
    }
}

