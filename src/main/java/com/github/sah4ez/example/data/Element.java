package com.github.sah4ez.example.data;

import java.io.Serializable;
import java.util.Objects;

/**
 * Created by aleksandr on 20.12.16.
 */
public class Element implements Serializable {
    private Integer id = 0;
    private String name = "element";
    private Float price = 0.0F;

    public Element(Integer id, String name, Float price) {
        this.id = id;
        this.name = name;
        this.price = price;
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

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Element element = (Element) o;
        return Objects.equals(id, element.id) &&
                Objects.equals(name, element.name) &&
                Objects.equals(price, element.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, price);
    }
}
