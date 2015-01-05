package com.swayam.demo.xml;

import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.swayam.demo.xml.jaxb.JaxbMapAdapter;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class EmployeeGroups {

    private final int groupSize;

    @XmlJavaTypeAdapter(JaxbMapAdapter.class)
    private final Map<EmployeeRole, List<Employee>> employeeGroups;

    public EmployeeGroups(int groupSize, Map<EmployeeRole, List<Employee>> employeeGroups) {
        this.groupSize = groupSize;
        this.employeeGroups = employeeGroups;
    }

    /**
     * Default constructor, as Jaxb will not work without it
     */
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
