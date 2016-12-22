erp-vaadin-framework
==============

Framework ERP UI on Vaadin.

INSTALL
========
You need add repository to your pom.xml:
```xml

<repositories>
  <repository>
    <id>oss-sonatype</id>
    <name>oss-sonatype</name>
    <url>
      https://oss.sonatype.org/content/repositories/snapshots/
    </url>
    <snapshots>
      <enabled>true</enabled>
    </snapshots>
  </repository>
</repositories>
```

And add dependency:
```xml
    <dependency>
      <groupId>com.github.sah4ez</groupId>
      <artifactId>core</artifactId>
      <version>1.0.1-SNAPSHOT</version>
    </dependency>

```

HOW TO
========

This framework has next structure:
```
  core
  |__data
  |  |_DataContainer(A)
  |  |_TreeBeanContainer(C)
  |__elements
  |  |_ButtomTabs(A)
  |  |_CommonLogic(A)
  |  |_CommonView(A)
  |  |_FilterPanel(A)
  |  |_Logic(I)
  |  |_Menu(A)
  |  |_MenuNavigator(A)
  |  |_Mode(E)
  |  |_Workspace(A)
  |__permission
     |_ModifierAccess(A)
     |_PermissionAccess(F)
     |_PermissionAccessUI(I)
```
(A) - abstract class, (C) - class, (I) - interface, (E) - enum, (F) - final class. </p>

This framework extend **Demo Vaadin CRUD**. </p>

QUICK START
==========

You need create **UI** Vaadin:
```java
@Theme("mytheme")
public class MyUI extends UI {
    
    @Override
    protected void init(VaadinRequest vaadinRequest) {
        Responsive.makeResponsive(this);
        Locale locale = new Locale("ru", "RU");
        this.setLocale(locale);
        this.getSession().setLocale(locale);
        getPage().setTitle("Example");
        showMainView();
    }

    private void showMainView() {
        addStyleName(ValoTheme.UI_WITH_MENU);
        setContent(new MainScreen(this));
        String view = getNavigator().getState();
        getNavigator().navigateTo(view);
    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}
```
</p>
In MyUI create class MainScreen, example: </p>

```java

public class MainScreen extends HorizontalLayout{

    private MyMenu menu;

    public MainScreen(MyUI ui){
        CssLayout viewContainer = new CssLayout();
        viewContainer.addStyleName("valo-content");
        viewContainer.setSizeFull();

        final Navigator navigator = new Navigator(ui, viewContainer);
        navigator.setErrorView(ErrorView.class);

        menu = new MyMenu(navigator);
        menu.setMenuCaption("This Example");

        menu.addView(new MyView(), "MyView", "MyView", null);

        navigator.addViewChangeListener(viewChangeListener);

        addComponent(menu);
        addComponent(viewContainer);
        setExpandRatio(viewContainer, 1);
        setSizeFull();
    }

    ViewChangeListener viewChangeListener = new ViewChangeListener() {

        @Override
        public boolean beforeViewChange(ViewChangeEvent event) {
            return true;
        }

        @Override
        public void afterViewChange(ViewChangeEvent event) {
            menu.setActiveView(event.getViewName());
        }

    };
}

```

Where MyMenu - extend Menu from Framework. MyView extend from CommonView, MyLogic extend from CommonLogic<p>
For UI create package **layout** where need make: 
```java

public class MyLayout extends Workspace {
    
    private ElementContainer container = new ElementContainer();
    private MyTabSheet tabSheet;
    private MyMenu menu;

    public MyLayout(Logic logic) {
        super(logic);
        tabSheet = new MyTabSheet();
        menu = new MyMenu("myMenu", this);
        logic.setDataToTable(container.loadAllData(), getTable());
        setBottomTabs(tabSheet);
        setNavigator(menu);
    }
    
    @Override
    protected ItemClickEvent.ItemClickListener editTableItemClick() {
        return itemClickEvent -> {
        };
    }

    @Override
    protected ItemClickEvent.ItemClickListener selectTableItemClick() {
        return itemClickEvent -> {
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
}
```

Not forget return from ItemClickEven.ClickListener, if this return null and will result to NullPointerException. <p> 
Example instance of MenuNavigator with mode for MenuItem:
```java

public class MyMenu extends MenuNavigator {

    public MyMenu(String caption, Workspace parent) {
        super(caption, parent);
    }

    @Override
    public void add() {
        if (getAdd().getStyleName() == null)
            getAdd().setStyleName(ENABLE_BUTTON_STYLE);
        else
            getAdd().setStyleName(null);
    }

    @Override
    public void delete() {
        if (getDelete().getStyleName() == null)
            getDelete().setStyleName(ENABLE_BUTTON_STYLE);
        else
            getDelete().setStyleName(null);
    }

    @Override
    public void print() {
        if (getPrint().getStyleName() == null)
            getPrint().setStyleName(ENABLE_BUTTON_STYLE);
        else
            getPrint().setStyleName(null);

    }
}
```
Example instance of BottomTabs:
```java

public class MyTabSheet extends BottomTabs {
    public MyTabSheet() {
        super();
    }

    @Override
    public void initTabs() {
        addCaption("Tab1",
                "Tab2",
                "Tab3",
                "Tab4");

        addComponent(new Label("label1"),
                new Label("label2"),
                new Label("label3"),
                new Label("label4"));

        addResource(FontAwesome.AMAZON,
                FontAwesome.AMAZON,
                FontAwesome.AMAZON,
                FontAwesome.AMAZON
        );
    }
}
```
For add Tab to **TabSheet**, you need add **caption** to method *addCaption(String ... caption)*,
add **component** to method *addComponent(Component ... component)*, and **icon** to method *addResource(Resource ... res)*.
<p>
Then create instance *MyLayout* in *class MyView*.

RUN
==========
For run this example you need change directory with **pom.xml** and execute command:
```
mvn clear install jetty:run
```
and on **localhost:8080** start your application.

