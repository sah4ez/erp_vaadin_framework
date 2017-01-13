package com.github.sah4ez.example.layout;

import com.github.sah4ez.core.elements.BottomPage;
import com.github.sah4ez.core.elements.Logic;
import com.github.sah4ez.core.elements.Workspace;
import com.github.sah4ez.example.data.Element;
import com.github.sah4ez.example.data.ElementContainer;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Layout;
import org.tepi.filtertable.FilterTable;

import java.util.Set;
import java.util.function.Consumer;

/**
 * Created by aleksandr on 20.12.16.
 */
public class MyLayout extends Workspace {
    private ElementContainer container = new ElementContainer();
    private MyTabSheet tabSheet;
    private MyMenu menu;
    private BottomPage<Layout, FilterTable> page1;
    private BottomPage<Label, FilterTable> page2;
    Button button1 = new Button("hello");
    Button button2 = new Button("hello");

    public MyLayout(Logic logic) {
        super(logic, "MyLayout1");
        tabSheet = new MyTabSheet(logic);

        Layout layout = new HorizontalLayout();
        layout.addComponents(button1, button2);
        page1 = new BottomPage<Layout, FilterTable>(layout, getTable(), "tab", FontAwesome.AMAZON) {

            @Override
            public void action(Event event) {
            }
        };

        Consumer action = event -> {
            Set<Element> set = ((Set<Element>) page1.getExternalComponent().getValue());
            Element element = set.stream().findFirst().get();
            element.setName("nnnn");
            System.out.println(element);
            container.refresh();
        };
        page1.addComponent(button1, action);

        Consumer action1 = event -> {
            Set<Element> set = ((Set<Element>) page1.getExternalComponent().getValue());
            Element element = set.stream().findFirst().get();
            element.setName("батон");
            System.out.println(element.getName());
            container.refresh();
        };

        page1.addComponent(button2, action1);

        page2 = new BottomPage<Label, FilterTable>(new Label("lllll"), getTable(), "tab2", FontAwesome.AMAZON) {
            @Override
            public void action(Event event) {

            }
        };

        tabSheet.addPage(page1);
        tabSheet.addPage(page2);

        menu = new MyMenu("myMenu", this);
        logic.setDataToTable(container.loadAllData(), getTable());
        setBottomTabs(tabSheet);
        setNavigator(menu);
    }

    public void action(){

    }

    @Override
    protected ItemClickEvent.ItemClickListener editTableItemClick() {
        return itemClickEvent -> {
        };
    }

    @Override
    protected ItemClickEvent.ItemClickListener selectTableItemClick() {
        return itemClickEvent -> {
            button1.setCaption(((Element) itemClickEvent.getItemId()).getId().toString() + " "
                    + ((Element) itemClickEvent.getItemId()).getName());
//            page1.getComponent().setCaption();

        };
    }

    @Override
    protected ItemClickEvent.ItemClickListener editTableAllItemClick() {
        return itemClickEvent -> {
        };
    }

    @Override
    protected ItemClickEvent.ItemClickListener selectTableAllItemClick() {
        return itemClickEvent -> {
        };
    }

    @Override
    public void clearLayout() {

    }
}
