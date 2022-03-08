package management;

import entity.CustomEntity;
import entity.PersonEntity;
import exception.UserNullinputException;
import util.HibernateUtil;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static util.ManagamentUtil.*;

/************************************************************************
 Made by        PatrickSys
 Date           08/03/2022
 Package        management
 Description:
 ************************************************************************/
public class CideManagement {
    private HibernateUtil utils;

    final String MENUCHOICES =
            "Introduce una opción: \n 1.Dar de alta una persona\n 2.Dar de baja una persona\n 3.Modificar una persona\n " +
                    "4.Dar de alta un socio \n 5.Dar de baja un socio\n 6.Modificar un socio\n 7.Consultar un socio\n " +
                    "8.Consultar un libro";

    Map<Integer, Runnable> menuCallbackMap;
    private String menuChoices;
    private ArrayList<String> menuList;

    public void start() throws UserNullinputException {
        this.initializeMenuMap();
        this.utils = new HibernateUtil();

        this.menuList = this.getMenuList();
        this.menuChoices = parseListAsString(this.menuList);

        // do things
        try {
            menu();
        } catch (UserNullinputException | ClassCastException e) {
            menu();
        }
    }

    private void initializeMenuMap() {
        this.menuCallbackMap = new HashMap<>();
        this.menuCallbackMap.put(1, () -> this.create(new PersonEntity()));
        this.menuCallbackMap.put(2, () -> this.baja(new PersonEntity()));
        this.menuCallbackMap.put(3, () -> this.update(new PersonEntity()));

    }

    private void create(CustomEntity<?> entity) {
        try {
            utils.create(entity.createWithJoption(entity));
        } catch (UserNullinputException e) {
            e.printStackTrace();
        }
    }


    private void update(CustomEntity<?> entity) {
        try {
            CustomEntity<?> retrievedEntity = findById(entity);
            CustomEntity<?> newEntity = entity.createWithJoption(retrievedEntity);
            this.utils.update(newEntity);
        } catch (UserNullinputException e) {
            e.printStackTrace();
        } catch (EntityNotFoundException e) {
            showEntityNotFoundException(e.getMessage());
        }
    }


    private void baja(CustomEntity<?> entity) {
        int entityId = 0;
        try {
            entityId = inputNumber("Id del " + entity.name() + " a dar de baja");
        } catch (UserNullinputException e) {
            e.printStackTrace();
            return;
        }
        entity.setId(entityId);

        try {
            this.utils.delete(entity);
        } catch (EntityNotFoundException e) {
            showEntityNotFoundException(e.getMessage());
        }


    }

    private CustomEntity<?> findById(CustomEntity<?> entity) throws UserNullinputException {
        int id = inputNumber("Id del " + entity.name() + " a actualizar");
        return this.utils.findById(id, entity);
    }
    private void menu() throws UserNullinputException {
        String choice = inputData(this.MENUCHOICES);


        while(!isNumberValid(choice)){
            choice = inputData("Opción inválida, por favor, escoge una opción entre: " + this.MENUCHOICES);
        }

        int choiceInt = Integer.parseInt(choice);

        this.handleMenuChoice(choiceInt);
    }

    private void handleMenuChoice(int choice) throws UserNullinputException {
        this.menuCallbackMap.get(choice).run();
        this.menu();
    }

    private ArrayList<String> getMenuList() {
        return new ArrayList<>() {
            {
                add("1.Dar de alta un libro\n");
                add("2.Dar de baja un libro\n");
                add("3.Modificar un libro\n");
                add("4.Dar de alta un socio\n");
                add("5.Dar de baja un socio\n");
                add("6.Modificar un socio");
            }
        };
    }


}
