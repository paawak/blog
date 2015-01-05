package com.swayam.demo.xml.jaxb;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import com.swayam.demo.xml.Employee;
import com.swayam.demo.xml.EmployeeRole;

public class JaxbMapAdapter extends XmlAdapter<List<SimpleMapEntry>, Map<EmployeeRole, List<Employee>>> {

    @Override
    public List<SimpleMapEntry> marshal(Map<EmployeeRole, List<Employee>> employeeGroups) {
        List<SimpleMapEntry> simpleEntries = new ArrayList<>();

        for (Entry<EmployeeRole, List<Employee>> employeeGroupEntry : employeeGroups.entrySet()) {
            SimpleMapEntry simpleMapEntry = new SimpleMapEntry();
            simpleMapEntry.setEmployeeRole(employeeGroupEntry.getKey());
            simpleMapEntry.setEmployees(employeeGroupEntry.getValue());
            simpleEntries.add(simpleMapEntry);
        }

        return simpleEntries;
    }

    @Override
    public Map<EmployeeRole, List<Employee>> unmarshal(List<SimpleMapEntry> v) {
        throw new UnsupportedOperationException();
    }

}
