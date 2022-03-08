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
@Table(name = "department", schema = "cide", catalog = "")
public class DepartmentEntity {
    private int id;
    private String name;

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
    @Column(name = "name", nullable = false, length = 25)
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

}
