package com.swayam.demo.xml.jaxb;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import com.swayam.demo.xml.Employee;
import com.swayam.demo.xml.EmployeeRole;

public class JaxbMapAdapter extends XmlAdapter<ListWrapper<SimpleMapEntry>, Map<EmployeeRole, List<Employee>>> {

    @Override
    public ListWrapper<SimpleMapEntry> marshal(Map<EmployeeRole, List<Employee>> employeeGroups) {
	List<SimpleMapEntry> simpleEntries = new ArrayList<>();

	for (Entry<EmployeeRole, List<Employee>> employeeGroupEntry : employeeGroups.entrySet()) {
	    SimpleMapEntry simpleMapEntry = new SimpleMapEntry();
	    simpleMapEntry.setEmployeeRole(employeeGroupEntry.getKey());
	    simpleMapEntry.setEmployees(employeeGroupEntry.getValue());
	    simpleEntries.add(simpleMapEntry);
	}

	return new ListWrapper<>(simpleEntries);
    }

    @Override
    public Map<EmployeeRole, List<Employee>> unmarshal(ListWrapper<SimpleMapEntry> listWrapper) {
	Map<EmployeeRole, List<Employee>> map = new HashMap<>();
	for (SimpleMapEntry mapEntry : listWrapper.getList()) {
	    map.put(mapEntry.getEmployeeRole(), mapEntry.getEmployees());
	}
	return map;
    }

}
