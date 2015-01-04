package com.swayam.demo.xml;

public class Employee {

	private final String name;
	private final int age;
	private final EmployeeRole role;

	public Employee(String name, int age, EmployeeRole role) {
		this.name = name;
		this.age = age;
		this.role = role;
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
		return "Employee [name=" + name + ", age=" + age + ", role=" + role
				+ "]";
	}

}
