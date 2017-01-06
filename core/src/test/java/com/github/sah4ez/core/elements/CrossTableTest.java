package com.github.sah4ez.core.elements;

import com.github.sah4ez.core.data.DataContainer;
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
                    this.add(new Element1(4, "name4", 4.0F));
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
    public void testInnerContainer(){
        assertNotNull(crossTable.getFirstContainer());
        assertNotNull(crossTable.getSecondContainer());
    }

    @Test
    public void testCreateDataInCrossTable(){
        crossTable.createData("id", "name", "id", "name", "state");
    }

    private class MyCrossTableTest extends CrossTable{

        public MyCrossTableTest(Logic logic, String identify, DataContainer<?> first, DataContainer<?> second) {
            super(logic, identify, first, second);
        }
    }

    //<editor-fold desc="Description ENTITY CLASS">
    private class Element1 {
        private Integer id = 0;
        private String name = "";
        private Float price = 0.0F;

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
