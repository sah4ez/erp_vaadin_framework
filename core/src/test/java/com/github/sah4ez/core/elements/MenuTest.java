package com.github.sah4ez.core.elements;

import com.vaadin.navigator.Navigator;
import com.vaadin.server.Resource;
import com.vaadin.ui.Button;
import com.vaadin.ui.Image;
import com.vaadin.ui.MenuBar;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * Created by aleksandr on 28.12.16.
 */
public class MenuTest extends Assert {

    private TestMenu menu;
    private Navigator navigator = Mockito.mock(Navigator.class);
    private CommonView commonView = Mockito.mock(CommonView.class);
    private Resource resource = Mockito.mock(Resource.class);
    private Image image = Mockito.mock(Image.class);


    @Before
    public void setUp() throws Exception {
        menu = new TestMenu(navigator);

    }

    @Test
    public void userIsLoggined() throws Exception {
        assertFalse(menu.userIsLoggined());

    }

    @Test
    public void loginCommand() throws Exception {
        assertNull(menu.loginCommand());

    }

    @Test
    public void getMenuCaption() throws Exception {
        assertEquals("Example", menu.getMenuCaption());
    }

    @Test
    public void setMenuCaption() throws Exception {
        menu.setMenuCaption("test");
        assertEquals("test", menu.getMenuCaption());
    }

    @Test
    public void addView() throws Exception {
        menu.addView(commonView, "nameCommon", "captionCommon", resource);
        assertEquals(1, menu.getViewButtons().size());
    }

    @Test
    public void setActiveView() throws Exception {
        menu.addView(commonView, "nameCommon", "captionCommon", resource);
        menu.addView(commonView, "nameCommon1", "captionCommon1", resource);
        menu.setActiveView("nameCommon");
        assertTrue(menu.getViewButtons().get("nameCommon").getStyleName().contains("selected"));
        assertFalse(menu.getViewButtons().get("nameCommon1").getStyleName().contains("selected"));
    }

    @Test
    public void getImage() throws Exception{
        assertNull(menu.getImage());
    }

    @Test
    public void setImage() throws Exception {
        menu.setImage(image);
        assertEquals(image, menu.getImage());
    }

    @Test
    public void enterMenu(){
        Button.ClickEvent event = Mockito.mock(Button.ClickEvent.class);
        menu.enterMenu(event);
        assertTrue(menu.getMenuPart().getStyleName().contains("valo-menu-visible"));
        menu.enterMenu(event);
        assertFalse(menu.getMenuPart().getStyleName().contains("valo-menu-visible"));
    }

    private class TestMenu extends Menu {

        public TestMenu(Navigator navigator) {
            super(navigator);
        }

        @Override
        public MenuBar.Command loginCommand() {
            return null;
        }
    }

}