package com.github.sah4ez.core.permission;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by aleksandr on 26.12.16.
 */
public class ModifierAccessTest extends Assert{

    @Test
    public void getId() throws Exception {
        assertEquals(1, ModifierAccess.EDIT.getId());
        assertEquals(2, ModifierAccess.READ.getId());
        assertEquals(3, ModifierAccess.HIDE.getId());
    }

    @Test
    public void getMessage() throws Exception {
        assertEquals("edit", ModifierAccess.EDIT.getMessage());
        assertEquals("read", ModifierAccess.READ.getMessage());
        assertEquals("hide", ModifierAccess.HIDE.getMessage());
    }

}