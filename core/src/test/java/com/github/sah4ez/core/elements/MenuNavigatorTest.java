package com.github.sah4ez.core.elements;

import com.github.sah4ez.core.permission.ModifierAccess;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.verify;

/**
 * Created by aleksandr on 28.12.16.
 */
public class MenuNavigatorTest extends Assert {

    private Workspace workspace = Mockito.mock(Workspace.class);
    private TestMenuNavigator menu;
    private FilterPanel filterPanel = Mockito.mock(FilterPanel.class);

    @Before
    public void setUp() throws Exception {
        Mockito.when(filterPanel.isVisible()).thenReturn(false).thenReturn(true);

        Mockito.when(workspace.getFilterPanel()).thenReturn(filterPanel);

        menu = new TestMenuNavigator("test", workspace, "menuTest1");
    }

    @Test
    public void getParentLayout() throws Exception {
        assertEquals(workspace, menu.getParentLayout());
    }

    @Test
    public void setIdentify() throws Exception {
        menu.setIdentify("test");
        assertEquals("test", menu.getIdentify());
    }

    @Test
    public void getIdentify() throws Exception {
        assertEquals("menuTest1", menu.getIdentify());
    }

    @Test
    public void filter() throws Exception {
        menu.filter();
        verify(filterPanel).isVisible();
        verify(filterPanel).loadColumns();
        verify(filterPanel).show();
        assertEquals("highlight", menu.getFilter().getStyleName());

        menu.filter();
        verify(filterPanel).hide();
        assertNull(menu.getFilter().getStyleName());

    }

    @Test
    public void getAdd() throws Exception {
        assertNotNull(menu.getAdd());
    }

    @Test
    public void getDelete() throws Exception {
        assertNotNull(menu.getDelete());
    }

    @Test
    public void getPrint() throws Exception {
        assertNotNull(menu.getPrint());
    }

    @Test
    public void getModifierAccess() throws Exception {
        assertEquals(ModifierAccess.HIDE, menu.getModifierAccess());
    }

    @Test
    public void setPermissionAccessHIDE() throws Exception {
        menu.setModifierAccess(ModifierAccess.HIDE);
        assertFalse(menu.getItems().get(0).isVisible());
        assertFalse(menu.getItems().get(1).isVisible());
        assertFalse(menu.getItems().get(2).isVisible());
        assertFalse(menu.getItems().get(3).isVisible());

        assertFalse(menu.getItems().get(0).isEnabled());
        assertFalse(menu.getItems().get(1).isEnabled());
        assertFalse(menu.getItems().get(2).isEnabled());
        assertFalse(menu.getItems().get(3).isEnabled());

        assertFalse(menu.isVisible());
        assertFalse(menu.isEnabled());
    }

    @Test
    public void setPermissionAccessREAD() throws Exception {
        menu.setModifierAccess(ModifierAccess.READ);
        assertTrue(menu.getItems().get(0).isVisible());
        assertTrue(menu.getItems().get(1).isVisible());
        assertTrue(menu.getItems().get(2).isVisible());
        assertTrue(menu.getItems().get(3).isVisible());

        assertFalse(menu.getItems().get(0).isEnabled());
        assertFalse(menu.getItems().get(1).isEnabled());
        assertFalse(menu.getItems().get(2).isEnabled());
        assertFalse(menu.getItems().get(3).isEnabled());

        assertTrue(menu.isVisible());
        assertFalse(menu.isEnabled());
    }

    @Test
    public void setPermissionAccessEDIT() throws Exception {
        menu.setModifierAccess(ModifierAccess.EDIT);
        assertTrue(menu.getItems().get(0).isVisible());
        assertTrue(menu.getItems().get(1).isVisible());
        assertTrue(menu.getItems().get(2).isVisible());
        assertTrue(menu.getItems().get(3).isVisible());

        assertTrue(menu.getItems().get(0).isEnabled());
        assertTrue(menu.getItems().get(1).isEnabled());
        assertTrue(menu.getItems().get(2).isEnabled());
        assertTrue(menu.getItems().get(3).isEnabled());

        assertTrue(menu.isVisible());
        assertTrue(menu.isEnabled());
    }

    @Test
    public void replacePermissionAccessHideToHide() throws Exception {
        menu.setModifierAccess(ModifierAccess.HIDE);
        menu.replacePermissionAccess(ModifierAccess.HIDE);

        assertFalse(menu.getItems().get(0).isVisible());
        assertFalse(menu.getItems().get(1).isVisible());
        assertFalse(menu.getItems().get(2).isVisible());
        assertFalse(menu.getItems().get(3).isVisible());

        assertFalse(menu.getItems().get(0).isEnabled());
        assertFalse(menu.getItems().get(1).isEnabled());
        assertFalse(menu.getItems().get(2).isEnabled());
        assertFalse(menu.getItems().get(3).isEnabled());

        assertFalse(menu.isVisible());
        assertFalse(menu.isEnabled());
    }

    @Test
    public void replacePermissionAccessHideToRead() throws Exception {
        menu.setModifierAccess(ModifierAccess.HIDE);
        menu.replacePermissionAccess(ModifierAccess.READ);

        assertTrue(menu.getItems().get(0).isVisible());
        assertTrue(menu.getItems().get(1).isVisible());
        assertTrue(menu.getItems().get(2).isVisible());
        assertTrue(menu.getItems().get(3).isVisible());

        assertFalse(menu.getItems().get(0).isEnabled());
        assertFalse(menu.getItems().get(1).isEnabled());
        assertFalse(menu.getItems().get(2).isEnabled());
        assertFalse(menu.getItems().get(3).isEnabled());

        assertTrue(menu.isVisible());
        assertFalse(menu.isEnabled());
    }

    @Test
    public void replacePermissionAccessHideToEdit() throws Exception {
        menu.setModifierAccess(ModifierAccess.HIDE);
        menu.replacePermissionAccess(ModifierAccess.EDIT);

        assertTrue(menu.getItems().get(0).isVisible());
        assertTrue(menu.getItems().get(1).isVisible());
        assertTrue(menu.getItems().get(2).isVisible());
        assertTrue(menu.getItems().get(3).isVisible());

        assertTrue(menu.getItems().get(0).isEnabled());
        assertTrue(menu.getItems().get(1).isEnabled());
        assertTrue(menu.getItems().get(2).isEnabled());
        assertTrue(menu.getItems().get(3).isEnabled());

        assertTrue(menu.isVisible());
        assertTrue(menu.isEnabled());
    }

    @Test
    public void replacePermissionAccessReadToHide() throws Exception {
        menu.setModifierAccess(ModifierAccess.READ);
        menu.replacePermissionAccess(ModifierAccess.HIDE);

        assertTrue(menu.getItems().get(0).isVisible());
        assertTrue(menu.getItems().get(1).isVisible());
        assertTrue(menu.getItems().get(2).isVisible());
        assertTrue(menu.getItems().get(3).isVisible());

        assertFalse(menu.getItems().get(0).isEnabled());
        assertFalse(menu.getItems().get(1).isEnabled());
        assertFalse(menu.getItems().get(2).isEnabled());
        assertFalse(menu.getItems().get(3).isEnabled());

        assertTrue(menu.isVisible());
        assertFalse(menu.isEnabled());
    }

    @Test
    public void replacePermissionAccessReadToRead() throws Exception {
        menu.setModifierAccess(ModifierAccess.READ);
        menu.replacePermissionAccess(ModifierAccess.READ);

        assertTrue(menu.getItems().get(0).isVisible());
        assertTrue(menu.getItems().get(1).isVisible());
        assertTrue(menu.getItems().get(2).isVisible());
        assertTrue(menu.getItems().get(3).isVisible());

        assertFalse(menu.getItems().get(0).isEnabled());
        assertFalse(menu.getItems().get(1).isEnabled());
        assertFalse(menu.getItems().get(2).isEnabled());
        assertFalse(menu.getItems().get(3).isEnabled());

        assertTrue(menu.isVisible());
        assertFalse(menu.isEnabled());
    }

    @Test
    public void replacePermissionAccessReadToEdit() throws Exception {
        menu.setModifierAccess(ModifierAccess.READ);
        menu.replacePermissionAccess(ModifierAccess.EDIT);

        assertTrue(menu.getItems().get(0).isVisible());
        assertTrue(menu.getItems().get(1).isVisible());
        assertTrue(menu.getItems().get(2).isVisible());
        assertTrue(menu.getItems().get(3).isVisible());

        assertTrue(menu.getItems().get(0).isEnabled());
        assertTrue(menu.getItems().get(1).isEnabled());
        assertTrue(menu.getItems().get(2).isEnabled());
        assertTrue(menu.getItems().get(3).isEnabled());

        assertTrue(menu.isVisible());
        assertTrue(menu.isEnabled());
    }
    @Test
    public void replacePermissionAccessEditToHide() throws Exception {
        menu.setModifierAccess(ModifierAccess.EDIT);
        menu.replacePermissionAccess(ModifierAccess.HIDE);

        assertTrue(menu.getItems().get(0).isVisible());
        assertTrue(menu.getItems().get(1).isVisible());
        assertTrue(menu.getItems().get(2).isVisible());
        assertTrue(menu.getItems().get(3).isVisible());

        assertTrue(menu.getItems().get(0).isEnabled());
        assertTrue(menu.getItems().get(1).isEnabled());
        assertTrue(menu.getItems().get(2).isEnabled());
        assertTrue(menu.getItems().get(3).isEnabled());

        assertTrue(menu.isVisible());
        assertTrue(menu.isEnabled());
    }

    @Test
    public void replacePermissionAccessEditToRead() throws Exception {
        menu.setModifierAccess(ModifierAccess.EDIT);
        menu.replacePermissionAccess(ModifierAccess.READ);

        assertTrue(menu.getItems().get(0).isVisible());
        assertTrue(menu.getItems().get(1).isVisible());
        assertTrue(menu.getItems().get(2).isVisible());
        assertTrue(menu.getItems().get(3).isVisible());

        assertTrue(menu.getItems().get(0).isEnabled());
        assertTrue(menu.getItems().get(1).isEnabled());
        assertTrue(menu.getItems().get(2).isEnabled());
        assertTrue(menu.getItems().get(3).isEnabled());

        assertTrue(menu.isVisible());
        assertTrue(menu.isEnabled());
    }

    @Test
    public void replacePermissionAccessEditToEdit() throws Exception {
        menu.setModifierAccess(ModifierAccess.EDIT);
        menu.replacePermissionAccess(ModifierAccess.EDIT);

        assertTrue(menu.getItems().get(0).isVisible());
        assertTrue(menu.getItems().get(1).isVisible());
        assertTrue(menu.getItems().get(2).isVisible());
        assertTrue(menu.getItems().get(3).isVisible());

        assertTrue(menu.getItems().get(0).isEnabled());
        assertTrue(menu.getItems().get(1).isEnabled());
        assertTrue(menu.getItems().get(2).isEnabled());
        assertTrue(menu.getItems().get(3).isEnabled());

        assertTrue(menu.isVisible());
        assertTrue(menu.isEnabled());
    }
    @Test
    public void setReadMode() throws Exception {
        menu.setReadMode();
        assertFalse(menu.isEnabled());
        assertTrue(menu.isVisible());
    }

    @Test
    public void setEditMode() throws Exception {
        menu.setEditMode();
        assertTrue(menu.isEnabled());
        assertTrue(menu.isVisible());
    }

    @Test
    public void setHideMode() throws Exception{
        menu.setHideMode();
        assertFalse(menu.isEnabled());
        assertFalse(menu.isVisible());
    }

    @Test
    public void getFilter() throws Exception {
        assertNotNull(menu.getFilter());
    }

    @Test
    public void hideAdd() throws Exception {
        menu.hideAdd();
        assertFalse(menu.getAdd().isVisible());
    }

    @Test
    public void showAdd() throws Exception {
        menu.showAdd();
        assertTrue(menu.getAdd().isVisible());
    }

    @Test
    public void hideDelete() throws Exception {
        menu.hideDelete();
        assertFalse(menu.getDelete().isVisible());
    }

    @Test
    public void showDelete() throws Exception {
        menu.showDelete();
        assertTrue(menu.getDelete().isVisible());
    }

    @Test
    public void hidePrint() throws Exception {
        menu.hidePrint();
        assertFalse(menu.getPrint().isVisible());
    }

    @Test
    public void showPrint() throws Exception {
        menu.showPrint();
        assertTrue(menu.getPrint().isVisible());
    }

    @Test
    public void hideFilter() throws Exception {
        menu.hideFilter();
        assertFalse(menu.getFilter().isVisible());
    }

    @Test
    public void showFilter() throws Exception {
        menu.showFilter();
        assertTrue(menu.getFilter().isVisible());
    }

    @Test
    public void editModeOff() throws Exception {
        menu.editModeOff();
        assertNull(menu.getAdd().getStyleName());

    }

    @Test
    public void editModeOn() throws Exception {
        menu.editModeOn();
        assertEquals("highlight", menu.getAdd().getStyleName());

    }

    @Test
    public void testConstructor(){
        assertNotNull(menu);
    }

    private class TestMenuNavigator extends MenuNavigator {

        public TestMenuNavigator(String caption, Workspace parent, String identify) {
            super(caption, parent, identify);
        }

        @Override
        public void add() {

        }

        @Override
        public void delete() {

        }

        @Override
        public void print() {

        }
    }

}