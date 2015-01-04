package com.swayam.demo.xml;

import java.util.List;
import java.util.Map;

public class EmployeeGroups {

	private final Map<EmployeeRole, List<Employee>> employeeGroups;

	public EmployeeGroups(Map<EmployeeRole, List<Employee>> employeeGroups) {
		this.employeeGroups = employeeGroups;
	}

	public Map<EmployeeRole, List<Employee>> getEmployeeGroups() {
		return employeeGroups;
	}

}
