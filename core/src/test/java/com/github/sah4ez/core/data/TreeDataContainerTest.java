package com.github.sah4ez.core.data;

import com.vaadin.data.util.filter.SimpleStringFilter;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.mockito.Mockito.*;

/**
 * Created by aleksandr on 24.12.16.
 */
public class TreeDataContainerTest extends Assert{
    TreeElement treeElement1;
    TreeElement treeElement2;
    TreeElement treeElement3;
    private TreeDataContainer<TreeElement> container;

    @Before
    public void setUp() throws Exception {
        container = new TreeDataContainer<TreeElement>(TreeElement.class, "parent") {
            @Override
            protected void initHeaders() {
                addCaption("id", "name");
                addHeader("id", "name");
                addCollapsed(true, true);
            }

            @Override
            public DataContainer loadAllData() {
                return this;
            }
        };

        treeElement1 = new TreeElement(1, "name1", 0);
        treeElement2 = new TreeElement(2, "name2", 1);
        treeElement3 = new TreeElement(3, "name3", 2);

        treeElement2.setParent(treeElement1);

        treeElement3.setParent(treeElement2);

        container.add(treeElement1);
        container.add(treeElement2);
        container.add(treeElement3);
    }

    @Test
    public void getChildren() throws Exception {
        assertEquals(1, container.getChildren(treeElement1).size());
        assertEquals(1, container.getChildren(treeElement2).size());
        assertEquals(0, container.getChildren(treeElement3).size());
    }

    @Test
    public void getParent() throws Exception {
        assertNull(container.getParent(treeElement1));
        assertNotNull(container.getParent(treeElement2));
        assertNotNull(container.getParent(treeElement3));
    }

    @Test
    public void rootItemIds() throws Exception {
        assertEquals(treeElement1, ((ArrayList) container.rootItemIds()).get(0));
    }

    @Test (expected = UnsupportedOperationException.class)
    public void setParent() throws Exception {
        container.setParent(treeElement1, treeElement2);
    }

    @Test
    public void areChildrenAllowed() throws Exception {
        assertFalse(container.areChildrenAllowed(treeElement3));
        assertTrue(container.areChildrenAllowed(treeElement2));
        assertTrue(container.areChildrenAllowed(treeElement1));
    }

    @Test (expected = UnsupportedOperationException.class)
    public void setChildrenAllowed() throws Exception {
        container.setChildrenAllowed(treeElement1, true);
    }

    @Test
    public void isRoot() throws Exception {
        assertTrue(container.isRoot(treeElement1));
        assertFalse(container.isRoot(treeElement2));
        assertFalse(container.isRoot(treeElement3));
    }

    @Test
    public void hasChildren() throws Exception {
        assertTrue(container.hasChildren(treeElement1));
        assertTrue(container.hasChildren(treeElement2));
        assertFalse(container.hasChildren(treeElement3));
    }

    @Test
    public void getFilteredItemIds() throws Exception {
        assertNull(container.getFilteredItemIds());

        container.addContainerFilter(new SimpleStringFilter("idEntity",
                "1", false,
                false));
        assertNotNull(container.getFilteredItemIds());

        assertEquals(treeElement1, container.getFilteredItemIds().get(0));

        container.removeAllContainerFilters();

        assertNull(container.getFilteredItemIds());
    }

    @Test
    public void testRootIds(){
        container.clear();
        treeElement1.setParent(null);
        treeElement2.setParent(null);
        treeElement3.setParent(null);

        container.add(treeElement1);
        container.add(treeElement2);
        container.add(treeElement3);

        assertEquals(3,  container.rootItemIds().size());
    }

    @Test
    public void testBuildRelationship() throws Exception {
        //Assign

        //Act
        container.buildRelationship();

        //Assert
        assertTrue(container.hasChildren(treeElement1));
        assertTrue(container.hasChildren(treeElement2));
        assertFalse(container.hasChildren(treeElement3));

    }

    @Test
    public void testRefresh(){
        container.refresh();
    }

    @Test
    public void testSetParent() throws Exception {
        //Assign
        TreeElement treeElement = mock(TreeElement.class);
        when(treeElement.getIdParent()).thenReturn(4);
        container.add(treeElement);

        //Act
        container.setParent(treeElement);

        //Assert
        verify(treeElement).setParent(null);
    }
}