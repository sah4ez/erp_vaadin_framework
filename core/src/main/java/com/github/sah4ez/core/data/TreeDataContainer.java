package com.github.sah4ez.core.data;

import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.data.Property;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by aleksandr on 20.12.16.
 */
abstract public class TreeDataContainer<T extends TreeEntity> extends DataContainer<T> implements Container.Hierarchical {
    // The contained bean type uses this property to store
    // the parent relationship.
    Object parentPID;

    public TreeDataContainer(Class<T> type,
                             Object parentPropertyId) {
        super(type);

        this.parentPID = parentPropertyId;
    }

    @Override
    public Collection<?> getChildren(Object itemId) {
        ArrayList<Object> children = new ArrayList<>();

        //TODO: Find new implementation
        // This implementation has O(n^2) complexity when
        // painting the tree, so it's really inefficient.
        for (Object candidateId : getItemIds()) {
            Item item = getItem(candidateId);
            Property property = item.getItemProperty(parentPID);
            Object parentRef = null;
            if (property != null && property.getValue() != null)
                parentRef = property.getValue();
            if (parentRef != null && parentRef == itemId)
                children.add(candidateId);
        }

        return children;
    }

    @Override
    public Object getParent(Object itemId) {
        return getItem(itemId).
                getItemProperty(parentPID).getValue();
    }

    @Override
    public Collection<?> rootItemIds() {
        ArrayList<Object> result = new ArrayList<>();
        for (Object candidateId : getItemIds()) {
            Object parentRef = null;
            Item item = getItem(candidateId);
            if (item != null) {
                Property property = item.getItemProperty(parentPID);
                parentRef = property.getValue();
                if (parentRef == null)
                    result.add(candidateId);
            }
        }

        if (result.size() > 0)
            return result;
        else
            return this.getItemIds();
    }

    @Override
    public boolean setParent(Object itemId, Object newParentId)
            throws UnsupportedOperationException {
        throw new UnsupportedOperationException(
                "Not implemented here");
    }

    @Override
    public boolean areChildrenAllowed(Object itemId) {
        return hasChildren(itemId);
    }

    @Override
    public boolean setChildrenAllowed(Object itemId,
                                      boolean childrenAllowed)
            throws UnsupportedOperationException {
        throw new UnsupportedOperationException(
                "Not implemented here");

    }

    @Override
    public boolean isRoot(Object itemId) {
        return getItem(itemId).getItemProperty(parentPID).
                getValue() == null;
    }

    @Override
    public boolean hasChildren(Object itemId) {
        for (Object candidateId : getItemIds()) {
            Object parentRef = getItem(candidateId).
                    getItemProperty(parentPID).getValue();
            if (parentRef == itemId)
                return true;
        }
        return false;
    }

    @Override
    protected boolean isFiltered() {
        return super.isFiltered();
    }

    @Override
    protected void filterAll(){
        super.filterAll();
    }

    @Override
    protected void fireItemSetChange(){
        super.fireItemSetChange();
    }

    public void refresh() {
        if (isFiltered()) {
            filterAll();
        } else {
            fireItemSetChange();
        }
    }

    public List<T> getFilteredItemIds() {
        return super.getFilteredItemIds();
    }


    public void buildRelationship() {
        getItemIds().forEach(this::setParent);
    }

    protected void setParent(T t) {
        if (t.getIdParent() == null || t.getIdParent().equals(TreeEntity.NULL_ID_PARENT)) return;

        t.setParent(getByIdEntity(t.getIdParent()));
    }

}

