package com.github.sah4ez.core.data;

import com.vaadin.data.Container;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by aleksandr on 20.12.16.
 */
public class TreeDataContainer<T> extends DataContainer<T> implements Container.Hierarchical {
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

        // This implementation has O(n^2) complexity when
        // painting the tree, so it's really inefficient.
        for (Object candidateId: getItemIds()) {
            Object parentRef = getItem(candidateId).
                    getItemProperty(parentPID).getValue();
            if (parentRef == itemId)
                children.add(candidateId);
        }

        if (children.size() > 0)
            return children;
        else
            return null;
    }

    @Override
    public Object getParent(Object itemId) {
        return getItem(itemId).
                getItemProperty(parentPID).getValue();
    }

    @Override
    public Collection<?> rootItemIds() {
        LinkedList<Object> result = new LinkedList<Object>();
        for (Object candidateId: getItemIds()) {
            Object parentRef  = null;
            parentRef = getItem(candidateId).getItemProperty(parentPID).getValue();
            if (parentRef == null)
                result.add(candidateId);
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
        for (Object candidateId: getItemIds()) {
            Object parentRef = getItem(candidateId).
                    getItemProperty(parentPID).getValue();
            if (parentRef == itemId)
                return true;
        }
        return false;
    }
    public void refresh(){
        if(isFiltered())
            filterAll();
        else fireItemSetChange();
    }

    @Override
    protected void initHeaders() {

    }

    @Override
    public DataContainer loadAllData() {
        return this;
    }

    public List<T> getFilteredItemIds(){
        return super.getFilteredItemIds();
    }
}

