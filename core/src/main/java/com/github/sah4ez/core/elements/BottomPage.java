package com.github.sah4ez.core.elements;

import com.vaadin.server.Resource;
import com.vaadin.ui.Component;

import java.util.EventObject;

/**
 * Created by pc999 on 1/10/17.
 */
public abstract class BottomPage<T1 extends Component, T2 extends Component> {
    private T1 component;
    private Resource resource;
    private String caption;
    private T2 externalComponent;

    public BottomPage() {}

    public void setResource(Resource resource) {
        this.resource = resource;
    }

    public Resource getResource() {
        return resource;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getCaption() {
        return caption;
    }

    public T1 getComponent() {
        return component;
    }

    public void setComponent(T1 component) {
        this.component = component;
    }

    public void setExternalComponent(T2 externalComponent) {
        this.externalComponent = externalComponent;
    }

    public abstract void listener(EventObject event);

    public T2 getExternalComponet() {
        return externalComponent;
    }
}
