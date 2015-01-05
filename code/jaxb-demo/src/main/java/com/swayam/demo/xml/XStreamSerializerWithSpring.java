package com.swayam.demo.xml;

import java.io.OutputStream;

import javax.xml.transform.Result;
import javax.xml.transform.stream.StreamResult;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.oxm.xstream.XStreamMarshaller;

public class XStreamSerializerWithSpring implements XmlSerializer {

    private static final Logger LOG = LoggerFactory.getLogger(XStreamSerializerWithSpring.class);

    @Override
    public void serialize(Object object, OutputStream outputStream) {

        XStreamMarshaller marshaller = new XStreamMarshaller();
        // Map<String, ?> aliasesByType = new HashMap<>();

        Result xmlResultCapturer = new StreamResult(outputStream);
        try {
            marshaller.marshal(object, xmlResultCapturer);
        } catch (Exception e) {
            LOG.error("could not convert rmi output to xml", e);
        }

    }

}
