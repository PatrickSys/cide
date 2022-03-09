package entity;

import exception.UserNullinputException;

import javax.persistence.*;
import java.lang.annotation.Annotation;
import java.util.Objects;

import static util.ManagamentUtil.inputString;

/************************************************************************
 Made by        PatrickSys
 Date           08/03/2022
 Package        entity
 Description:
 ************************************************************************/
@Entity
@Table(name = "department", schema = "cide", catalog = "")
public class DepartmentEntity  implements CustomEntity<DepartmentEntity>{
    private int id;
    private String name;

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    @Override
    public String name() {
        return "Departamento";
    }

    // Crea un departamento usando Java Swing
    @Override
    public CustomEntity<DepartmentEntity> createWithJoption(CustomEntity<?> department) throws UserNullinputException {
        DepartmentEntity newDepartment = new DepartmentEntity();
        newDepartment.setId(department.getId());

        String departmentName = inputString("Nombre del departamento: ");
        newDepartment.setName(departmentName);
        return newDepartment;
    }

    public void setId(int id) {
        this.id = id;
    }


    @Basic
    @Column(name = "name", length = 25)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DepartmentEntity that = (DepartmentEntity) o;

        if (id != 0 ? id != that.id : that.id != 0) return false;
        if (!Objects.equals(name, that.name)) return false;

        return true;
    }

    @Override
    public Class<? extends Annotation> annotationType() {
        return null;
    }

    @Override
    public String toString() {
        return
                "id departamento: " + id +
                ", nombre: " + name;
    }
}
