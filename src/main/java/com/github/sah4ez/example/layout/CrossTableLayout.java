package com.github.sah4ez.example.layout;

import com.github.sah4ez.core.data.CellCondition;
import com.github.sah4ez.core.data.Condition;
import com.github.sah4ez.core.data.DataContainer;
import com.github.sah4ez.core.elements.CrossTable;
import com.github.sah4ez.core.elements.Logic;
import com.vaadin.event.ItemClickEvent;

/**
 * Created by aleksandr on 08.01.17.
 */
public class CrossTableLayout extends CrossTable{
    private static DataContainer<Element1> element1DataContainer;
    private static DataContainer<Element4> element4DataContainer;

    private Condition lastCondition = null;

    static {
        element1DataContainer =
                new DataContainer<Element1>(Element1.class) {
                    @Override
                    protected void initHeaders() {
                        addCaption("id", "name", "price");
                        addHeader("ID", "NAME", "PRICE");
                        addCollapsed(true, false, false);
                    }

                    @Override
                    public DataContainer loadAllData() {
                        clear();
                        this.add(new Element1(1, "name1", 1.0F));
                        this.add(new Element1(2, "name2", 2.0F));
                        this.add(new Element1(3, "name3", 3.0F));
                        return this;
                    }
                };
        element4DataContainer =
                new DataContainer<Element4>(Element4.class) {
                    @Override
                    protected void initHeaders() {
                        addCaption("id", "name", "price");
                        addHeader("ID", "NAME", "PRICE");
                        addCollapsed(true, false, false);
                    }

                    @Override
                    public DataContainer loadAllData() {
                        clear();
                        this.add(new Element4(1, "name1", 1.0F));
                        this.add(new Element4(2, "name2", 2.0F));
                        this.add(new Element4(3, "name3", 3.0F));
                        this.add(new Element4(4, "name4", 4.0F));
                        return this;
                    }
                };
    }

    public CrossTableLayout(Logic logic, String identify ) {
        super(logic, identify, CrossTableLayout.element1DataContainer.loadAllData(), element4DataContainer.loadAllData());
        getTable().setSizeFull();
        editOff();
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
        return itemClickEvent -> {};
    }

    @Override
    protected ItemClickEvent.ItemClickListener selectTableItemClick() {
        return itemClickEvent -> {
        };
    }

    @Override
    protected ItemClickEvent.ItemClickListener editTableAllItemClick() {
        return itemClickEvent -> {};
    }

    @Override
    protected ItemClickEvent.ItemClickListener selectTableAllItemClick() {
        return itemClickEvent -> {};
    }

    private static class Element1 {
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

    private static class Element2 {
        private Integer id = 0;
        private String name = "";
        private Float price = 0.0F;

        public Element2(Integer id, String name, Float price) {
            this.id = id;
            this.name = name;
            this.price = price;
        }
    }

    private static class Element3 {
        private Integer id = 0;
        private String name = "";
        private Float price = 0.0F;

        public Element3(Integer id, String name, Float price) {
            this.id = id;
            this.name = name;
            this.price = price;
        }
    }

    private static class Element4 {
        private Integer id = 0;
        private String name = "";
        private Float price = 0.0F;

        public Element4(Integer id, String name, Float price) {
            this.id = id;
            this.name = name;
            this.price = price;
        }
    }
}
