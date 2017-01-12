package com.github.sah4ez.core.elements;

import com.github.sah4ez.core.permission.ModifierAccess;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.server.ThemeResource;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.tepi.filtertable.FilterTable;

/**
 * Created by aleksandr on 28.12.16.
 */
public class WorkspaceTest extends Assert {

    private TestWorkspace workspace;
    private Logic logic = Mockito.mock(Logic.class);

    @Before
    public void setUp() throws Exception {
        workspace = new TestWorkspace(logic, "workspace");
    }

    @Test
    public void getSplitPosition() throws Exception {
        assertEquals(100F, workspace.getSplitPosition(), 0.0F);
    }

    @Test
    public void setIdentify() throws Exception {
        workspace.setIdentify("h");
        assertEquals("h", workspace.getIdentify());
    }

    @Test
    public void getIdentify() throws Exception {
        assertEquals("workspace", workspace.getIdentify());
    }

    @Test
    public void getCAPTION() throws Exception {
        assertEquals("", workspace.getCAPTION());
    }

    @Test
    public void setCAPTION() throws Exception {
        workspace.setCAPTION("HH");
        assertEquals("HH", workspace.getCAPTION());
    }

    @Test
    public void getPICTURE() throws Exception {
        assertNull(workspace.getPICTURE());
    }

    @Test
    public void setPICTURE() throws Exception {
        ThemeResource resource = Mockito.mock(ThemeResource.class);
        workspace.setPICTURE(resource);
        assertEquals(resource, workspace.getPICTURE());
    }

    @Test
    public void getTableAll() throws Exception {
        assertNotNull(workspace.getTableAll());
    }

    @Test
    public void setTableAll() throws Exception {
        FilterTable table = Mockito.mock(FilterTable.class);
        workspace.setTableAll(table);
        assertEquals(table, workspace.getTableAll());
    }

    @Test
    public void getTable() throws Exception {
        assertNotNull(workspace.getTable());
    }

    @Test
    public void setTable() throws Exception {
        FilterTable table = Mockito.mock(FilterTable.class);
        workspace.setTable(table);
        assertEquals(table, workspace.getTable());
    }

    @Test
    public void getVerticalSplitPanel() throws Exception {
        assertNotNull(workspace.getVerticalSplitPanel());
    }

    @Test
    public void getHorizontalSplitPanel() throws Exception {
        assertNotNull(workspace.getHorizontalSplitPanel());
    }

    @Test
    public void getBottomTabs() throws Exception {
        assertNull(workspace.getBottomTabs());
    }

    @Test
    public void setBottomTabs() throws Exception {
        BottomTabs tabs = Mockito.mock(BottomTabs.class);
        workspace.setBottomTabs(tabs);
        assertEquals(tabs, workspace.getBottomTabs());
    }

    @Test
    public void getNavigator() throws Exception {
        assertNotNull(workspace.getNavigator());
    }

    @Test
    public void setNavigator() throws Exception {
        MenuNavigator navigator = Mockito.mock(MenuNavigator.class);
        workspace.setNavigator(navigator);
        assertEquals(navigator, workspace.getNavigator());
    }

    @Test
    public void getFilterPanel() throws Exception {
        assertNotNull(workspace.getFilterPanel());
    }

    @Test
    public void setFilterPanel() throws Exception {
        FilterPanel filterPanel = Mockito.mock(FilterPanel.class);
        workspace.setFilterPanel(filterPanel);
        assertEquals(filterPanel, workspace.getFilterPanel());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void getFilterTable() {
        workspace.getFilterTable();
    }

    @Test
    public void setModifierAccess() throws Exception {
        BottomTabs tabs = Mockito.mock(BottomTabs.class);
        workspace.setBottomTabs(tabs);

        workspace.setModifierAccess(ModifierAccess.EDIT);
        assertEquals(ModifierAccess.EDIT, workspace.getModifierAccess());

        workspace.setModifierAccess(ModifierAccess.HIDE);
        assertEquals(ModifierAccess.HIDE, workspace.getModifierAccess());

        workspace.setModifierAccess(ModifierAccess.READ);
        assertEquals(ModifierAccess.READ, workspace.getModifierAccess());

    }

    @Test
    public void innerNavigator(){
        MenuNavigator navigator = workspace.getNavigator();
        navigator.add();
        navigator.delete();
        navigator.print();
        navigator.filter();

        navigator.setIdentify("hh");
        assertEquals("hh", navigator.getIdentify());
    }

    @Test
    public void innerFilterPanel(){
        FilterPanel panel = workspace.getFilterPanel();
        assertEquals("id", panel.getNameIdentifyColumn());
        panel.addParentsAndChildren(null);
        panel.sortListener();
    }

    @Test
    public void setSplitPosition(){
        workspace.setSplitPosition(100F);
        assertTrue(workspace.getHorizontalSplitPanel().isLocked());

        workspace.setSplitPosition(50F);
        assertFalse(workspace.getHorizontalSplitPanel().isLocked());
    }

    @Test
    public void replacePermissionAccess() throws Exception {
        assertEquals(ModifierAccess.HIDE, workspace.getModifierAccess());
        workspace.replacePermissionAccess(ModifierAccess.EDIT);
        assertEquals(ModifierAccess.EDIT, workspace.getModifierAccess());
    }

    @Test
    public void getModifierAccess() throws Exception {
        assertEquals(ModifierAccess.HIDE, workspace.getModifierAccess());
    }

    @Test
    public void hideBottomPanel() throws Exception {
        workspace.hideBottomPanel();
        assertEquals(100F, workspace.getVerticalSplitPanel().getSplitPosition(), 0.0F);
        assertTrue(workspace.getVerticalSplitPanel().isLocked());
    }

    @Test
    public void showBottomPanel() throws Exception {
        workspace.showBottomPanel();
        assertEquals(50F, workspace.getVerticalSplitPanel().getSplitPosition(), 0.0F);
        assertFalse(workspace.getVerticalSplitPanel().isLocked());
    }

    @Test
    public void editOff() throws Exception {
        FilterTable table = Mockito.mock(FilterTable.class);
        FilterTable tableAll = Mockito.mock(FilterTable.class);
        workspace.setTable(table);
        workspace.setTableAll(tableAll);

        workspace.editOff();
        assertEquals(Mode.NORMAL, workspace.getMode());
        Mockito.verify(table).removeItemClickListener(workspace.getEditItemClickListener());
        Mockito.verify(table).addItemClickListener(workspace.getSelectItemClickListener());

        Mockito.verify(tableAll).removeItemClickListener(workspace.getEditItemClickListenerAll());
        Mockito.verify(tableAll).addItemClickListener(workspace.getSelectItemClickListenerAll());
    }

    @Test
    public void editOn() throws Exception {
        FilterTable table = Mockito.mock(FilterTable.class);
        FilterTable tableAll = Mockito.mock(FilterTable.class);
        workspace.setTable(table);
        workspace.setTableAll(tableAll);

        workspace.editOn();
        assertEquals(Mode.CHANGE, workspace.getMode());
        Mockito.verify(table).addItemClickListener(workspace.getEditItemClickListener());
        Mockito.verify(table).removeItemClickListener(workspace.getSelectItemClickListener());

        Mockito.verify(tableAll).addItemClickListener(workspace.getEditItemClickListenerAll());
        Mockito.verify(tableAll).removeItemClickListener(workspace.getSelectItemClickListenerAll());
    }

    @Test
    public void getMode() throws Exception {
        assertEquals(Mode.NORMAL, workspace.getMode());
    }

    @Test
    public void setMode() throws Exception {
        workspace.setMode(Mode.PROJECT);
        assertEquals(Mode.PROJECT, workspace.getMode());
    }

    @Test
    public void getEditItemClickListener() throws Exception {
        assertNotNull(workspace.getEditItemClickListener());
    }

    @Test
    public void setEditItemClickListener() throws Exception {
        ItemClickEvent.ItemClickListener itemClickListener =
                Mockito.mock(ItemClickEvent.ItemClickListener.class);
        workspace.setEditItemClickListener(itemClickListener);
        assertEquals(itemClickListener, workspace.getEditItemClickListener());
    }

    @Test
    public void getEditItemClickListenerAll() throws Exception {
        assertNotNull(workspace.getEditItemClickListenerAll());
    }

    @Test
    public void setEditItemClickListenerAll() throws Exception {
        ItemClickEvent.ItemClickListener itemClickListener =
                Mockito.mock(ItemClickEvent.ItemClickListener.class);
        workspace.setEditItemClickListenerAll(itemClickListener);
        assertEquals(itemClickListener, workspace.getEditItemClickListenerAll());
    }

    @Test
    public void getSelectItemClickListener() throws Exception {
        assertNotNull(workspace.getSelectItemClickListener());
    }

    @Test
    public void setSelectItemClickListener() throws Exception {
        ItemClickEvent.ItemClickListener itemClickListener =
                Mockito.mock(ItemClickEvent.ItemClickListener.class);
        workspace.setSelectItemClickListener(itemClickListener);
        assertEquals(itemClickListener, workspace.getSelectItemClickListener());
    }

    @Test
    public void getSelectItemClickListenerAll() throws Exception {
        assertNotNull(workspace.getSelectItemClickListenerAll());
    }

    @Test
    public void setSelectItemClickListenerAll() throws Exception {
        ItemClickEvent.ItemClickListener itemClickListener =
                Mockito.mock(ItemClickEvent.ItemClickListener.class);
        workspace.setSelectItemClickListenerAll(itemClickListener);
        assertEquals(itemClickListener, workspace.getSelectItemClickListenerAll());
    }

    @Test
    public void testClearLayout(){
        workspace.clearLayout();
    }

    private class TestWorkspace extends Workspace {

        public TestWorkspace(Logic logic, String identify) {
            super(logic, identify);
        }

        @Override
        protected ItemClickEvent.ItemClickListener editTableItemClick() {
            return itemClickEvent -> {
            };
        }

        @Override
        protected ItemClickEvent.ItemClickListener selectTableItemClick() {
            return itemClickEvent -> {
            };
        }

        @Override
        protected ItemClickEvent.ItemClickListener editTableAllItemClick() {
            return itemClickEvent -> {
            };
        }

        @Override
        protected ItemClickEvent.ItemClickListener selectTableAllItemClick() {
            return itemClickEvent -> {
            };
        }

        @Override
        public void clearLayout() {

        }
    }

}