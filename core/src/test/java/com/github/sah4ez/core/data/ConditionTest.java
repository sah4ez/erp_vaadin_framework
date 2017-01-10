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
        assertEquals("edit", Condition.EDIT.getCssStyle());
        assertEquals("not-use", Condition.NOT_USE.getCssStyle());
        assertEquals("use", Condition.USE.getCssStyle());
        assertEquals("use-edit", Condition.USE_EDIT.getCssStyle());
        assertEquals("use-not-edit", Condition.USE_NOT_EDIT.getCssStyle());
    }
    
    @Test
    public void getCondition(){
        assertEquals(Condition.EDIT, Condition.valueOf("EDIT"));
        assertEquals(Condition.NOT_USE, Condition.valueOf("NOT_USE"));
        assertEquals(Condition.USE, Condition.valueOf("USE"));
        assertEquals(Condition.USE_EDIT, Condition.valueOf("USE_EDIT"));
        assertEquals(Condition.USE_NOT_EDIT, Condition.valueOf("USE_NOT_EDIT"));
    }

}