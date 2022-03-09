import entity.ProfessorEntity;
import exception.UserNullinputException;
import management.CideManagement;
import org.hibernate.query.Query;

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
