package entity;

import exception.UserNullinputException;

import javax.persistence.*;
import java.lang.annotation.Annotation;
import java.util.Objects;

import static util.ManagamentUtil.inputNumber;

/************************************************************************
 Made by        PatrickSys
 Date           08/03/2022
 Package        entity
 Description:
 ************************************************************************/
@Entity
@Table(name = "professor", schema = "cide")
public class ProfessorEntity implements CustomEntity<ProfessorEntity> {
    private int id;
    private Integer personId;
    private Integer departmentId;

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    @Override
    public String name() {
        return "Profesor";
    }

    // Crea un departamento usando Java Swing
    @Override
    public CustomEntity<ProfessorEntity> createWithJoption(CustomEntity<?> professor) throws UserNullinputException {
        ProfessorEntity newProfessor = new ProfessorEntity();
        newProfessor.setId(professor.getId());
        int personId = inputNumber("id de persona del profesor: ");
        int departmentId = inputNumber("id del departamento del profesor");
        newProfessor.setPersonId(personId);
        newProfessor.setDepartmentId(departmentId);
        return newProfessor;
    }


    public void setId(int id) {
        this.id = id;
    }


    @Basic
    @Column(name = "person_id", nullable = true)
    public Integer getPersonId() {
        return personId;
    }

    public void setPersonId(Integer personId) {
        this.personId = personId;
    }

    @Basic
    @Column(name = "department_id", nullable = true)
    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProfessorEntity that = (ProfessorEntity) o;

        if (id != 0 ? id != that.id : that.id != 0) return false;
        if (!Objects.equals(personId, that.personId)) return false;
        if (!Objects.equals(departmentId, that.departmentId)) return false;

        return true;
    }

    @Override
    public Class<? extends Annotation> annotationType() {
        return null;
    }

    @Override
    public String toString() {
        return
                "id: " + id +
                ", id persona: " + personId +
                ", id departamento: " + departmentId;
    }
}
