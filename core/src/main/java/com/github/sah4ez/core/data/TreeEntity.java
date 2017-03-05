package com.github.sah4ez.core.data;

/**
 * Created by aleksandr on 05.03.17.
 */
public interface TreeEntity extends Entity {

    Integer NULL_ID_PARENT = -1;

    TreeEntity getParent();

    void setParent(TreeEntity parent);

    Integer getIdParent();

    void setIdParent(Integer idParent);
}
