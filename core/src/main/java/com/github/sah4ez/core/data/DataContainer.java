package com.github.sah4ez.core.data;

import com.vaadin.data.util.BeanItemContainer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 * Created by aleksandr on 20.12.16.
 */
abstract public class DataContainer<T> extends BeanItemContainer<T> {

    private final ArrayList<String> headers = new ArrayList<>();
    private ArrayList<String> captions = new ArrayList<>();
    private ArrayList<Boolean> visible = new ArrayList<>();

    public DataContainer(Class<T> type) {
        super(type);
        if (validCaption())
            initHeaders();
        // TODO: 23.12.16 реализовать checked exception для валидации количества столбцов
    }

    private boolean validCaption() {
        return captions.size() == visible.size() &&
                captions.size() == headers.size();
    }

    abstract protected void initHeaders();

    abstract public DataContainer loadAllData();

    public List<T> getList() {
        return getItemIds();
    }

    public Stream<T> stream() {
        return getItemIds().stream();
    }

    public DataContainer<T> add(T o) {
        addBean(o);
        return this;
    }

    public DataContainer<T> remove(T o) {
        this.removeItem(o);
        return this;
    }

    public void addCaption(String... captions) {
        Arrays.stream(captions).forEach(this::addCaption);
    }

    public void addHeader(String... headers) {
        Arrays.stream(headers).forEach(this::addHeader);
    }

    public void addCollapsed(Boolean... visible) {
        Arrays.stream(visible).forEach(this::addCollapsed);
    }

    public void addCaption(String caption) {
        this.captions.add(caption);
    }

    public void addHeader(String header) {
        this.headers.add(header);
    }

    public void addCollapsed(Boolean visible) {
        this.visible.add(visible);
    }

    public Object[] getCaptions() {
        Object[] caption = captions.toArray();
        return caption;
    }

    public String[] getHeaders() {
        String[] headers = this.headers.toArray(new String[0]);
        return headers;
    }

    public Boolean[] getVisible() {
        Boolean[] visible = this.visible.toArray(new Boolean[0]);
        return visible;
    }

    public void clear() {
        this.removeAllItems();
    }

    public void removeHeaders() {
        captions.clear();
        headers.clear();
        visible.clear();
    }

    public void refresh() {
        this.fireItemSetChange();
    }

    public boolean isEmpty(){
        return size()<=0;
    }
}

