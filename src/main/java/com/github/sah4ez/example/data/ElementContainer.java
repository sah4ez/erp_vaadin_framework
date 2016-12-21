package com.github.sah4ez.example.data;


import com.github.sah4ez.core.data.DataContainer;

/**
 * Created by aleksandr on 20.12.16.
 */
public class ElementContainer extends DataContainer<Element> {
    public ElementContainer() {
        super(Element.class);
    }

    @Override
    protected void initHeaders() {
        addCaptionColumn("id", "name", "price");
        addHeaderColumn("ID", "Название", "Цена");
        addCollapsedColumn(true, false, false);
    }

    @Override
    public DataContainer loadAllData() {
        add(new Element(1, "name1", 1.0f));
        add(new Element(2, "name2", 2.0f));
        add(new Element(3, "name3", 3.0f));
        add(new Element(4, "name4", 4.0f));
        add(new Element(5, "name5", 5.0f));
        add(new Element(6, "name6", 6.0f));
        add(new Element(7, "name7", 7.0f));
        add(new Element(8, "name8", 8.0f));
        add(new Element(9, "name9", 9.0f));
        add(new Element(10, "name10", 10.0f));
        add(new Element(11, "name11", 11.0f));
        return this;
    }
}
