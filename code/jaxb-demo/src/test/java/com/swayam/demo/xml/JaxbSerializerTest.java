package com.swayam.demo.xml;

import org.junit.Before;
import org.junit.Test;

public class JaxbSerializerTest {

    private EmployeeGroups employeeGroups;

    @Before
    public void setup() {
        employeeGroups = new EmployeeServiceImpl1().getEmployeeGroups();
    }

    @Test
    public void testPlainSerialization() {
        XmlSerializer xmlSerializer = new PlainJaxbSerializer();
        xmlSerializer.serialize(employeeGroups, System.err);
    }

    @Test
    public void testSpringSerialization() {
        XmlSerializer xmlSerializer = new JaxbSerializerWithSpring();
        xmlSerializer.serialize(employeeGroups, System.err);
    }

}
