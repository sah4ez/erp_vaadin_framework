package com.github.sah4ez.example;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.Responsive;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.UI;
import com.vaadin.ui.themes.ValoTheme;

import javax.servlet.annotation.WebServlet;
import java.util.Locale;

/**
 * This UI is the application entry point. A UI may either represent a browser window
 * (or tab) or some part of a html page where a Vaadin application is embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is intended to be
 * overridden to add component to the user interface and initialize non-component functionality.
 */
@Theme("mytheme")
public class MyUI extends UI {


    @Override
    protected void init(VaadinRequest vaadinRequest) {

        Responsive.makeResponsive(this);
        //setLocale(vaadinRequest.getLocale());
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
