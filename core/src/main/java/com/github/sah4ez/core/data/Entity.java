package com.github.sah4ez.core.data;

import java.io.Serializable;

/**
 * Created by aleksandr on 05.03.17.
 */
public interface Entity extends Serializable {
    Integer NULL_ID_ENTITY = -1;

    Integer getIdEntity();

    void setIdEntity(Integer idEntity);
}
