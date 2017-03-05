package com.github.sah4ez.core.data;

import java.util.Objects;

/**
 * Created by aleksandr on 05.03.17.
 */
public class TreeElement extends Element implements TreeEntity {
    private Integer idParent = TreeEntity.NULL_ID_PARENT;
    public TreeEntity parent = null;

    public TreeElement(Integer id, String name) {
        super(id, name);
    }

    public TreeElement(Integer id, String name, Integer idParent) {
        super(id, name);
        this.idParent = idParent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TreeElement)) return false;
        TreeElement treeElement = (TreeElement) o;
        return super.equals(o) &&
                Objects.equals(getIdParent(), treeElement.getIdParent());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getIdParent());
    }

    public TreeEntity getParent() {
        return parent;
    }

    @Override
    public Integer getIdParent() {
        return null;
    }

    @Override
    public void setIdParent(Integer idParent) {
        this.idParent = idParent;
    }

    @Override
    public void setParent(TreeEntity parent) {
        this.parent = parent;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("TreeElement{");
        sb.append("idParent=").append(idParent);
        sb.append(", parent=").append(parent);
        sb.append('}');
        return sb.toString();
    }
}

