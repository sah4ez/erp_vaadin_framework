package com.github.sah4ez.core.elements;

import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.tepi.filtertable.FilterTable;

import java.util.function.Consumer;

import static org.junit.Assert.assertEquals;

/**
 * Created by pc999 on 1/10/17.
 */
public class BottomPageTest {
    private BottomPage<FilterTable> page;
    private ThemeResource resource = Mockito.mock(ThemeResource.class);
    private Component component = Mockito.mock(Component.class);
    private String caption = "caption";
    private FilterTable filterPanel = Mockito.mock(FilterTable.class);

    FilterTable table1 = Mockito.mock(FilterTable.class);
    FilterTable table2 = Mockito.mock(FilterTable.class);
    HorizontalLayout layout = Mockito.mock(HorizontalLayout.class);

    @Before
    public void setUp() throws Exception {
        page = new BottomPage<>();
        page.setExternalComponent(filterPanel);
        page.setCaption(caption);
        page.setResource(resource);
        page.setComponent(component);
    }

    @Test
    public void getExternalComponent() {
        assertEquals(filterPanel, page.getExternalComponent());
    }

    @Test
    public void getResource() throws Exception {
        System.out.println(page);
        assertEquals(resource, page.getResource());
    }

    @Test
    public void getCaption() throws Exception {
        assertEquals(caption, page.getCaption());
    }

    @Test
    public void getComponent() throws Exception {
        assertEquals(component, page.getComponent());
    }

    @Test
    public void testActionWithExternalTable(){
        Elemenet1 elemenet = new Elemenet1(1, "my name");
        Consumer consumer = event -> {
            elemenet.setName("bla-bla-bla");
        };

        page.addComponent(page.getComponent(), consumer);
        consumer.accept(null);

        assertEquals("bla-bla-bla", elemenet.getName());
    }

    @Test
    public void testSomeComponentAction(){
        Consumer<Component.Event> action1 = System.out::println;
        Consumer<Component.Event> action2 = System.out::println;
        page.addComponent(table1, action1);
        page.addComponent(table2, action2);

        Mockito.verify(table1).addListener(Mockito.any(Component.Listener.class));
        Mockito.verify(table2).addListener(Mockito.any(Component.Listener.class));
    }

    public HorizontalLayout testLayout(){
        layout.setSizeFull();
        table1.setSizeFull();
        table2.setSizeFull();
        layout.addComponent(table1);
        layout.addComponent(table2);

        return layout;
    }

    private class Elemenet1{
        private Integer id = 0;
        private String name = "";

        public Elemenet1(Integer id, String name) {
            this.id = id;
            this.name = name;
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
    }
}