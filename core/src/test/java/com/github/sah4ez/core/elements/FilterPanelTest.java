package com.github.sah4ez.core.elements;

import com.github.sah4ez.core.data.DataContainer;
import com.github.sah4ez.core.data.TreeDataContainer;
import com.vaadin.ui.Table;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.tepi.filtertable.FilterTable;
import org.tepi.filtertable.FilterTreeTable;

import java.io.Serializable;

/**
 * Created by aleksandr on 27.12.16.
 */
public class FilterPanelTest extends Assert{


    TestFilterPanel testFilterTable;
    TestFilterPanel testFilterFilterTable;
    TestFilterPanel testFilterFilterTreeTable;

    Table table = Mockito.mock(Table.class);
    FilterTable filterTable = Mockito.mock(FilterTable.class);
    FilterTreeTable filterTreeTable = Mockito.mock(FilterTreeTable.class);

    Object[] visibleColumns = new Object[]{"id", "name", "price"};

    DataContainer container = Mockito.mock(DataContainer.class);
    TreeDataContainer treeContainer = Mockito.mock(TreeDataContainer.class);

    TreeDataContainer containerAddParentAndChildren = null;
    @Before
    public void setUp() throws Exception {


        Mockito.when(table.getVisibleColumns()).thenReturn(visibleColumns);
        Mockito.when(table.getColumnHeader("id")).thenReturn("ID");
        Mockito.when(table.getColumnHeader("name")).thenReturn("NAME");
        Mockito.when(table.getColumnHeader("price")).thenReturn("PRICE");
        Mockito.when(table.getContainerDataSource())
                .thenReturn(container)
                .thenReturn(container)
                .thenReturn(treeContainer)
                .thenReturn(treeContainer);

        Mockito.when(filterTable.getVisibleColumns()).thenReturn(visibleColumns);
        Mockito.when(filterTable.getColumnHeader("id")).thenReturn("ID");
        Mockito.when(filterTable.getColumnHeader("name")).thenReturn("NAME");
        Mockito.when(filterTable.getColumnHeader("price")).thenReturn("PRICE");
        Mockito.when(filterTable.getContainerDataSource())
                .thenReturn(container)
                .thenReturn(container)
                .thenReturn(treeContainer)
                .thenReturn(treeContainer);

        Mockito.when(filterTreeTable.getVisibleColumns()).thenReturn(visibleColumns);
        Mockito.when(filterTreeTable.getColumnHeader("id")).thenReturn("ID");
        Mockito.when(filterTreeTable.getColumnHeader("name")).thenReturn("NAME");
        Mockito.when(filterTreeTable.getColumnHeader("price")).thenReturn("PRICE");
        Mockito.when(filterTreeTable.getContainerDataSource())
                .thenReturn(treeContainer)
                .thenReturn(treeContainer);

        testFilterTable = new TestFilterPanel(table);
        testFilterFilterTable = new TestFilterPanel(filterTable);
        testFilterFilterTreeTable = new TestFilterPanel(filterTreeTable);

    }

    @Test
    public void loadColumns() throws Exception {
        assertEquals(3, testFilterTable.getColumn().getItemIds().size());
        assertEquals(3, testFilterFilterTable.getColumn().getItemIds().size());
        assertEquals(3, testFilterFilterTreeTable.getColumn().getItemIds().size());

    }

    @Test
    public void removeAllFilters() throws Exception {

    }

    @Test
    public void show() throws Exception {
        testFilterTable.show();
        assertTrue(testFilterTable.isVisible());

        testFilterFilterTable.show();
        assertTrue(testFilterFilterTable.isVisible());

        testFilterFilterTreeTable.show();
        assertTrue(testFilterFilterTreeTable.isVisible());
    }

    @Test
    public void hide() throws Exception {
        testFilterTable.hide();
        assertFalse(testFilterTable.isVisible());

        testFilterFilterTable.hide();
        assertFalse(testFilterFilterTable.isVisible());

        testFilterFilterTreeTable.hide();
        assertFalse(testFilterFilterTreeTable.isVisible());
    }

    @Test
    public void addParentsAndChildren() throws Exception {

    }

    @Test
    public void getNameIdentifyColumn() throws Exception {
        assertEquals("id", testFilterTable.getNameIdentifyColumn());
        assertEquals("id", testFilterFilterTable.getNameIdentifyColumn());
        assertEquals("id", testFilterFilterTreeTable.getNameIdentifyColumn());

    }

    @Test
    public void sortListener() throws Exception {

    }

    @Test
    public void beforeFilter() throws Exception {

    }

    @Test
    public void testGetContainer(){
        assertEquals(container, testFilterTable.getContainer());
        assertEquals(treeContainer, testFilterTable.getContainer());

        assertEquals(container, testFilterFilterTable.getContainer());
        assertEquals(treeContainer, testFilterFilterTable.getContainer());

        assertEquals(treeContainer, testFilterFilterTreeTable.getContainer());
    }

    private class TestFilterPanel extends FilterPanel {

        public TestFilterPanel(Table table) {
            super(table);

        }

        public TestFilterPanel(FilterTable table) {
            super(table);
        }

        public TestFilterPanel(FilterTreeTable filterTreeTable) {
            super(filterTreeTable);
        }

        @Override
        public void addParentsAndChildren(TreeDataContainer treeDataContainer) {
            containerAddParentAndChildren = treeDataContainer;
        }

        @Override
        public Object getNameIdentifyColumn() {
            return "id";
        }

        @Override
        public void sortListener() {

        }
    }

}