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
    private BottomPage<Component, FilterTable> page;
    private ThemeResource resource = Mockito.mock(ThemeResource.class);
    private Component component = Mockito.mock(Component.class);
    private String caption = "caption";
    private FilterTable filterPanel = Mockito.mock(FilterTable.class);

    FilterTable table1 = Mockito.mock(FilterTable.class);
    FilterTable table2 = Mockito.mock(FilterTable.class);
    HorizontalLayout layout = Mockito.mock(HorizontalLayout.class);

    @Before
    public void setUp() throws Exception {
        page = new BottomPageImpl();
        page.setResource(resource);
        page.setCaption(caption);
        page.setComponent(component);
        page.setExternalComponent(filterPanel);
    }

    @Test
    public void getResource() throws Exception {
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
        Component.Event event = Mockito.mock(Component.Event.class);

        Mockito.when(page.getExternalComponent().getValue()).thenReturn(elemenet);

        page.getComponent().addListener(page::action);

        page.action(event);

        assertEquals(((BottomPageImpl)page).getTargetName(), elemenet.getName());
        assertEquals(((BottomPageImpl)page).getTargetId(), elemenet.getId());
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

    public class BottomPageImpl extends BottomPage<Component, FilterTable> {
        private String targetName;
        private Integer targetId;

        public BottomPageImpl() {
            targetName = "bla-bla-bla";
            targetId = 21;
        }

        @Override
        public void action(Component.Event event) {
            Object select = getExternalComponent().getValue();

            if (select instanceof Elemenet1) {
                ((Elemenet1) select).setName(getTargetName());
                ((Elemenet1) select).setId(getTargetId());

            }
        }

        public String getTargetName() {
            return targetName;
        }

        public Integer getTargetId() {
            return targetId;
        }
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