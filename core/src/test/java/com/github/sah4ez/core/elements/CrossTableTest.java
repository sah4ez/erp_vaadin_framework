package com.github.sah4ez.core.elements;

import com.github.sah4ez.core.data.CellCondition;
import com.github.sah4ez.core.data.Condition;
import com.github.sah4ez.core.data.DataContainer;
import com.vaadin.event.ItemClickEvent;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * Created by aleksandr on 06.01.17.
 */
public class CrossTableTest extends Assert {

    MyCrossTableTest crossTable;
    Logic logic = Mockito.mock(Logic.class);

    //<editor-fold desc="Containers for ELEMENT 1 and 4">
    DataContainer<Element1> element1DataContainer =
            new DataContainer<Element1>(Element1.class) {
                @Override
                protected void initHeaders() {
                    addCaption("id", "name", "price");
                    addHeader("ID", "NAME", "PRICE");
                    addCollapsed(true, false, false);
                }

                @Override
                public DataContainer loadAllData() {
                    this.add(new Element1(1, "name1", 1.0F));
                    this.add(new Element1(2, "name2", 2.0F));
                    this.add(new Element1(3, "name3", 3.0F));
                    return this;
                }
            };
    DataContainer<Element4> element4DataContainer =
            new DataContainer<Element4>(Element4.class) {
                @Override
                protected void initHeaders() {
                    addCaption("id", "name", "price");
                    addHeader("ID", "NAME", "PRICE");
                    addCollapsed(true, false, false);
                }

                @Override
                public DataContainer loadAllData() {
                    this.add(new Element4(1, "name1", 1.0F));
                    this.add(new Element4(2, "name2", 2.0F));
                    this.add(new Element4(3, "name3", 3.0F));
                    this.add(new Element4(4, "name4", 4.0F));
                    return this;
                }
            };
    //</editor-fold>

    @Before
    public void setUp() {
        crossTable = new MyCrossTableTest(logic,
                "crosstable1",
                element1DataContainer,
                element4DataContainer);
    }

    @Test
    public void testInnerContainer() {
        assertNotNull(crossTable.getFirstContainer());
        assertNotNull(crossTable.getSecondContainer());
    }

    @Test
    public void testSetContainer() {
        crossTable.setFirstContainer(null);
        crossTable.setSecondContainer(null);
        assertNull(crossTable.getFirstContainer());
        assertNull(crossTable.getSecondContainer());
        crossTable.setFirstContainer(element1DataContainer);
        crossTable.setSecondContainer(element4DataContainer);
        assertEquals(element1DataContainer, crossTable.getFirstContainer());
        assertEquals(element4DataContainer, crossTable.getSecondContainer());
    }

    @Test
    public void testCreatePropertyInCrossTable() {
        crossTable.createData("id", "name", "id", "name", "condition");
        assertEquals(6, crossTable.getTable().getContainerPropertyIds().size());
        Object[] property = crossTable.getTable().getContainerPropertyIds().toArray();
        assertEquals("id", property[0].toString());
        assertEquals("name", property[1].toString());
        assertEquals("1", property[2].toString());
        assertEquals("2", property[3].toString());
        assertEquals("3", property[4].toString());
        assertEquals("4", property[5].toString());
    }

    @Test
    public void testCreateRowsInCrossTable() throws NoSuchFieldException, IllegalAccessException {
        crossTable.createData("id", "name", "id", "name", "condition");
        assertEquals(3, crossTable.getTable().size());
        Object[] items = crossTable.getTable().getItemIds().toArray();


        assertEquals("1",getValueProperty(items[0], "id"));
        assertEquals("2", getValueProperty(items[1], "id"));
        assertEquals("3", getValueProperty(items[2], "id"));

        assertEquals("name1", getValueProperty(items[0], "name"));
        assertEquals("name2", getValueProperty(items[1], "name"));
        assertEquals("name3", getValueProperty(items[2], "name"));

        assertEquals(Condition.USE, getValueProperty(items[0], "1"));
        assertEquals(Condition.USE_EDIT, getValueProperty(items[0], "2"));
        assertEquals(Condition.USE_NOT_EDIT, getValueProperty(items[0], "3"));
        assertEquals(Condition.NOT_USE, getValueProperty(items[0], "4"));

        assertEquals(Condition.USE, getValueProperty(items[1], "1"));
        assertEquals(Condition.USE_EDIT, getValueProperty(items[1], "2"));
        assertEquals(Condition.USE_NOT_EDIT, getValueProperty(items[1], "3"));
        assertEquals(Condition.NOT_USE, getValueProperty(items[1], "4"));

        assertEquals(Condition.USE, getValueProperty(items[2], "1"));
        assertEquals(Condition.USE_EDIT, getValueProperty(items[2], "2"));
        assertEquals(Condition.USE_NOT_EDIT, getValueProperty(items[2], "3"));
        assertEquals(Condition.NOT_USE, getValueProperty(items[2], "4"));
    }

    private Object getValueProperty(Object object, String property) {
        return crossTable.getTable().getItem(object).getItemProperty(property).getValue();
    }

    private class MyCrossTableTest extends CrossTable {

        public MyCrossTableTest(Logic logic, String identify, DataContainer<?> first, DataContainer<?> second) {
            super(logic, identify, first, second);
        }

        @Override
        public CellCondition getCell(Object idRow, Object idColumn) {
            if (idColumn instanceof String){
                switch ((String) idColumn){
                    case "1":
                        return Condition.USE;
                    case "2":
                        return Condition.USE_EDIT;
                    case "3":
                        return Condition.USE_NOT_EDIT;
                    case "4":
                        return Condition.NOT_USE;
                }
            }
            return Condition.USE;
        }

        @Override
        protected ItemClickEvent.ItemClickListener editTableItemClick() {
            return itemClickEvent ->{} ;
        }

        @Override
        protected ItemClickEvent.ItemClickListener selectTableItemClick() {
            return itemClickEvent -> {};
        }

        @Override
        protected ItemClickEvent.ItemClickListener editTableAllItemClick() {
            return itemClickEvent -> {};
        }

        @Override
        protected ItemClickEvent.ItemClickListener selectTableAllItemClick() {
            return itemClickEvent -> {};
        }
    }

    //<editor-fold desc="Description ENTITY CLASS">
    private class Element1 {
        private Integer id = 0;
        private String name = "";
        private Float price = 0.0F;
        private Condition condition = Condition.USE;

        private DataContainer<Element2> element2DataContainer;
        private DataContainer<Element3> element3DataContainer;

        public Element1(Integer id, String name, Float price) {
            this.id = id;
            this.name = name;
            this.price = price;
            element2DataContainer =
                    new DataContainer<Element2>(Element2.class) {
                        @Override
                        protected void initHeaders() {
                            addCaption("id", "name", "price");
                            addHeader("ID", "NAME", "PRICE");
                            addCollapsed(true, false, false);
                        }

                        @Override
                        public DataContainer loadAllData() {
                            this.add(new Element2(1, "name1", 1.0F));
                            this.add(new Element2(2, "name2", 2.0F));
                            this.add(new Element2(3, "name3", 3.0F));
                            this.add(new Element2(4, "name4", 4.0F));
                            return this;
                        }
                    };
            element3DataContainer =
                    new DataContainer<Element3>(Element3.class) {
                        @Override
                        protected void initHeaders() {
                            addCaption("id", "name", "price");
                            addHeader("ID", "NAME", "PRICE");
                            addCollapsed(true, false, false);
                        }

                        @Override
                        public DataContainer loadAllData() {
                            this.add(new Element3(1, "name1", 1.0F));
                            this.add(new Element3(2, "name2", 2.0F));
                            this.add(new Element3(3, "name3", 3.0F));
                            this.add(new Element3(4, "name4", 4.0F));
                            return this;
                        }
                    };
        }
    }

    private class Element2 {
        private Integer id = 0;
        private String name = "";
        private Float price = 0.0F;

        public Element2(Integer id, String name, Float price) {
            this.id = id;
            this.name = name;
            this.price = price;
        }
    }

    private class Element3 {
        private Integer id = 0;
        private String name = "";
        private Float price = 0.0F;

        public Element3(Integer id, String name, Float price) {
            this.id = id;
            this.name = name;
            this.price = price;
        }
    }

    private class Element4 {
        private Integer id = 0;
        private String name = "";
        private Float price = 0.0F;

        public Element4(Integer id, String name, Float price) {
            this.id = id;
            this.name = name;
            this.price = price;
        }
    }
    //</editor-fold>
}
