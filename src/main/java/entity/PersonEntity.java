package entity;

import exception.UserNullinputException;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.lang.annotation.Annotation;
import java.sql.Date;
import java.util.Objects;

import static util.ManagamentUtil.*;

/************************************************************************
 Made by        PatrickSys
 Date           08/03/2022
 Package        entity
 Description:
 ************************************************************************/
@Entity
@Table(name = "person", schema = "cide")
public class PersonEntity implements CustomEntity<PersonEntity> {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;
    private String nif;
    private String name;
    private String firstName;
    private String lastName;
    private Date birthDate;
    private String gender;
    private String address;
    private Integer phoneNumber;

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    @Override
    public String name() {
        return "Persona";
    }


    // Crea un departamento usando Java Swing
    @Override
    public CustomEntity<PersonEntity> createWithJoption(CustomEntity<?> person) throws UserNullinputException {
     PersonEntity newPerson = new PersonEntity();

        newPerson.setId(person.getId());
        String nif = inputString("nif: ");
        String name = inputString("nombre: ");
        String firstName = inputString("primer apellido");
        String lastName = inputString("segundo apellido");
        Date birthDate = inputDate("fecha nac. formato yyyy-MM-dd");
        String address = inputString("direccion: ");
        String gender = inputString("sexo: ");
        int phoneNumber = inputNumber("telefono: ");

        newPerson.setNif(nif);
        newPerson.setName(name);
        newPerson.setFirstName(firstName);
        newPerson.setLastName(lastName);
        newPerson.setBirthDate(birthDate);
        newPerson.setAddress(address);
        newPerson.setGender(gender);
        newPerson.setPhoneNumber(phoneNumber);

        return newPerson;
    }


    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "nif", nullable = true, length = 11)
    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    @Basic
    @Column(name = "name", nullable = true, length = 20)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "first_name", nullable = true, length = 20)
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Basic
    @Column(name = "last_name", nullable = true, length = 20)
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Basic
    @Column(name = "birth_date", nullable = true)
    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    @Basic
    @Column(name = "gender", nullable = true, length = 10)
    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Basic
    @Column(name = "address", nullable = true, length = 100)
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Basic
    @Column(name = "phone_number", nullable = true)
    public Integer getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(Integer phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PersonEntity that = (PersonEntity) o;

        if (id != 0 ? id != that.id : that.id != 0) return false;
        if (!Objects.equals(nif, that.nif)) return false;
        if (!Objects.equals(name, that.name)) return false;
        if (!Objects.equals(firstName, that.firstName)) return false;
        if (!Objects.equals(lastName, that.lastName)) return false;
        if (!Objects.equals(birthDate, that.birthDate)) return false;
        if (!Objects.equals(gender, that.gender)) return false;
        if (!Objects.equals(address, that.address)) return false;
        if (!Objects.equals(phoneNumber, that.phoneNumber)) return false;

        return true;
    }

    @Override
    public Class<? extends Annotation> annotationType() {
        return null;
    }

    @Override
    public String toString() {
        return
                "\tid: " + id +
                ", nif: " + nif +
                ", nombre: " + name + " " + firstName + " " + lastName +
                ", fecha_nac: " + birthDate +
                ", sexo: " + gender +
                ", dirección: " + address +
                ", numero tel.: " + phoneNumber + "\t";
    }
}
