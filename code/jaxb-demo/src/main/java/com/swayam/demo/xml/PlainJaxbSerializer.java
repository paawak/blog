package com.swayam.demo.xml;

import java.io.OutputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.transform.Result;
import javax.xml.transform.stream.StreamResult;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PlainJaxbSerializer implements XmlSerializer {

    private static final Logger LOG = LoggerFactory.getLogger(PlainJaxbSerializer.class);

    @Override
    public void serialize(Object object, OutputStream outputStream) {
        JAXBContext jaxbContext;
        try {
            jaxbContext = JAXBContext.newInstance(EmployeeGroups.class);
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }

        Marshaller marshaller;
        try {
            marshaller = jaxbContext.createMarshaller();
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }

        Result xmlResultCapturer = new StreamResult(outputStream);
        try {
            marshaller.marshal(object, xmlResultCapturer);
        } catch (Exception e) {
            LOG.error("could not convert rmi output to xml", e);
        }
    }

}
