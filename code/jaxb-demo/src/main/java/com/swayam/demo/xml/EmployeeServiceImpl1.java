package com.swayam.demo.xml;

import java.time.Instant;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EmployeeServiceImpl1 implements EmployeeService {

    @Override
    public EmployeeGroups getEmployeeGroups() {

	List<Employee> managers = Arrays.asList(new Employee("Sachin", 35, EmployeeRole.MANAGER, Instant.now()), new Employee("Hamid", 32, EmployeeRole.MANAGER, Instant.now()),
		new Employee("Naresh", 34, EmployeeRole.MANAGER, Instant.now()), new Employee("Manna", 36, EmployeeRole.MANAGER, Instant.now()));

	List<Employee> technicians = Arrays.asList(new Employee("Sadhu", 22, EmployeeRole.TECHNICIAN, Instant.now()), new Employee("Yadav", 25, EmployeeRole.TECHNICIAN, Instant.now()),
		new Employee("Chris", 28, EmployeeRole.TECHNICIAN, Instant.now()), new Employee("Bill", 34, EmployeeRole.TECHNICIAN, Instant.now()));

	List<Employee> superviors = Arrays.asList(new Employee("Ramu", 42, EmployeeRole.SUPERVISOR, Instant.now()), new Employee("Shyam", 45, EmployeeRole.SUPERVISOR, Instant.now()),
		new Employee("Radhe", 38, EmployeeRole.SUPERVISOR, Instant.now()), new Employee("Jesse", 44, EmployeeRole.SUPERVISOR, Instant.now()));

	Map<EmployeeRole, List<Employee>> employeeGroups = new HashMap<>();
	employeeGroups.put(EmployeeRole.MANAGER, managers);
	employeeGroups.put(EmployeeRole.TECHNICIAN, technicians);
	employeeGroups.put(EmployeeRole.SUPERVISOR, superviors);

	return new EmployeeGroups(3, employeeGroups);

    }

}
