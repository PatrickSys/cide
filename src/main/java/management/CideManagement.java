package management;

import entity.CustomEntity;
import entity.DepartmentEntity;
import entity.PersonEntity;
import entity.ProfessorEntity;
import exception.EntityForeignKeyViolation;
import exception.EntityForeignKeysAlreadyExist;
import exception.UserNullinputException;
import util.CustomOrderBy;
import util.HibernateUtil;

import javax.persistence.EntityNotFoundException;
import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Field;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.String.valueOf;
import static javax.swing.JOptionPane.showMessageDialog;
import static util.ManagamentUtil.*;

/************************************************************************
 Made by        PatrickSys
 Date           08/03/2022
 Package        management
 Description:   Clase principal donde se gestiona la app
 ************************************************************************/
public class CideManagement {
    private HibernateUtil utils;

    final String MENUCHOICES =
            "Introduce una opción: \n 1.Dar de alta una persona\n 2.Dar de baja una persona\n 3.Modificar una persona\n " +
                    "4.Dar de alta un departamento \n 5.Dar de baja un departamento\n 6.Modificar un departamento\n 7.Dar de alta un profesor\n " +
                    "8.Dar de baja un profesor\n 9.Modificar un profesor\n 10.Consultar una persona\n 11.Consultar un profesor\n 12.Consultar un departamento" +
                    "\n 13.Consultar todas las personas y ordenarlas";

    Map<Integer, Runnable> menuCallbackMap;
    private String menuChoices;
    private ArrayList<String> menuList;

    /**
     * Método principal para inizializar la app
     * @throws UserNullinputException si el usuario le da a cancelar
     */
    public void start() throws UserNullinputException {
        this.setOptionPaneSettings();
        this.initializeMenuMap();
        this.utils = new HibernateUtil();
        this.menuList = this.getMenuList();
        this.menuChoices = parseListAsString(this.menuList);

        try {
            menu();
        } catch (UserNullinputException | ClassCastException e) {
            menu();
        }
    }

    private void setOptionPaneSettings() {
        UIManager.put("OptionPane.messageFont", new Font("Arial", Font.BOLD, 16));
        UIManager.put("OptionPane.buttonFont", new Font("Arial", Font.PLAIN, 12));
        UIManager.put("OptionPane.cancelButtonText", "Cancelar");
        UIManager.put("OptionPane.noButtonText", "No");
        UIManager.put("OptionPane.okButtonText", "De acuerdo");
        UIManager.put("OptionPane.yesButtonText", "Vale");
        UIManager.put("OptionPane.background", Color.WHITE);
        UIManager.put("OptionPane.messagebackground", Color.WHITE);
        UIManager.put("Panel.background", Color.WHITE);
    }

    private void initializeMenuMap() {
        this.menuCallbackMap = new HashMap<>();
        this.menuCallbackMap.put(1, () -> this.create(new PersonEntity()));
        this.menuCallbackMap.put(2, () -> this.remove(new PersonEntity()));
        this.menuCallbackMap.put(3, () -> this.update(new PersonEntity()));
        this.menuCallbackMap.put(4, () -> this.create(new DepartmentEntity()));
        this.menuCallbackMap.put(5, () -> this.remove(new DepartmentEntity()));
        this.menuCallbackMap.put(6, () -> this.update(new DepartmentEntity()));
        this.menuCallbackMap.put(7, () -> this.createProfessor(new ProfessorEntity()));
        this.menuCallbackMap.put(8, () -> this.remove(new ProfessorEntity()));
        this.menuCallbackMap.put(9, () -> this.update(new ProfessorEntity()));
        this.menuCallbackMap.put(10, () -> this.consult(new PersonEntity()));
        this.menuCallbackMap.put(11, () -> this.consult(new ProfessorEntity()));
        this.menuCallbackMap.put(12, () -> this.consult(new DepartmentEntity()));
        this.menuCallbackMap.put(13, this::sortPeople);
    }

    // Crea un nuevo objeto persistente en la BBDD
    private void create(CustomEntity<?> entity) {
        try {
            utils.create(entity.createWithJoption(entity));
        } catch (UserNullinputException e) {
            e.printStackTrace();
        }
    }

    // Método genérico de findAll
    private <T> List<T> findAll(CustomEntity<T> entity) {
         return (List<T>) this.utils.<T>findAll((Class<CustomEntity<T>>) entity.getClass());
    }


    // Encuentra todas las personas de la bbdd y las ordena según diga el usuario
    private void sortPeople() {
        Map<Integer, Runnable> sortFunctions = new HashMap<>();
        List<PersonEntity> people = this.findAll(new PersonEntity());
        int orderByChoice = 0;
        sortFunctions.put(1, ()-> this.orderPeopleBy(people, CustomOrderBy.NAME));
        sortFunctions.put(2, ()-> this.orderPeopleBy(people, CustomOrderBy.FIRSTNAME));
        sortFunctions.put(3, ()-> this.orderPeopleBy(people, CustomOrderBy.LASTNAME));
        sortFunctions.put(4, ()-> this.orderPeopleBy(people, CustomOrderBy.BIRTHDATE));
        try {
           orderByChoice = inputNumber("Ordenar por: \n1.Nombre\n2.Primer apellido\n3.Segundo apellido\n4.Fecha de nacimiento");
           sortFunctions.get(orderByChoice).run();
        } catch (UserNullinputException e) {
          return;
        }
        catch(NullPointerException e) {
            showWrongChoiceMessage(1,4);
        }

    }

    // Mapa de callback según cómo haya que ordenar
    private void orderPeopleBy(List<PersonEntity> people, String order)  {
        HashMap <String, Runnable> orderCallback = new HashMap<>();
        orderCallback.put(CustomOrderBy.NAME, () -> this.orderPeopleByName(people));
        orderCallback.put(CustomOrderBy.FIRSTNAME, () -> this.orderPeopleByFirstName(people));
        orderCallback.put(CustomOrderBy.LASTNAME, () -> this.orderPeopleByLastName(people));
        orderCallback.put(CustomOrderBy.BIRTHDATE, () -> this.orderPeopleByDateOfBirth(people));
        orderCallback.get(order).run();
    }

    /**
     * Métodos para ordenar las personas
     * @param people
     */

    private void orderPeopleByName(List<PersonEntity> people ) {
        List<PersonEntity> parsedPersonList = people.stream().sorted((person, person1) -> person.getName().
                compareToIgnoreCase(person1.getName())).collect(Collectors.toList());
        this.parsePeopleSorted(parsedPersonList);
    }

    private void orderPeopleByFirstName(List<PersonEntity> people ) {
        List<PersonEntity> parsedPersonList = people.stream().sorted((person, person1) -> person.getFirstName().
                compareToIgnoreCase(person1.getFirstName())).collect(Collectors.toList());
        this.parsePeopleSorted(parsedPersonList);
    }

    private void orderPeopleByLastName(List<PersonEntity> people ) {
        List<PersonEntity> parsedPersonList = people.stream().sorted((person, person1) -> person.getLastName().
                compareToIgnoreCase(person1.getFirstName())).collect(Collectors.toList());
        this.parsePeopleSorted(parsedPersonList);
    }
    private void orderPeopleByDateOfBirth(List<PersonEntity> people ) {
        List<PersonEntity> parsedPersonList = people.stream().sorted(Comparator.comparing(PersonEntity::getBirthDate)).collect(Collectors.toList());
        this.parsePeopleSorted(parsedPersonList);
    }

    private void parsePeopleSorted(List<PersonEntity> sortedPeople) {
        StringBuilder listParser = new StringBuilder();
        sortedPeople.forEach(person -> {
            listParser.append(person.toString()).append(concatPersonExtraInfo(person)).append("\n");
        });
        showMessage(listParser.toString());
    }
    // método específico para crear el profesor, ya que hay que asegurarse de que las foreign keys existan
  private void createProfessor(CustomEntity<ProfessorEntity> professor) {
        try {
            utils.createProfessor((ProfessorEntity) professor.createWithJoption(professor));
        } catch (UserNullinputException e) {
            e.printStackTrace();
        } catch (EntityForeignKeysAlreadyExist e) {
            showEntityAlreadyExists(professor.name());
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
        } catch (EntityForeignKeyViolation e) {
            showConstraintViolation();
        }
    }


    private void remove(CustomEntity<?> entity) {
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

    /**
     * Permite consultar cualquier entidad por cualquier campo
     */
    private void consult(CustomEntity<?> entity) {

        String field = "";
        String fieldValue = "";
        try {
            field = inputData("campo por el que buscar");
            List<String> entityFields = Arrays.stream(entity.getClass().getDeclaredFields()).map(Field::getName).collect(Collectors.toList());

            if(!entityFields.contains(field)) {
                throw new NoSuchFieldException("El campo no existe");
            }

            fieldValue = createForSearch(field);
            CustomEntity<?> retrievedEntity = this.utils.findByField(entity, field, fieldValue);
            if(null == retrievedEntity ) {
                showEntityNotFoundException(entity.name());
                return;
            }
            String entityToString = retrievedEntity.toString();
            entityToString += concatPersonExtraInfo(retrievedEntity);
            entityToString += concatDepartmentExtraInfo(retrievedEntity);
            showMessageDialog(null,entityToString);

        } catch (NoSuchFieldException e) {
            showMessageDialog(null, e.getMessage());
        } catch (UserNullinputException ignored) {
        }
    }

    // Método para concatenar la información de la persona cuando se llame al consult()
    private String concatPersonExtraInfo(CustomEntity<?> person) {
        if(!person.getClass().isAssignableFrom(PersonEntity.class)) return "";

        String professorId = "";
        String departmentName = "";
        String concatString = "";

        ProfessorEntity professor = (ProfessorEntity) this.utils.findByField(new ProfessorEntity(), "personId", valueOf(person.getId()));
        if(null == professor) {
            return " ";
        }

        professorId = valueOf(professor.getId());
        concatString+= " id profesor: " + professorId;

        DepartmentEntity department = (DepartmentEntity) this.utils.findByField(new DepartmentEntity(), "id", valueOf(professor.getDepartmentId()));

        if(null != department) {
            departmentName = department.getName();
            concatString+= " nombre departamento: " + departmentName;
        }

        return concatString;
    }

    // Método para concatenar la información del departamento cuando se llame al consult()
    private String concatDepartmentExtraInfo(CustomEntity<?> department) {
        if(!department.getClass().isAssignableFrom(DepartmentEntity.class)) return "";
        List<String> professorsInfo = new ArrayList<>();
        Optional<String> optionalProfessorsInfo;
        String parsedProfessorsInfo = " ";

        List<ProfessorEntity>  professors = this.utils.getProfessorsByDepartment(department.getId());
        if(professors.isEmpty()) return "";

        // obtenemos la info personal de cada profesor
        professors.forEach(professorEntity -> {
            PersonEntity person = (PersonEntity) this.utils.findById(professorEntity.getPersonId(), new PersonEntity());
            String parsedInfo = "\nprofesor " + person.getName() +  " id: " + professorEntity.getId() + "nif: " + person.getNif();
            professorsInfo.add(parsedInfo);
        });

        //parseamos la lista de info del profesor
        optionalProfessorsInfo = professorsInfo.stream().reduce(String::concat);
        if(optionalProfessorsInfo.isEmpty()) return "";
        parsedProfessorsInfo = optionalProfessorsInfo.get().replaceAll("\\[", "").replaceAll("\\]","");;
        return  parsedProfessorsInfo;
    }

    // método genérico para buscar cualquier entidad por id
    private CustomEntity<?> findById(CustomEntity<?> entity) throws UserNullinputException {
        String idDel = entity.getClass().isAssignableFrom(PersonEntity.class) ? "Id de la " : "Id del ";
        int id = inputNumber(idDel + entity.name() + " a actualizar");
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
        try {
            this.menuCallbackMap.get(choice).run();
        }
        catch (NullPointerException e) {
            showWrongChoiceMessage(1, 13);
        }
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
