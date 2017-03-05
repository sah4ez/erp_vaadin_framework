package com.github.sah4ez.core.data;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by aleksandr on 05.03.17.
 */
public class CommonEntityTest {
    CommonEntity entity;
    @Before
    public void setUp() throws Exception {
        entity = new CommonEntity();
        entity.setIdEntity(1);
    }

    @Test
    public void getEntity() throws Exception {
        //Assign

        //Act

        //Assert
        assertEquals(1, entity.getIdEntity().intValue());
    }

    @Test
    public void testToString() throws Exception {
        //Assign
        String result = "CommonEntity{" + "idEntity=" + 1 + "}";
        //Act

        //Assert
        assertEquals(result, entity.toString());
    }
}