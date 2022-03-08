package entity;

import exception.UserNullinputException;

import javax.persistence.Entity;

/************************************************************************
 Made by        PatrickSys
 Date           08/03/2022
 Package        entity
 Description:
 ************************************************************************/
public interface CustomEntity<T> extends Entity {
    void setId(int entityId);
    int getId();
    String name();
    CustomEntity<T> createWithJoption(CustomEntity<?> entity) throws UserNullinputException;
    String findBy();
}
