package com.github.sah4ez.core.elements;

import com.github.sah4ez.core.data.DataContainer;
import com.github.sah4ez.core.data.TreeDataContainer;
import com.vaadin.data.util.filter.SimpleStringFilter;
import com.vaadin.ui.Button;
import com.vaadin.ui.Table;
import com.vaadin.ui.themes.ValoTheme;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.tepi.filtertable.FilterTable;
import org.tepi.filtertable.FilterTreeTable;

import java.util.Collections;

import static org.mockito.Mockito.verify;

/**
 * Created by aleksandr on 27.12.16.
 */
public class FilterPanelTest extends Assert {


    private TestFilterPanel testFilterTable;
    private TestFilterPanel testFilterFilterTable;
    private TestFilterPanel testFilterFilterTreeTable;

    private Table table = Mockito.mock(Table.class);
    private FilterTable filterTable = Mockito.mock(FilterTable.class);
    private FilterTreeTable filterTreeTable = Mockito.mock(FilterTreeTable.class);

    private Object[] visibleColumns = new Object[]{"id", "name", "price"};

    private DataContainer container = Mockito.mock(DataContainer.class);
    private TreeDataContainer treeContainer = Mockito.mock(TreeDataContainer.class);

    private TreeDataContainer containerAddParentAndChildren = null;

    @Before
    public void setUp() throws Exception {

        Mockito.when(container.getItemIds()).thenReturn(Collections.singletonList(1));
        Mockito.when(treeContainer.getItemIds()).thenReturn(Collections.singletonList(1));

        Mockito.when(treeContainer.getContainerFilters()).thenReturn(Collections.singletonList(1));

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
    public void filterButton() throws Exception {
        Button button = testFilterTable.filterButton("hello");
        assertEquals("null hello x", button.getCaption());
        assertEquals(1, testFilterTable.getFilters().getComponentCount());
        assertEquals(ValoTheme.BUTTON_QUIET, button.getStyleName());

        testFilterTable.removeFilter(button);
        assertEquals(0, testFilterTable.getFilters().getComponentCount());
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
    public void testSort() {
        assertEquals(0, testFilterTable.getSortColumn().size());
        assertEquals(0, testFilterTable.getSortAscending().size());
        testFilterTable.addSort("id", true);
        testFilterTable.addSort("name", false);
        assertEquals(2, testFilterTable.getSortColumn().size());
        assertEquals(2, testFilterTable.getSortAscending().size());
        testFilterTable.removeSort("id");
        assertEquals(1, testFilterTable.getSortColumn().size());
        assertEquals(1, testFilterTable.getSortAscending().size());
        testFilterTable.removeSort("name");
        assertEquals(0, testFilterTable.getSortColumn().size());
        assertEquals(0, testFilterTable.getSortAscending().size());
    }

    @Test
    public void testParentsAndChildren() {
        testFilterFilterTreeTable.addSort("id", true);
        assertNotNull(containerAddParentAndChildren);
    }

    @Test
    public void getNameIdentifyColumn() throws Exception {
        assertEquals("id", testFilterTable.getNameIdentifyColumn());
        assertEquals("id", testFilterFilterTable.getNameIdentifyColumn());
        assertEquals("id", testFilterFilterTreeTable.getNameIdentifyColumn());

    }

    @Test
    public void testAddRemoveFilters() {
        testFilterTable.addFilter("id", "1");
        verify(container).addContainerFilter(Mockito.any(SimpleStringFilter.class));


        testFilterFilterTreeTable.addFilter("id", "1");
        verify(treeContainer).addContainerFilter(Mockito.any(SimpleStringFilter.class));
        assertNotNull(containerAddParentAndChildren);
    }

    @Test
    public void testRemoveFilter(){
        SimpleStringFilter filter = new SimpleStringFilter("id", "1",  true, false);
        testFilterTable.removeFilter(filter);
        verify(container).removeContainerFilter(filter);

        testFilterTable.removeFilter(filter);
        verify(container).removeContainerFilter(filter);
    }

    @Test
    public void testGetContainer() {
        assertEquals(container, testFilterTable.getContainer());
        assertEquals(treeContainer, testFilterTable.getContainer());

        assertEquals(container, testFilterFilterTable.getContainer());
        assertEquals(treeContainer, testFilterFilterTable.getContainer());

        assertEquals(treeContainer, testFilterFilterTreeTable.getContainer());
    }

    @Test
    public void testAddButton(){
        Button.ClickEvent event = Mockito.mock(Button.ClickEvent.class);

        testFilterTable.addButton(event);
        assertTrue(testFilterTable.getFilters().isVisible());
    }

    @Test
    public void testDescSortButton(){
        Button.ClickEvent event = Mockito.mock(Button.ClickEvent.class);
        testFilterTable.descSortListener(event);

        assertTrue(testFilterTable.getFilters().isVisible());
        assertEquals(1, testFilterTable.getSortAscending().size());
        assertEquals(1, testFilterTable.getSortColumn().size());
    }

    @Test
    public void testAscSortButton(){
        Button.ClickEvent event = Mockito.mock(Button.ClickEvent.class);
        testFilterTable.ascSortListener(event);

        assertTrue(testFilterTable.getFilters().isVisible());
        assertEquals(1, testFilterTable.getSortAscending().size());
        assertEquals(1, testFilterTable.getSortColumn().size());
    }

    @Test
    public void createButton(){
        testFilterTable.createButton("Hello");
        verify(container).addContainerFilter(Mockito.any(SimpleStringFilter.class));
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