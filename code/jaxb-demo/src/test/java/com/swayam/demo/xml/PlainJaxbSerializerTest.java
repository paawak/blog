package com.swayam.demo.xml;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;

import org.junit.Before;
import org.junit.Test;

public class PlainJaxbSerializerTest {

    private EmployeeGroups employeeGroups;

    @Before
    public void setup() {
        employeeGroups = new EmployeeServiceImpl1().getEmployeeGroups();
    }

    @Test
    public void testSerialization() throws FileNotFoundException {
        XmlSerializer xmlSerializer = new PlainJaxbSerializer();
        OutputStream outputStream = new FileOutputStream("jaxb.xml");
        xmlSerializer.serialize(employeeGroups.getEmployeeGroups(), outputStream);
    }

}
