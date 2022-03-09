package entity;

import exception.UserNullinputException;

import javax.persistence.Entity;

/************************************************************************
 Made by        PatrickSys
 Date           08/03/2022
 Package        entity
 Description:   Interface para extender en nuestras entidades
 ************************************************************************/
public interface CustomEntity<T> extends Entity {
    void setId(int entityId);
    int getId();
    String name();
    // Método para crear una instancia de una entidad nuestra y asignarle todos los valores pertinentes
    CustomEntity<T> createWithJoption(CustomEntity<?> entity) throws UserNullinputException;
    String toString();
}
