package com.sah4ez.elements;

import com.sah4ez.permission.ModiferAccess;
import com.sah4ez.permission.PermissionAccess;
import com.sah4ez.permission.PermissionAccessUI;
import com.vaadin.server.Resource;
import com.vaadin.ui.Component;
import com.vaadin.ui.TabSheet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * Created by aleksandr on 20.12.16.
 */
abstract public class BottomTabs extends TabSheet implements PermissionAccessUI {

    private ModiferAccess permissionAccess = ModiferAccess.HIDE;
    private final Logic logic;
    private final List<String> captions = new ArrayList<>();
    private final List<Component> components = new ArrayList<>();
    private final List<Resource> resources = new ArrayList<>();

    public BottomTabs(Logic logic) {
        this.logic = logic;
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

