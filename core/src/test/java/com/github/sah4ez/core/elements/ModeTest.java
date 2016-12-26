package com.github.sah4ez.core.elements;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by aleksandr on 26.12.16.
 */
public class ModeTest extends Assert{
    @Test
    public void getId() throws Exception {
        assertEquals(1, Mode.CHANGE.getId());
        assertEquals(2, Mode.REMOVE.getId());
        assertEquals(3, Mode.NORMAL.getId());
        assertEquals(4, Mode.PROJECT.getId());
        assertEquals(5, Mode.TASK.getId());
    }

    @Test
    public void getMessage() throws Exception {
        assertEquals("change", Mode.CHANGE.getMessage());
        assertEquals("remove", Mode.REMOVE.getMessage());
        assertEquals("normal", Mode.NORMAL.getMessage());
        assertEquals("project", Mode.PROJECT.getMessage());
        assertEquals("task", Mode.TASK.getMessage());
    }

}