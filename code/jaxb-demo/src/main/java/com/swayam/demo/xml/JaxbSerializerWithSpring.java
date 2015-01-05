package com.swayam.demo.xml;

import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.xml.transform.Result;
import javax.xml.transform.stream.StreamResult;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

public class JaxbSerializerWithSpring implements XmlSerializer {

    private static final Logger LOG = LoggerFactory.getLogger(JaxbSerializerWithSpring.class);

    @Override
    public void serialize(Object object, OutputStream outputStream) {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setClassesToBeBound(EmployeeGroups.class);
        Map<String, Object> marshallerProperties = new HashMap<>();
        marshallerProperties.put(javax.xml.bind.Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        marshaller.setMarshallerProperties(marshallerProperties);

        Result xmlResultCapturer = new StreamResult(outputStream);
        try {
            marshaller.marshal(object, xmlResultCapturer);
        } catch (Exception e) {
            LOG.error("could not convert rmi output to xml", e);
        }
    }

}
