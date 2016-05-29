package com.swayam.demo.xml;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.ValidationEvent;
import javax.xml.bind.ValidationEventHandler;

import org.junit.Test;

import com.swayam.demo.xml.jaxb.JaxbInstantAdapterField;
import com.swayam.demo.xml.jaxb.JaxbInstantAdapterGlobal;
import com.swayam.demo.xml.jaxb.SimpleMapEntry;

public class JaxbDeSerializerTest {

    @Test
    public void test() throws JAXBException {
	JAXBContext jaxbContext = JAXBContext.newInstance(EmployeeGroups.class, SimpleMapEntry.class);

	Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

	unmarshaller.setAdapter(JaxbInstantAdapterField.class, new JaxbInstantAdapterGlobal());

	// fail if un-marshalling fails
	unmarshaller.setEventHandler(new ValidationEventHandler() {
	    @Override
	    public boolean handleEvent(ValidationEvent event) {
		// print stacktrace
		// event.getLinkedException().printStackTrace(System.err);
		return false;
	    }
	});

	EmployeeGroups employeeGroups = (EmployeeGroups) unmarshaller.unmarshal(JaxbDeSerializerTest.class.getResourceAsStream("/employee-group.xml"));

	Employee emp = employeeGroups.getEmployeeGroups().get(EmployeeRole.MANAGER).get(0);
	assertNotNull(emp);
	assertEquals("Sachin", emp.getName());
	System.out.println("JaxbDeSerializerTest.test() dob: " + emp.getDateOfBirth());
    }

}
