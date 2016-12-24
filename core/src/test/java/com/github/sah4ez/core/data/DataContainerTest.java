package com.github.sah4ez.core.data;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.Serializable;
import java.util.Objects;

/**
 * Created by aleksandr on 24.12.16.
 */
public class DataContainerTest extends Assert {

    private DataContainer<Element> container;

    @Before
    public void setUp() throws Exception {
        container = new DataContainer<Element>(Element.class) {
            @Override
            protected void initHeaders() {
                addCaption("id", "name");
                addHeader("id", "name");
                addCollapsed(true, true);
            }

            @Override
            public DataContainer loadAllData() {
                add(new Element(1, "name"));
                add(new Element(2, "name"));
                return this;
            }
        };
    }

    @Test
    public void testClass() {
        assertEquals(2, container.loadAllData().size());
        assertEquals(2, container.getVisible().length);
    }

    @Test
    public void getList() throws Exception {
        assertEquals(2, container.loadAllData().getList().size());
    }

    @Test
    public void stream() throws Exception {
        assertNotNull(container.stream());
    }

    @Test
    public void add() throws Exception {
        container.add(new Element(1, "name"));
        assertEquals(1, container.size());
        container.add(new Element(1, "name"));
        assertEquals(1, container.size());
        container.add(new Element(2, "name"));
        assertEquals(2, container.size());
    }

    @Test
    public void remove() throws Exception {
        container.loadAllData();
        assertEquals(2, container.size());
        container.remove(new Element(1, "name"));
        assertEquals(1, container.size());
    }

    @Test
    public void addCaption() throws Exception {
        assertEquals(2, container.getCaptions().length);
        container.addCaption("newCaption");
        assertEquals(3, container.getCaptions().length);
        container.addCaption("newCaption", "newCaption2");
        assertEquals(5, container.getCaptions().length);

    }

    @Test
    public void addHeader() throws Exception {
        assertEquals(2, container.getHeaders().length);
        container.addHeader("newCaption");
        assertEquals(3, container.getHeaders().length);
        container.addHeader("newCaption", "newCaption");
        assertEquals(5, container.getHeaders().length);
    }

    @Test
    public void addCollapsed() throws Exception {
        assertEquals(2, container.getVisible().length);
        container.addCollapsed(true);
        assertEquals(3, container.getVisible().length);
        container.addCollapsed(true, false);
        assertEquals(5, container.getVisible().length);
    }

    @Test
    public void getCaptions() throws Exception {
        assertNotNull(container.getCaptions());
    }

    @Test
    public void getHeaders() throws Exception {
        assertNotNull(container.getHeaders());
    }

    @Test
    public void getVisible() throws Exception {
        assertNotNull(container.getVisible());
    }

    @Test
    public void isEmpty() throws Exception {
        assertTrue(container.isEmpty());
    }

    @Test
    public void testClear() {
        assertEquals(2, container.loadAllData().size());
        container.clear();
        assertEquals(0, container.size());
        assertTrue(container.isEmpty());
    }

    @Test
    public void testRemoveHeaders() {
        assertEquals(2, container.getCaptions().length);
        assertEquals(2, container.getHeaders().length);
        assertEquals(2, container.getVisible().length);
        container.removeHeaders();
        assertEquals(0, container.getCaptions().length);
        assertEquals(0, container.getHeaders().length);
        assertEquals(0, container.getVisible().length);
    }

    //region Declaration test class Element
    private class Element implements Serializable {
        private Integer id = 0;
        private String name = "";

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
                    Objects.equals(getName(), element.getName());
        }

        @Override
        public int hashCode() {
            return Objects.hash(getId(), getName());
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