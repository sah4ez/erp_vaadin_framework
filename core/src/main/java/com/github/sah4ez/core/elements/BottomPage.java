package com.github.sah4ez.core.elements;

import com.vaadin.server.Resource;
import com.vaadin.ui.Component;

import java.util.function.Consumer;

/**
 * Created by pc999 on 1/10/17.
 */
public class BottomPage<T2 extends Component> {
    private Component component;
    private Resource resource;
    private String caption;
    private T2 externalComponent;

    public BottomPage() {}

    public BottomPage(T2 externalComponent, String caption, Resource resource) {
        setCaption(caption);
        setResource(resource);
        setExternalComponent(externalComponent);
    }

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

    public Component getComponent() {
        return component;
    }

    public void setComponent(Component component) {
        this.component = component;
    }

    public void setExternalComponent(T2 externalComponent) {
        this.externalComponent = externalComponent;
    }

    public T2 getExternalComponent() {
        return externalComponent;
    }

    public void addComponent(Component component, Consumer<Component.Event> action1) {
        component.addListener(action1::accept);
    }
}
