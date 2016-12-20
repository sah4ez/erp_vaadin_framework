package com.sah4ez.data;

import com.vaadin.data.util.BeanItemContainer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 * Created by aleksandr on 20.12.16.
 */
abstract public class DataContainer<T> extends BeanItemContainer<T> {

    private ArrayList<String> captionList = new ArrayList<>();
    private ArrayList<Boolean> visibleList = new ArrayList<>();
    private final ArrayList<String> headersList = new ArrayList<>();

    public DataContainer(Class<T> type) {
        super(type);
        initHeaders();
    }

    public List<T> getList() {
        return getItemIds();
    }

    public Stream<T> stream() {
        return getItemIds().stream();
    }

    public DataContainer<T> add(T o){
        addBean(o);
        return this;
    }

    public DataContainer<T> remove(T o){
        this.removeItem(o);
        return this;
    }

    public DataContainer<T> get() {
        return this;
    }

    public void addCaptionColumn(String... captions) {
        Arrays.stream(captions).forEach(this::addCaptionColumn);
    }

    public void addHeaderColumn(String... headers) {
        Arrays.stream(headers).forEach(this::addHeaderColumn);
    }

    public void addCollapsedColumn(Boolean... visible) {
        Arrays.stream(visible).forEach(this::addCollapsedColumn);
    }

    public void addCaptionColumn(String caption) {
        this.captionList.add(caption);
    }

    public void addHeaderColumn(String header) {
        this.headersList.add(header);
    }

    public void addCollapsedColumn(Boolean visible) {
        this.visibleList.add(visible);
    }

    public Object[] getCaption() {
        Object[] caption = captionList.toArray();
        return caption;
    }

    public String[] getHeaders() {
        String[] headers = headersList.toArray(new String[0]);
        return headers;
    }

    public Boolean[] getVisible() {
        Boolean[] visible = visibleList.toArray(new Boolean[0]);
        return visible;
    }

    public void clear(){ this.removeAllItems(); }

    public void removeHeaders(){
        captionList.clear();
        headersList.clear();
        visibleList.clear();
    }

    abstract protected void initHeaders();

    abstract public DataContainer loadAllData();

    public void refresh(){
        this.fireItemSetChange();
    }
}

