package com.github.sah4ez.core.elements;

import com.github.sah4ez.core.data.DataContainer;
import com.github.sah4ez.core.permission.ModifierAccess;
import com.github.sah4ez.core.permission.PermissionAccessUI;
import com.vaadin.ui.CustomTable;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by aleksandr on 26.12.16.
 */
public class CommonLogicTest extends Assert {

    private CommonView view = Mockito.mock(CommonView.class);
    private PermissionAccessUI ui = Mockito.mock(PermissionAccessUI.class);

    private Map<String, ModifierAccess> modifiers = new HashMap<>();
    private Map<String, PermissionAccessUI> components = new HashMap<>();

    private CustomTable table = Mockito.mock(CustomTable.class);
    private DataContainer container = Mockito.mock(DataContainer.class);
    private Object[] visible = new Object[]{};
    private Object[] headers = new Object[]{};
    private Object[] collapsed = new Object[]{};
    private Boolean allowed = false;

    private Object[] testVisible = new Object[]{"h1", "h2"};
    private String[] testHeaders = new String[]{"h1", "h2"};
    private Boolean[] testCollapsed = new Boolean[]{true, false};

    private Object expectedContainer = null;

    private TestLogic logic;

    @Before
    public void setUp() throws Exception {
        logic = new TestLogic(view);
        Mockito.doAnswer(invocation -> addUi(invocation.getArguments()))
                .when(view)
                .addUI(ui);

        Mockito.when(ui.getModifierAccess()).thenReturn(ModifierAccess.HIDE);

        Mockito.when(container.getCaptions()).thenReturn(testVisible);
        Mockito.when(container.getHeaders()).thenReturn(testHeaders);
        Mockito.when(container.getVisible()).thenReturn(testCollapsed);


        Mockito.doAnswer(invocation -> setContainer(invocation.getArguments()))
                .doNothing()
                .when(table)
                .setContainerDataSource(container);

        Mockito.doAnswer(invocation -> addVisibleColumns(invocation.getArguments()))
                .doNothing()
                .when(table)
                .setVisibleColumns(testVisible);

        Mockito.doAnswer(invocation -> addHeaders(invocation.getArguments()))
                .doNothing()
                .when(table)
                .setColumnHeaders(testHeaders);

        Mockito.doAnswer(invocation -> addCollapsed(invocation.getArguments()))
                .doNothing()
                .when(table)
                .setColumnCollapsed(testVisible[0], testCollapsed[0]);

        Mockito.doAnswer(invocation -> setAllowed(invocation.getArguments()))
                .doNothing()
                .when(table)
                .setColumnCollapsingAllowed(true);
    }



    private Void addUi(Object[] ui) {
        if (ui.length > 1) return null;
        if (!(ui[0] instanceof PermissionAccessUI)) return null;

        modifiers.put(ui.getClass().toString(), ((PermissionAccessUI) ui[0]).getModifierAccess());
        components.put(ui.getClass().toString(), ((PermissionAccessUI) ui[0]));

        return null;
    }

    private Void addVisibleColumns(Object[] columns){
        visible = columns;
        return null;
    }

    private Void addHeaders(Object[] columns){
        headers = columns;
        return null;
    }

    private Void addCollapsed(Object[] columns){
        collapsed = columns;
        return null;
    }

    private Void setAllowed(Object[] value){
        if (value.length>1) return null;
        if (!(value[0] instanceof Boolean)) return null;

        allowed = ((Boolean) value[0]);

        return null;
    }

    private Void setContainer(Object[] container){
        if (container.length>1) return null;

        expectedContainer = container[0];

        return null;
    }

    @Test
    public void getView() throws Exception {
        assertEquals(view, logic.getView());

    }

    @Test
    public void addUi() {
        logic.addUi(ui);
        assertEquals(1, modifiers.size());
        assertEquals(1, components.size());
    }

    @Test
    public void setDataToTable() throws Exception {
        logic.setDataToTable(container, table);
        assertEquals(expectedContainer, container);
        assertEquals(2, visible.length);
        assertEquals(2, headers.length);
        assertEquals(2, collapsed.length);
        assertTrue(allowed);

    }

    private class TestLogic extends CommonLogic {

        public TestLogic(CommonView view) {
            super(view);
        }

        @Override
        public void addUi(PermissionAccessUI ui) {
            getView().addUI(ui);
        }
    }


}