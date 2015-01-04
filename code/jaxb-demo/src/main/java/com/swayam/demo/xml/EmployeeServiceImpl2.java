package com.swayam.demo.xml;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EmployeeServiceImpl2 implements EmployeeService {

    @Override
    public EmployeeGroups getEmployeeGroups() {

        List<Employee> managers = new ArrayList<>();
        managers.add(new Employee("Sachin", 35, EmployeeRole.MANAGER));
        managers.add(new Employee("Hamid", 32, EmployeeRole.MANAGER));
        managers.add(new Employee("Naresh", 34, EmployeeRole.MANAGER));
        managers.add(new Employee("Manna", 36, EmployeeRole.MANAGER));

        List<Employee> technicians = new ArrayList<>();
        technicians.add(new Employee("Sadhu", 22, EmployeeRole.TECHNICIAN));
        technicians.add(new Employee("Yadav", 25, EmployeeRole.TECHNICIAN));
        technicians.add(new Employee("Chris", 28, EmployeeRole.TECHNICIAN));
        technicians.add(new Employee("Bill", 34, EmployeeRole.TECHNICIAN));

        List<Employee> superviors = new ArrayList<>();
        superviors.add(new Employee("Ramu", 42, EmployeeRole.SUPERVISOR));
        superviors.add(new Employee("Shyam", 45, EmployeeRole.SUPERVISOR));
        superviors.add(new Employee("Radhe", 38, EmployeeRole.SUPERVISOR));
        superviors.add(new Employee("Jesse", 44, EmployeeRole.SUPERVISOR));

        Map<EmployeeRole, List<Employee>> employeeGroups = new HashMap<>();
        employeeGroups.put(EmployeeRole.MANAGER, managers);
        employeeGroups.put(EmployeeRole.TECHNICIAN, technicians);
        employeeGroups.put(EmployeeRole.SUPERVISOR, superviors);

        return new EmployeeGroups(employeeGroups);
    }
}
