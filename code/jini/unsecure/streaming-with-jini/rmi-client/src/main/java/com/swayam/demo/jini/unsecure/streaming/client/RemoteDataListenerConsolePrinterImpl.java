package com.swayam.demo.jini.unsecure.streaming.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.swayam.demo.jini.unsecure.streaming.api.service.RemoteDataListener;

public class RemoteDataListenerConsolePrinterImpl<T> implements RemoteDataListener<T> {

    private static final Logger LOGGER = LoggerFactory.getLogger(RemoteDataListenerConsolePrinterImpl.class);

    @Override
    public void newData(T data) {
	LOGGER.info("New data recieved: {}", data);
    }

    @Override
    public void endOfData() {
	LOGGER.info("End of data");
    }

}
