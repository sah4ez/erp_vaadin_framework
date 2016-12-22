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
      <version>1.0-SNAPSHOT</version>
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

    protected void showMainView() {
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


```

