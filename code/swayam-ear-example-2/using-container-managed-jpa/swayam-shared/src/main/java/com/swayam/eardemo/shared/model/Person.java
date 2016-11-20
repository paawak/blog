/*
 * Person.java
 *
 * Created on 18-Nov-2016 12:11:04 AM
 *
 * Copyright (c) 2002 - 2008 : Swayam Inc.
 *
 * P R O P R I E T A R Y & C O N F I D E N T I A L
 *
 * The copyright of this document is vested in Swayam Inc. without
 * whose prior written permission its contents must not be published,
 * adapted or reproduced in any form or disclosed or
 * issued to any third party.
 */

package com.swayam.eardemo.shared.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

/**
 * 
 * @author paawak
 */
@Entity(name = "person")
public class Person implements Serializable {

    private static final long serialVersionUID = 3L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "first_name")
    @NotNull
    private String firstName;

    @Column(name = "last_name")
    @NotNull
    private String lastName;

    @Column(name = "date_of_birth")
    @NotNull
    private LocalDate dateOfBirth;

    @Column(name = "date_of_joining")
    @NotNull
    private LocalDateTime dateOfJoining;

    public Person() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public LocalDateTime getDateOfJoining() {
        return dateOfJoining;
    }

    public void setDateOfJoining(LocalDateTime dateOfJoining) {
        this.dateOfJoining = dateOfJoining;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((dateOfBirth == null) ? 0 : dateOfBirth.hashCode());
        result = prime * result + ((dateOfJoining == null) ? 0 : dateOfJoining.hashCode());
        result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Person other = (Person) obj;
        if (dateOfBirth == null) {
            if (other.dateOfBirth != null)
                return false;
        } else if (!dateOfBirth.equals(other.dateOfBirth))
            return false;
        if (dateOfJoining == null) {
            if (other.dateOfJoining != null)
                return false;
        } else if (!dateOfJoining.equals(other.dateOfJoining))
            return false;
        if (firstName == null) {
            if (other.firstName != null)
                return false;
        } else if (!firstName.equals(other.firstName))
            return false;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (lastName == null) {
            if (other.lastName != null)
                return false;
        } else if (!lastName.equals(other.lastName))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Person [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", dateOfBirth="
                + dateOfBirth + ", dateOfJoining=" + dateOfJoining + "]";
    }

}
