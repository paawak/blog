package com.swayam.demo.xml;

import java.time.Instant;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.swayam.demo.xml.jaxb.JaxbInstantAdapterField;

@XmlAccessorType(XmlAccessType.FIELD)
public class Employee {

    private final String name;
    private final int age;
    private final EmployeeRole role;

    @XmlJavaTypeAdapter(JaxbInstantAdapterField.class)
    private final Instant dateOfBirth;

    public Employee(String name, int age, EmployeeRole role, Instant dateOfBirth) {
	this.name = name;
	this.age = age;
	this.role = role;
	this.dateOfBirth = dateOfBirth;
    }

    public Employee() {
	this(null, -1, null, null);
    }

    public String getName() {
	return name;
    }

    public int getAge() {
	return age;
    }

    public EmployeeRole getRole() {
	return role;
    }

    public Instant getDateOfBirth() {
	return dateOfBirth;
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + age;
	result = prime * result + ((name == null) ? 0 : name.hashCode());
	result = prime * result + ((role == null) ? 0 : role.hashCode());
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
	Employee other = (Employee) obj;
	if (age != other.age)
	    return false;
	if (name == null) {
	    if (other.name != null)
		return false;
	} else if (!name.equals(other.name))
	    return false;
	if (role != other.role)
	    return false;
	return true;
    }

    @Override
    public String toString() {
	return "Employee [name=" + name + ", age=" + age + ", role=" + role + "]";
    }

}
