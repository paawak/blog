package com.swayam.demo.trx.mq;

import javax.jms.Message;
import javax.jms.MessageListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class JmsMessageConsumer implements MessageListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(JmsMessageConsumer.class);

    @Override
    public void onMessage(Message message) {

        LOGGER.info("+++++++++++++++++ recieved message: {}", message);

    }

}
