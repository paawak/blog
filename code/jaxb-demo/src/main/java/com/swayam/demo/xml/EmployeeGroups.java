package com.swayam.demo.xml;

import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class EmployeeGroups {

    private final int groupSize;

    private final Map<EmployeeRole, List<Employee>> employeeGroups;

    public EmployeeGroups(int groupSize, Map<EmployeeRole, List<Employee>> employeeGroups) {
        this.groupSize = groupSize;
        this.employeeGroups = employeeGroups;
    }

    public EmployeeGroups() {
        this(0, null);
    }

    public int getGroupSize() {
        return groupSize;
    }

    public Map<EmployeeRole, List<Employee>> getEmployeeGroups() {
        return employeeGroups;
    }

}
