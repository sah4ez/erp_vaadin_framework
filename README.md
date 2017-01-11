erp-vaadin-framework
==============

Framework ERP UI on Vaadin.


[![Build Status](https://travis-ci.org/sah4ez/erp_vaadin_framework.svg?branch=master)](https://travis-ci.org/sah4ez/erp_vaadin_framework)
[![Coverage Status](https://coveralls.io/repos/github/sah4ez/erp_vaadin_framework/badge.svg?branch=master)](https://coveralls.io/github/sah4ez/erp_vaadin_framework?branch=master)
[![Code Climate](https://codeclimate.com/github/sah4ez/erp_vaadin_framework/badges/gpa.svg)](https://codeclimate.com/github/sah4ez/erp_vaadin_framework)



INSTALL
==========

```xml
    <dependency>
      <groupId>com.github.sah4ez</groupId>
      <artifactId>core</artifactId>
      <version>1.0.9</version>
    </dependency>

```

INSTALL SNAPSHOT
========

For SNAPSHOT version.
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
      <version>1.0.4-SNAPSHOT</version>
    </dependency>

```
WHAT NEW
==========

*v 1.0.10*

**CrossTable**:

- add `CellStyleGenerator` that used method `getCssStyle()` from `CellCondition`.
- add selection mode for cross table: Single selection cell, Multi selection cell in row, Multi selection cell in column and Multi selection cell.

**SelectionModeCrossTable**:

- add enum `SelectionModeCrossTable`:
    - `SINGLE_CELL`;
    - `MULTI_CELL_IN_ROW`;
    - `MULTI_CELL_IN_COLUMNT`;
    - `MULTI_CELL`.

**Condition**:

- add `CSS` for condition to jar.

*v 1.0.9*

**BottomTabs**:

- added `public abstract void clear()`, need for cleaning components added to tab in TabSheet.

**CrossTable**:

- add class `public abstract class CrossTable`, class with two inner `DataContainer`, where first container used for created rows and second container used for created columns;
- add method `createData(String idFirst, String captionFirst, String idSecond, String captionSecond, String value)`, `idFirst, idSecond` - id property of entity, which use for generate query. `captionFirst, captionSecodn` - property of entity, which input in first column and headers other columns. And `valueProperty` - property elements from `firstContainer`. This method used data from two containers and create table.
- add method `public abstract CellCondition getCell(Object idRow, Object idColumn)` that `return` data from DB for each cell in cross table;

**Condition**:

- add enum `Condition` implementation `CellCodition`, with conditions: `EDIT`, `NOT_USE`, `USE`, `USE_EDIT`, `USE_NOT_EDIT`;
    - `EDIT` - this Object are edited;
    - `NOT_USE` - this Object not used;
    - `USE` - this Object are used and wasn't edited; 
    - `USE_EDIT` - this Object are used and was edited;
    - `USE_NOT_EDIT` - this Object must be edited and used.


*v 1.0.8*

**FilterPanel**:

- refactoring code, for better testing code.

**BottomTabs**:

- added `Logic` to constructor `abstract class`, and added *getter* and *setter*.

*v 1.0.7*

**PermissionAccessUI**:

- added property `String identity` for insert into instances of `Map` all component `CommonView`.
    

HOW TO
========

This framework has next structure:
```
  core
  |--data
  |  |--CellCondition(I)
  |  |--Condition(E)
  |  |--DataContainer(A)
  |  +--TreeBeanContainer(C)
  |--elements
  |  |--ButtomTabs(A)
  |  |--CommonLogic(A)
  |  |--CommonView(A)
  |  |--CrossTable(A)
  |  |--FilterPanel(A)
  |  |--Logic(I)
  |  |--Menu(A)
  |  |--MenuNavigator(A)
  |  |--Mode(E)
  |  |--SelectionModeCrossTable(E)
  |  +--Workspace(A)
  +--permission
     |--ModifierAccess(A)
     |--PermissionAccess(F)
     +--PermissionAccessUI(I)
```

(A) - abstract class, (C) - class, (I) - interface, (E) - enum, (F) - final class. 

This framework extend **Demo Vaadin CRUD**. 

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

In MyUI create class MainScreen, example:


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

Where MyMenu - extend Menu from Framework. MyView extend from CommonView, MyLogic extend from CommonLogic.

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

Not forget return from ItemClickEven.ClickListener, if this return null and will result to NullPointerException.

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

Then create instance *MyLayout* in *class MyView*.

RUN
==========
For run this example you need change directory with **pom.xml** and execute command:
```
mvn clear install jetty:run
```
and on **localhost:8080** start your application.

![Example](https://raw.githubusercontent.com/sah4ez/erp_vaadin_framework/master/picture/screen-01.png)

DATA STRUCTURE
==========
For structure data use JPA with implement **Serializable** interface:
```java
public class Element implements Serializable {
    private Integer id = 0;
    private String name = "element";
    private Float price = 0.0F;

    public Element(Integer id, String name, Float price) {
        this.id = id;
        this.name = name;
        this.price = price;
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

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }
}
```
And create class Container:
```java
public class ElementContainer extends DataContainer<Element> {
    public ElementContainer() {
        super(Element.class);
    }

    @Override
    protected void initHeaders() {
        addCaptionColumn("id", "name", "price"); //set property from Entity
        addHeaderColumn("ID", "Название", "Цена"); //set Caption for table
        addCollapsedColumn(true, false, false); //set collapsed column
    }

    @Override
    public DataContainer loadAllData() {
        add(new Element(1, "name1", 1.0f));
        add(new Element(2, "name2", 2.0f));
        add(new Element(3, "name3", 3.0f));
        add(new Element(4, "name4", 4.0f));
        add(new Element(5, "name5", 5.0f));
        add(new Element(6, "name6", 6.0f));
        add(new Element(7, "name7", 7.0f));
        add(new Element(8, "name8", 8.0f));
        add(new Element(9, "name9", 9.0f));
        add(new Element(10, "name10", 10.0f));
        add(new Element(11, "name11", 11.0f));
        //or you may load data from your DB.
        return this;
    }
}
```

For binding data to table (or treeTable) you might use this method:
` CommonLogic.setDataToTalbe(DataContainer container, CustomTable table);`

### License ###
## [**GPL**](http://www.gnu.org/licenses/gpl.txt) ##
