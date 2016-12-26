package com.github.sah4ez.core.permission;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by aleksandr on 26.12.16.
 */
public class PermissionAccessTest extends Assert {
    private TestComponent component;
    private Map<String, ModifierAccess> components = new HashMap<>();

    @Before
    public void setUp() throws Exception {
        component = new TestComponent();
        components.put("component1", ModifierAccess.EDIT);
        components.put("component2", ModifierAccess.READ);
        components.put("component3", ModifierAccess.HIDE);
    }

    @Test
    public void testConstructor(){
        assertNotNull(new PermissionAccess());
    }

    @Test
    public void getModifierAccess() throws Exception {
        assertEquals(ModifierAccess.EDIT, PermissionAccess.getModifierAccess(1));
        assertEquals(ModifierAccess.READ, PermissionAccess.getModifierAccess(2));
        assertEquals(ModifierAccess.HIDE, PermissionAccess.getModifierAccess(3));
        assertEquals(ModifierAccess.HIDE, PermissionAccess.getModifierAccess(4));
    }

    @Test
    public void replacePermissionAccessHideToHide() throws Exception {
        assertEquals(ModifierAccess.HIDE, component.getModifierAccess());
        PermissionAccess.replacePermissionAccess(component, ModifierAccess.HIDE);
        assertEquals(ModifierAccess.HIDE, component.getModifierAccess());
    }

    @Test
    public void replacePermissionAccessHideToRead() throws Exception {
        assertEquals(ModifierAccess.HIDE, component.getModifierAccess());
        PermissionAccess.replacePermissionAccess(component, ModifierAccess.READ);
        assertEquals(ModifierAccess.READ, component.getModifierAccess());
    }

    @Test
    public void replacePermissionAccessHideToEdit() throws Exception {
        assertEquals(ModifierAccess.HIDE, component.getModifierAccess());
        PermissionAccess.replacePermissionAccess(component, ModifierAccess.EDIT);
        assertEquals(ModifierAccess.EDIT, component.getModifierAccess());
    }

    @Test
    public void replacePermissionAccessReadToHide() throws Exception {
        component.setPermissionAccess(ModifierAccess.READ);
        assertEquals(ModifierAccess.READ, component.getModifierAccess());
        PermissionAccess.replacePermissionAccess(component, ModifierAccess.HIDE);
        assertEquals(ModifierAccess.READ, component.getModifierAccess());
    }

    @Test
    public void replacePermissionAccessReadToRead() throws Exception {
        component.setPermissionAccess(ModifierAccess.READ);
        assertEquals(ModifierAccess.READ, component.getModifierAccess());
        PermissionAccess.replacePermissionAccess(component, ModifierAccess.READ);
        assertEquals(ModifierAccess.READ, component.getModifierAccess());
    }

    @Test
    public void replacePermissionAccessReadToEdit() throws Exception {
        component.setPermissionAccess(ModifierAccess.READ);
        assertEquals(ModifierAccess.READ, component.getModifierAccess());
        PermissionAccess.replacePermissionAccess(component, ModifierAccess.EDIT);
        assertEquals(ModifierAccess.EDIT, component.getModifierAccess());
    }

    @Test
    public void replacePermissionAccessEditToHide() throws Exception {
        component.setPermissionAccess(ModifierAccess.EDIT);
        assertEquals(ModifierAccess.EDIT, component.getModifierAccess());
        PermissionAccess.replacePermissionAccess(component, ModifierAccess.HIDE);
        assertEquals(ModifierAccess.EDIT, component.getModifierAccess());
    }

    @Test
    public void replacePermissionAccessEditToRead() throws Exception {
        component.setPermissionAccess(ModifierAccess.EDIT);
        assertEquals(ModifierAccess.EDIT, component.getModifierAccess());
        PermissionAccess.replacePermissionAccess(component, ModifierAccess.READ);
        assertEquals(ModifierAccess.EDIT, component.getModifierAccess());
    }

    @Test
    public void replacePermissionAccessEditToEdit() throws Exception {
        component.setPermissionAccess(ModifierAccess.EDIT);
        assertEquals(ModifierAccess.EDIT, component.getModifierAccess());
        PermissionAccess.replacePermissionAccess(component, ModifierAccess.EDIT);
        assertEquals(ModifierAccess.EDIT, component.getModifierAccess());
    }

    @Test
    public void replaceMapPermissionAccessEditToHide() {
        assertEquals(ModifierAccess.EDIT, components.get("component1"));
        PermissionAccess.replacePermissionAccess(components, "component1", ModifierAccess.HIDE);
        assertEquals(ModifierAccess.EDIT, components.get("component1"));
    }

    @Test
    public void replaceMapPermissionAccessEditToRead() {
        assertEquals(ModifierAccess.EDIT, components.get("component1"));
        PermissionAccess.replacePermissionAccess(components, "component1", ModifierAccess.READ);
        assertEquals(ModifierAccess.EDIT, components.get("component1"));
    }

    @Test
    public void replaceMapPermissionAccessEditToEdit() {
        assertEquals(ModifierAccess.EDIT, components.get("component1"));
        PermissionAccess.replacePermissionAccess(components, "component1", ModifierAccess.EDIT);
        assertEquals(ModifierAccess.EDIT, components.get("component1"));
    }

    @Test
    public void replaceMapPermissionAccessReadToHide() {
        assertEquals(ModifierAccess.READ, components.get("component2"));
        PermissionAccess.replacePermissionAccess(components, "component2", ModifierAccess.HIDE);
        assertEquals(ModifierAccess.READ, components.get("component2"));
    }

    @Test
    public void replaceMapPermissionAccessReadToRead() {
        assertEquals(ModifierAccess.READ, components.get("component2"));
        PermissionAccess.replacePermissionAccess(components, "component2", ModifierAccess.READ);
        assertEquals(ModifierAccess.READ, components.get("component2"));
    }

    @Test
    public void replaceMapPermissionAccessReadToEdit() {
        assertEquals(ModifierAccess.READ, components.get("component2"));
        PermissionAccess.replacePermissionAccess(components, "component2", ModifierAccess.EDIT);
        assertEquals(ModifierAccess.EDIT, components.get("component2"));
    }

    @Test
    public void replaceMapPermissionAccessHideToHide() {
        assertEquals(ModifierAccess.HIDE, components.get("component3"));
        PermissionAccess.replacePermissionAccess(components, "component3", ModifierAccess.HIDE);
        assertEquals(ModifierAccess.HIDE, components.get("component3"));
    }

    @Test
    public void replaceMapPermissionAccessHideToRead() {
        assertEquals(ModifierAccess.HIDE, components.get("component3"));
        PermissionAccess.replacePermissionAccess(components, "component3", ModifierAccess.READ);
        assertEquals(ModifierAccess.READ, components.get("component3"));
    }

    @Test
    public void replaceMapPermissionAccessHideToEdit() {
        assertEquals(ModifierAccess.HIDE, components.get("component3"));
        PermissionAccess.replacePermissionAccess(components, "component3", ModifierAccess.EDIT);
        assertEquals(ModifierAccess.EDIT, components.get("component3"));
    }

    private class TestComponent implements PermissionAccessUI {
        private ModifierAccess access = ModifierAccess.HIDE;
        private String identify = "";

        @Override
        public void setPermissionAccess(ModifierAccess access) {
            this.access = access;
        }

        @Override
        public void replacePermissionAccess(ModifierAccess access) {
            PermissionAccess.replacePermissionAccess(this, access);
        }

        @Override
        public ModifierAccess getModifierAccess() {
            return access;
        }

        @Override
        public void setIdentify(String identify) {
            this.identify = identify;
        }

        @Override
        public String getIdentify() {
            return this.identify;
        }
    }
}