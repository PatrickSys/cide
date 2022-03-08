package entity;

import javax.persistence.*;
import java.util.Objects;

/************************************************************************
 Made by        PatrickSys
 Date           08/03/2022
 Package        entity
 Description:
 ************************************************************************/
@Entity
@Table(name = "professor", schema = "cide", catalog = "")
public class ProfessorEntity {
    private int id;
    private Integer personId;
    private Integer departmentId;

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
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

}
