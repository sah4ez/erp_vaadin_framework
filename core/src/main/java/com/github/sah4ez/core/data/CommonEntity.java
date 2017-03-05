package com.github.sah4ez.core.data;

/**
 * Created by aleksandr on 05.03.17.
 */
public class CommonEntity implements Entity {
    private Integer idEntity = Entity.NULL_ID_ENTITY;

    public CommonEntity() {

    }

    @Override
    public Integer getIdEntity() {
        return idEntity;
    }

    @Override
    public void setIdEntity(Integer idEntity) {
        this.idEntity = idEntity;
    }

    @Override
    public String toString() {
        return "CommonEntity{" + "idEntity=" + idEntity + "}";
    }
}
