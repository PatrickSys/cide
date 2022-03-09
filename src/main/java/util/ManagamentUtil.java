package util;

import exception.UserNullinputException;

import javax.swing.*;
import java.sql.Date;
import java.util.ArrayList;

import static javax.swing.JOptionPane.showMessageDialog;


/************************************************************************
 Made by        PatrickSys
 Date           22/02/2022
 Package        utils
 Description:
 ************************************************************************/


public class ManagamentUtil {

    public static void showWrongChoiceMessage(int from, int to) {
        showMessage("Por favor elige una opción entre " + from + " y " + to );
    }

    public static String inputData(String message) throws UserNullinputException {
        String data =  JOptionPane.showInputDialog(null, message, "Gestión CIDE", JOptionPane.QUESTION_MESSAGE);
        if(null == data) {
            throw new UserNullinputException();
        }
        return data;
    }

    public static String inputNonBlankData(String message) throws UserNullinputException {
        String data = inputData(message);
        if(null == data) {
            throw new UserNullinputException();
        }
        if (data.isBlank()) {
            String errorMessage =  "No puedes dejar un campo en blanco\n";
            message = message.replaceAll(errorMessage, "");
            return inputNonBlankData("No puedes dejar un campo en blanco\n" + message );
        }
        return data;
    }

    public static  String parseListAsString(ArrayList<String> listToParse) {
        return listToParse.toString().replaceAll("\\[", "").replaceAll("\\]", "");
    }
    // Checks if input data is not a number
    public static boolean notNumber(String input) {
        try {
            Integer.parseInt(input);
        } catch (NumberFormatException e) {
            return true;
        }
        return false;
    }
    public static int inputNumber(String message) throws UserNullinputException {
        String input = inputNonBlankData(message);
        if (!isNumberValid(input)) {
            return inputNumber("Por favor introduce un número válido");
        }
        return Integer.parseInt(input);
    }

    public static String inputString(String message) throws UserNullinputException {
        String input = inputNonBlankData(message);
        if (notNumber(input)) {
            return input;
        }
        return inputString("Por favor introduce un String válido");
    }


    public static Date inputDate(String message) throws UserNullinputException {
        Date date;
        String input = inputNonBlankData(message);
        try {
            date = Date.valueOf(input);
        }catch (IllegalArgumentException e) {
            showMessage("Fecha inválida");
            return inputDate(message);
        }
        return date;
    }
    public static boolean isNumberValid(String number) {
        return !notNumber(number);
    }

    public static void showEntityNotFoundException(String entityName) {
        showMessage(entityName + " no encontrado en la base de datos");
    }
    public static void showConstraintViolation() {
        showMessage(" Una foreign key falla");
    }
    public static void showSuccessfullyEntityCreated(String entityName) {
        showMessage(entityName + " creado en la base de datos");
    }

    public static void showSuccessfullyEntityDeleted(String entityName) {
        showMessage(entityName + " borrado de la base de datos");
    }

    public static void showSuccessfullyEntityUpdated(String entityName) {
        showMessage(entityName, " actualizado en la base de datos");
    }

    public static void showEntityAlreadyExists(String entityName) {
        showMessage(entityName, " ya existe");
    }
    private static void showMessage(String entityName, String message) {
        showMessageDialog(null, entityName.concat(message), "Gestión CIDE", JOptionPane.INFORMATION_MESSAGE);
    }

    public static String createForSearch(String field) throws UserNullinputException {
        return inputData(field);
    }
    public static void showMessage(String message) {
        showMessageDialog(null, message, "Gestión CIDE", JOptionPane.INFORMATION_MESSAGE);
    }

}
