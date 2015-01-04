package com.swayam.demo.xml;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;

import org.junit.Before;
import org.junit.Test;

public class XmlSerializerTest {

    private EmployeeGroups employeeGroups;

    @Before
    public void setup() {
        employeeGroups = new EmployeeServiceImpl1().getEmployeeGroups();
    }

    @Test
    public void testJaxbSerialization() throws FileNotFoundException {
        XmlSerializer xmlSerializer = new JaxbXmlSerializer();
        OutputStream outputStream = new FileOutputStream("jaxb.xml");
        xmlSerializer.serialize(employeeGroups, outputStream);
    }

    @Test
    public void testXstreamSerialization() throws FileNotFoundException {
        XmlSerializer xmlSerializer = new XStreamXmlSerializer();
        OutputStream outputStream = new FileOutputStream("xstream.xml");
        xmlSerializer.serialize(employeeGroups, outputStream);
    }

}
