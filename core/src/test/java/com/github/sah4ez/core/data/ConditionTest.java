package com.github.sah4ez.core.data;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by aleksandr on 08.01.17.
 */
public class ConditionTest extends Assert{
    @Test
    public void getId() throws Exception {
        assertEquals(1, Condition.EDIT.getId().intValue());
        assertEquals(2, Condition.NOT_USE.getId().intValue());
        assertEquals(3, Condition.USE.getId().intValue());
        assertEquals(4, Condition.USE_EDIT.getId().intValue());
        assertEquals(5, Condition.USE_NOT_EDIT.getId().intValue());
    }

    @Test
    public void getText() throws Exception {
        assertEquals("edit", Condition.EDIT.getText());
        assertEquals("not-use", Condition.NOT_USE.getText());
        assertEquals("use", Condition.USE.getText());
        assertEquals("use-edit", Condition.USE_EDIT.getText());
        assertEquals("use-not-edit", Condition.USE_NOT_EDIT.getText());
    }

}