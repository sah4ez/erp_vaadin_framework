package com.github.sah4ez.core.elements;

import com.github.sah4ez.core.permission.ModifierAccess;
import com.github.sah4ez.core.permission.PermissionAccessUI;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by aleksandr on 26.12.16.
 */
public class CommonViewTest extends Assert {

    private TestView view;

    private Logic logic = Mockito.mock(Logic.class);

    private Map<String, ModifierAccess> modifiers = new HashMap<>();
    private Map<String, PermissionAccessUI> components = new HashMap<>();

    private PermissionAccessUI component1 = Mockito.mock(PermissionAccessUI.class);
    private PermissionAccessUI component2 = Mockito.mock(PermissionAccessUI.class);
    private PermissionAccessUI component3 = Mockito.mock(PermissionAccessUI.class);

    @Before
    public void setUp() throws Exception {
        view = new TestView();
        view.setLogic(logic);

        Mockito.when(component1.getModifierAccess()).thenReturn(ModifierAccess.EDIT);
        Mockito.when(component1.getIdentify()).thenReturn("component1");

        Mockito.when(component2.getModifierAccess()).thenReturn(ModifierAccess.READ);
        Mockito.when(component2.getIdentify()).thenReturn("component2");

        Mockito.when(component3.getModifierAccess()).thenReturn(ModifierAccess.HIDE);
        Mockito.when(component3.getIdentify()).thenReturn("component3");

    }

    @Test
    public void getLogic() throws Exception {
        assertEquals(logic, view.getLogic());
    }

    @Test
    public void loadComponents() throws Exception {
        assertNotNull(view.loadComponents());
        assertEquals(3, view.loadComponents().size());
    }

    @Test
    public void addUI() throws Exception {
        view.addUI(component1);
        view.addUI(component2);
        view.addUI(component3);

        assertEquals(3, view.getModifiers().size());
        assertEquals(3, view.getComponents().size());

    }

    @Test
    public void enter() throws Exception {
        view.addUI(component1);
        view.addUI(component2);
        view.addUI(component3);

        assertEquals(ModifierAccess.READ, view.getModifiers().get("component1"));
        assertEquals(ModifierAccess.READ, view.getModifiers().get("component2"));
        assertEquals(ModifierAccess.READ, view.getModifiers().get("component3"));

        view.enter(null);

        assertEquals(ModifierAccess.EDIT, view.getModifiers().get("component1"));
        assertEquals(ModifierAccess.READ, view.getModifiers().get("component2"));
        assertEquals(ModifierAccess.READ, view.getModifiers().get("component3"));
    }

    private class TestView extends CommonView {

        @Override
        public Map<String, ModifierAccess> loadComponents() {
            Map<String, ModifierAccess> modifiers = new HashMap<>();
            modifiers.put("component1", ModifierAccess.EDIT);
            modifiers.put("component2", ModifierAccess.READ);
            modifiers.put("component3", ModifierAccess.HIDE);
            return modifiers;
        }
    }

}