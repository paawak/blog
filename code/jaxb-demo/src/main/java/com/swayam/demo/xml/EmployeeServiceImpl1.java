package com.swayam.demo.xml;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EmployeeServiceImpl1 implements EmployeeService {

    @Override
    public EmployeeGroups getEmployeeGroups() {

        List<Employee> managers = Arrays.asList(new Employee("Sachin", 35, EmployeeRole.MANAGER), new Employee("Hamid", 32, EmployeeRole.MANAGER), new Employee("Naresh", 34,
                EmployeeRole.MANAGER), new Employee("Manna", 36, EmployeeRole.MANAGER));

        List<Employee> technicians = Arrays.asList(new Employee("Sadhu", 22, EmployeeRole.TECHNICIAN), new Employee("Yadav", 25, EmployeeRole.TECHNICIAN), new Employee("Chris",
                28, EmployeeRole.TECHNICIAN), new Employee("Bill", 34, EmployeeRole.TECHNICIAN));

        List<Employee> superviors = Arrays.asList(new Employee("Ramu", 42, EmployeeRole.SUPERVISOR), new Employee("Shyam", 45, EmployeeRole.SUPERVISOR), new Employee("Radhe", 38,
                EmployeeRole.SUPERVISOR), new Employee("Jesse", 44, EmployeeRole.SUPERVISOR));

        Map<EmployeeRole, List<Employee>> employeeGroups = new HashMap<>();
        employeeGroups.put(EmployeeRole.MANAGER, managers);
        employeeGroups.put(EmployeeRole.TECHNICIAN, technicians);
        employeeGroups.put(EmployeeRole.SUPERVISOR, superviors);

        return new EmployeeGroups(employeeGroups);

    }

}
