package com.github.sah4ez.core.data;

import java.util.Objects;

/**
 * Created by aleksandr on 05.03.17.
 */
public class Element implements Entity {
    private Integer idEntity = 0;
    private String name = "";

    public Element(Integer idEntity, String name) {
        this.idEntity = idEntity;
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Element)) return false;
        Element element = (Element) o;
        return Objects.equals(getIdEntity(), element.getIdEntity()) &&
                Objects.equals(getName(), element.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIdEntity(), getName());
    }

    @Override
    public Integer getIdEntity() {
        return idEntity;
    }

    @Override
    public void setIdEntity(Integer idEntity) {
        this.idEntity = idEntity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
//endregion

