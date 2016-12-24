package com.github.sah4ez.core.data;

import com.vaadin.data.util.filter.SimpleStringFilter;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Created by aleksandr on 24.12.16.
 */
public class TreeDataContainerTest extends Assert{
    Element element1;
    Element element2;
    Element element3;
    private TreeDataContainer<Element> container;

    @Before
    public void setUp() throws Exception {
        container = new TreeDataContainer<Element>(Element.class, "parent") {
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

        element1 = new Element(1, "name1");
        element2 = new Element(2, "name2");
        element3 = new Element(3, "name3");

        element2.setParent(element1);

        element3.setParent(element2);

        container.add(element1);
        container.add(element2);
        container.add(element3);
    }

    @Test
    public void getChildren() throws Exception {
        assertEquals(1, container.getChildren(element1).size());
        assertEquals(1, container.getChildren(element2).size());
        assertEquals(0, container.getChildren(element3).size());
    }

    @Test
    public void getParent() throws Exception {
        assertNull(container.getParent(element1));
        assertNotNull(container.getParent(element2));
        assertNotNull(container.getParent(element3));
    }

    @Test
    public void rootItemIds() throws Exception {
        assertEquals(element1, ((ArrayList) container.rootItemIds()).get(0));
    }

    @Test (expected = UnsupportedOperationException.class)
    public void setParent() throws Exception {
        container.setParent(element1, element2);
    }

    @Test
    public void areChildrenAllowed() throws Exception {
        assertFalse(container.areChildrenAllowed(element3));
        assertTrue(container.areChildrenAllowed(element2));
        assertTrue(container.areChildrenAllowed(element1));
    }

    @Test (expected = UnsupportedOperationException.class)
    public void setChildrenAllowed() throws Exception {
        container.setChildrenAllowed(element1, true);
    }

    @Test
    public void isRoot() throws Exception {
        assertTrue(container.isRoot(element1));
        assertFalse(container.isRoot(element2));
        assertFalse(container.isRoot(element3));
    }

    @Test
    public void hasChildren() throws Exception {
        assertTrue(container.hasChildren(element1));
        assertTrue(container.hasChildren(element2));
        assertFalse(container.hasChildren(element3));
    }

    @Test
    public void getFilteredItemIds() throws Exception {
        assertNull(container.getFilteredItemIds());

        container.addContainerFilter(new SimpleStringFilter("id",
                "1", false,
                false));
        assertNotNull(container.getFilteredItemIds());

        assertEquals(element1, container.getFilteredItemIds().get(0));

        container.removeAllContainerFilters();

        assertNull(container.getFilteredItemIds());
    }

    @Test
    public void testRootIds(){
        container.clear();
        element1.setParent(null);
        element2.setParent(null);
        element3.setParent(null);

        container.add(element1);
        container.add(element2);
        container.add(element3);

        assertEquals(3,  container.rootItemIds().size());
    }

    //region Declaration test class Element
    public class Element implements Serializable {
        private Integer id = 0;
        private String name = "";
        public Element parent = null;

        public Element(Integer id, String name) {
            this.id = id;
            this.name = name;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Element)) return false;
            Element element = (Element) o;
            return Objects.equals(getId(), element.getId()) &&
                    Objects.equals(getName(), element.getName()) &&
                    Objects.equals(getParent(), element.getParent());
        }

        @Override
        public int hashCode() {
            return Objects.hash(getId(), getName(), getParent());
        }

        public Element getParent() {
            return parent;
        }

        public void setParent(Element parent) {
            this.parent = parent;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
    //endregion

}