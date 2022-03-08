import exception.UserNullinputException;
import management.CideManagement;

/************************************************************************
 Made by        PatrickSys
 Date           08/03/2022
 Package        PACKAGE_NAME
 Description:
 ************************************************************************/
public class Main {


    public static void main(String[] args) throws UserNullinputException {
        CideManagement management = new CideManagement();
        management.start();
    }
}
